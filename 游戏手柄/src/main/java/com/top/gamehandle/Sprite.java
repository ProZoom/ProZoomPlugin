package com.top.gamehandle;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Administrator on 2016/10/6.
 */

public class Sprite {
    public static final int DOWN = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;

    public float x;
    public float y;
    public int width;
    public int height;
    //精灵行走速度
    public double speed;
    //精灵当前行走方向
    public int direction;
    //精灵四个方向的动画
    public FrameAnimation[] frameAnimations;

    public Sprite(FrameAnimation[] frameAnimations, int ctrol_X, int ctrol_Y) {
        this.frameAnimations = frameAnimations;
        this.x = ctrol_X;
        this.y = ctrol_Y;
    }

    /**
     * 根据精灵的当前位置判断是否改变行走方向
     */
    public void setDirection(int x,int y) {
        if (x>=0&&(y<=x&&y>=-x)){
            this.direction = Sprite.RIGHT;
        }else if(x<0&&(y>x&&y<-x)){
            this.direction = Sprite.LEFT;
        }else if(y>0&&(x>-y&&x<y)){
            this.direction = Sprite.DOWN;
        }else {
            this.direction = Sprite.UP;
        }
    }

    public void draw(Canvas canvas,int spritex,int spritey,int x,int y) {
        setDirection(x,y);
        FrameAnimation frameAnimation = frameAnimations[this.direction];
        Bitmap bitmap = frameAnimation.nextFrame();
        if (null != bitmap) {
            canvas.drawBitmap(bitmap, spritex, spritey, null);
        }
    }
}
