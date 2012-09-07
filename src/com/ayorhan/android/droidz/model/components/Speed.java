package com.ayorhan.android.droidz.model.components;

/**
 * Created with IntelliJ IDEA.
 * User: yigithan
 * Date: 9/7/12
 * Time: 5:30 PM
 */
public class Speed {

    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_LEFT = -1;
    public static final int DIRECTION_UP = -1;
    public static final int DIRECTION_DOWN = 1;

    private float xv = 1;
    private float yv = 1;

    private int xDirection = DIRECTION_RIGHT;
    private int yDirection = DIRECTION_DOWN;

    public Speed() {
        this.xv = 1;
        this.yv = 1;
    }

    public Speed(float xv, float yv) {
        this.xv = xv;
        this.yv = yv;
    }

    public float getXv() {
        return xv;
    }

    public void setXv(float xv) {
        this.xv = xv;
    }

    public float getYv() {
        return yv;
    }

    public void setYv(float yv) {
        this.yv = yv;
    }

    public int getXDirection() {
        return xDirection;
    }

    public void setXDirection(int xDirection) {
        this.xDirection = xDirection;
    }

    public int getYDirection() {
        return yDirection;
    }

    public void setYDirection(int yDirection) {
        this.yDirection = yDirection;
    }

    public void toggleXDirection(){
        xDirection = xDirection * -1;
    }

    public void toggleYDirection(){
        yDirection = yDirection * -1;
    }
}
