package com.ayorhan.android.droidz.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import com.ayorhan.android.droidz.model.components.Speed;

import java.awt.font.TextAttribute;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 3:41 PM
 */
public class Droid {
    private static final String TAG = Droid.class.getSimpleName();

    private Bitmap bitmap; // actual bitmap
    private int x;
    private int y;
    private boolean touched;
    private Speed speed;

    public Droid(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y - (bitmap.getHeight()/2), null);
    }

    public void handleActionDown(int eventX, int eventY){
        if(eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            Log.d(TAG, "something touched");
            if(eventY >= (y - bitmap.getHeight()/2) && (y <= (y + bitmap.getHeight()/2))){
                // droid touched
                Log.d(TAG, "droid touched");
                setTouched(true);
            } else {
                Log.d(TAG, "droid not touched");
                setTouched(false);
            }
        } else {
            Log.d(TAG, "something not touched");
            setTouched(false);
        }
    }

    public void update() {
           if(!touched){
               x += (speed.getXv() * speed.getXDirection());
               y += (speed.getYv() * speed.getYDirection());
           }
    }
}
