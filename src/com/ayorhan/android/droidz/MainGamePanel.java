package com.ayorhan.android.droidz;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.ayorhan.android.R;
import com.ayorhan.android.droidz.model.Droid;
import com.ayorhan.android.droidz.model.components.Speed;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 1:19 PM
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;
    private Droid droid;

    public MainGamePanel(Context context) {
        super(context);

        // Add callback to the surface holder to intercept events
        getHolder().addCallback(this);

        // Create droid and load bitmap
        droid = new Droid(BitmapFactory.decodeResource(getResources(),R.drawable.droid_1), 50, 50);

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

        Log.d(TAG, "Thread shut down in a nice and clean way.");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent CALLED");
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            Log.d(TAG, "ACTION_DOWN - YOU GOT IT!");
            // The screen is a rectangle with upper left coordinates at (0,0)
            // and lower right coordinates at (getWidth(), getHeight())
            droid.handleActionDown((int)event.getX(), (int)event.getY());
            if (event.getY() > getHeight() - 50){
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG,"Coordinates: x=" + event.getX() + ",y=" + event.getY());
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            Log.d(TAG, "ACTION_MOVE");
            //if (droid.isTouched()){
                Log.d(TAG, "DROID TOUCHED AND ACTION MOVED - DROID ID MOVING...");
                // droid is picked up and is being dragged
                droid.setX((int)event.getX());
                droid.setY((int)event.getY());
            //}
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            Log.d(TAG, "ACTION_UP");
            // touch released
            if (droid.isTouched()){
                Log.d(TAG, "ACTION_UP AND DROID TOUCHED - DROPPED IT.");
                droid.setTouched(false);
            }

        }
        //return super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        droid.draw(canvas);
    }

    public void update() {
        // check collision with the RIGHT WALL if heading RIGHT
        if (droid.getSpeed().getXDirection() == Speed.DIRECTION_RIGHT
                && droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()){
            droid.getSpeed().toggleXDirection();
        }

        // check collision with the LEFT WALL if heading LEFT
        if (droid.getSpeed().getXDirection() == Speed.DIRECTION_LEFT
                && droid.getX() - droid.getBitmap().getWidth() / 2 <= 0){
            droid.getSpeed().toggleXDirection();
        }

        // check collision with the BOTTOM WALL if heading DOWN
        if (droid.getSpeed().getYDirection() == Speed.DIRECTION_DOWN
                && droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()){
            droid.getSpeed().toggleYDirection();
        }

        // check collision with the TOP WALL if heading UP
        if (droid.getSpeed().getYDirection() == Speed.DIRECTION_UP
                && droid.getY() - droid.getBitmap().getHeight() / 2 <= 0){
            droid.getSpeed().toggleYDirection();
        }
        // Update the droid
        droid.update();
    }
}
