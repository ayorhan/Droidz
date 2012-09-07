package com.ayorhan.android.droidz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 1:19 PM
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = MainGamePanel.class.getSimpleName();
    private MainThread thread;

    public MainGamePanel(Context context) {
        super(context);

        // Add callback to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // Make GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry){
            try{
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            // The screen is a rectangle with upper left coordinates at (0,0)
            // and lower right coordinates at (getWidth(), getHeight())
            if (event.getY() > getHeight() - 50){
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG,"Coordinates: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
