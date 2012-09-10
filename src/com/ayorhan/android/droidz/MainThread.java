package com.ayorhan.android.droidz;

import android.graphics.Canvas;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 1:45 PM
 */
public class MainThread extends Thread{

    private boolean running; // flag to hold game state.

    private final static String TAG = MainThread.class.getSimpleName();
    private final static int MAX_FPS = 50; // desired FPS.
    private final static int MAX_FRAME_SKIPS = 5; // max number of frames to be skipped.
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    // Stats
    private DecimalFormat df = new DecimalFormat("0.##"); // 2 decimal point
    private final static int STAT_INTERVAL = 1000; // 1 second in millisecond - read every sec
    private final static int FPS_HISTORY_NR = 10; // last n FPSs
    private long lastStatusStore = 0; // last time the status was stored
    private long statusIntervalTimer = 0l; // the status time counter
    private long totalFramesSkipped = 0l; // # of frames skipped since the game started
    private long framesSkippedPerStatCycle = 0l; //  # of frames skipped in a store cycle (1 sec)
    private int frameCountPerStatCycle = 0; // # of rendered frames in an interval
    private long totalFrameCount = 0l;
    private double fpsStore[]; // last FPS values
    private long statsCount = 0; // # of times the stat has been read
    private double averageFps = 0.0; // avg FPS since the game started

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        super.run();
        Canvas canvas;
        Log.d(TAG,"Starting game loop");

        initTimingElements();

        long beginTime;     // time when the cycle began
        long timeDiff;      // time it took for the cycle to execute
        int sleepTime;      // ms to sleep (<0 if we're behind)
        int framesSkipped;  // # of frames being skipped

        sleepTime = 0;

        while (running){
            canvas = null;
            // try locking the canvas for exclusive pixel editing in the surface
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0; // resetting the frames skipped

                    // update game state
                    this.gamePanel.update();

                    // render state to the screen
                    this.gamePanel.onDraw(canvas);

                    // calculate how long the cycle took
                    timeDiff = System.currentTimeMillis() - beginTime;

                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if(sleepTime > 0){
                        // if sleepTime > 0, the we're good.
                        try{
                            // Thread sleeps and we save some resources such as battery.
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
                        // catch up, update without rendering
                        this.gamePanel.update();
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }

                    if(framesSkipped > 0){
                        Log.d(TAG, "Skipped:" + framesSkipped);
                    }

                    // for statistics
                    framesSkippedPerStatCycle += framesSkipped;
                    storeStats(); //store gathered stats

                }
            } finally {
                // in case of an exception, clear things up.
                if(canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }

    }

    private void storeStats(){
        frameCountPerStatCycle++;
        totalFrameCount++;

        // check the actual time
        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if(statusIntervalTimer >= lastStatusStore + STAT_INTERVAL){

            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
            statsCount++;

            double totalFps = 0.0;

            for (int i=0; i<FPS_HISTORY_NR; i++){
                totalFps += fpsStore[i];
            }

            if(statsCount < FPS_HISTORY_NR){
                averageFps = totalFps / statsCount;
            } else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }

            totalFramesSkipped += framesSkippedPerStatCycle;
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;

            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
            Log.d(TAG, "Average FPS:" + df.format(averageFps));
            gamePanel.setAvgFps("FPS:" + df.format(averageFps));


        }
    }

    private void initTimingElements() {
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++){
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialized");
    }


}
