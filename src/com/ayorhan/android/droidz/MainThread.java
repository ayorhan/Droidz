package com.ayorhan.android.droidz;

import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 1:45 PM
 */
public class MainThread extends Thread{

    // flag to hold game state
    private boolean running;

    private static final String TAG = MainThread.class.getSimpleName();

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
        long tickCount = 0L;
        Log.d(TAG,"Starting game loop");

        while (running){
            // update game state
            // render state to the screen
        }

        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}
