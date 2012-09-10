package com.ayorhan.android.droidz.model;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/10/12
 * Time: 12:26 PM
 */
public class ElaineAnimated {
    private static final String TAG = ElaineAnimated.class.getSimpleName();

    private Bitmap bitmap;      // png sprite file containing all the frames.
    private Rect sourceRect;    // selection rectangle
    private int frameNr;        // # of frames in animation
    private int currentFrame;   // current frame
    private long frameTicker;   // time of the last frame update
    private int framePeriod;    // miliseconds between each frame (1000/fps)

    private int spriteWidth;
    private int spriteHeight;

    private int x;
    private int y;

    public ElaineAnimated(Bitmap bitmap, int x, int y, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0l;
    }
}

