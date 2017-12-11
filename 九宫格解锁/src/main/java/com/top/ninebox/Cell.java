package com.top.ninebox;

/**
 * Created by Administrator on 2016/10/18.
 * 每一个九宫格的圆圈
 */

public class Cell {

    private float centerx;
    private float centery;
    private float r;

    private boolean isSelected;

    public Cell(float centerx, float centery) {
        this.centerx = centerx;
        this.centery = centery;
    }

    public float getCenterx() {
        return centerx;
    }

    public void setCenterx(float centerx) {
        this.centerx = centerx;
    }

    public float getCentery() {
        return centery;
    }

    public void setCentery(float centery) {
        centery = centery;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
