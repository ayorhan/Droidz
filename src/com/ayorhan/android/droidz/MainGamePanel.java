package com.ayorhan.android.droidz;

import android.content.Context;
import android.graphics.Canvas;
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

    public MainGamePanel(Context context) {
        super(context);

        // Add callback to the surface holder to intercept events
        getHolder().addCallback(this);

        // Make GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
