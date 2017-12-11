package com.top.gamehandle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import static com.top.gamehandle.BasicUtils.logPrintf;


/*
* 作者：ProZoom
* 时间：2017/8/26-下午5:02
* 描述：
*/

public class ControlSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sfh;

    private Paint bigCirclePaint;
    private Paint smallCirclePaint;
    private Paint textPaint;
    private Paint bitmapPaint;
    public int bigCX, bigCY, bigCR;

    private int smallCX, smallCY, smallCr;
    private int speedX, speedY;
    private int x=200,y=200;
    private int speed = 10;
    private FrameAnimation []spriteAnimations;
    private Bitmap bitmap;
    private Sprite mSprite;

    public static int screenW, screenH;//屏幕尺寸保存
    private boolean isRunning = false;//线程启动标志
    public ControlSurfaceView(Context context) {
        super(context);

//
//        y=screenW/2;
//        x=screenH/2;

        //控制手柄参数设置
        smallCX=250;
        bigCX=smallCX;
        bigCY=screenW-250;
        smallCY=bigCY;
        bigCR=200;
        smallCr=50;

        //画笔参数设置
        bigCirclePaint=new Paint();
        bigCirclePaint.setColor(Color.WHITE);
        bigCirclePaint.setAlpha(100);

        smallCirclePaint=new Paint();
        smallCirclePaint.setColor(Color.RED);
        smallCirclePaint.setAlpha(100);

        textPaint=new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(100);
        textPaint.setTextSize(45);

        bitmapPaint=new Paint();
        bitmapPaint.setAlpha(100);

        //获取动画，图片资源
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        decodeAnimFromImageArray decodeAnimFromImageArray=new decodeAnimFromImageArray();
        spriteAnimations=decodeAnimFromImageArray.initResources(context);
        mSprite=new Sprite(spriteAnimations,speedX,speedY);

        sfh = this.getHolder();
        sfh.addCallback(this);
    }

    /******************************************************/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        logPrintf("surfaceCreated ok");
        isRunning = true;
        DrawThread controlPanelDrawThread = new DrawThread();
        DrawThread bodyPanelDraw=new DrawThread();
        controlPanelDrawThread.start();
        bodyPanelDraw.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        logPrintf("surfaceChanged ok");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        logPrintf("surfaceDestroyed ok");
        isRunning = false;
    }



    private boolean isOutOfCircle(double distance, int R, int r) {

        if(R - r >= distance) {
            return false;
        } else {
            return true;
        }
    }

    private double getDistance(int sx, int sy, int bx, int by) {
        double distance = Math.sqrt(Math.pow(sx - bx, 2) + Math.pow(sy - by, 2));
        return distance;
    }

    private int getSpeedY(double distance, int tmpY) {
        return (int) (speed * (tmpY - bigCY) / distance);
    }
    private int getSpeedX(double distance, int tmpX) {
        // TODO Auto-generated method stub
        return (int) (speed * (tmpX - bigCX) / distance);
    }
    private int getTempSmallY(double distance, int tmpY) {
        return (int) (bigCY + (bigCR - smallCr) * (tmpY - bigCY) / distance);
    }
    private int getTempSmallX(double distance, int tmpX) {
        // TODO Auto-generated method stub

        return (int) (bigCX + (bigCR - smallCr) * (tmpX - bigCX) / distance);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int tmpX = (int) event.getX();
        int tmpY = (int) event.getY();

        if(Math.pow((tmpX-bigCX),2)+Math.pow((tmpY-bigCY),2)<=Math.pow((bigCR+100),2)){
            int action = event.getAction();
            boolean isOut = false;
            double distance = 0;
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    logPrintf("DOWN");
                    distance = getDistance(tmpX, tmpY, bigCX, bigCY);
                    isOut = isOutOfCircle(distance,bigCR,smallCr);
                    if(!isOut) {
                        smallCX = tmpX;
                        smallCY = tmpY;
                    } else {
                        smallCX = getTempSmallX( distance, tmpX);
                        smallCY = getTempSmallY( distance, tmpY);
                    }
                    speedX = getSpeedX(distance, tmpX);
                    speedY = getSpeedY(distance, tmpY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    logPrintf("MOVE");
                    distance = getDistance(tmpX, tmpY, bigCX, bigCY);
                    isOut = isOutOfCircle(distance, bigCR,smallCr);
                    if(!isOut) {
                        smallCX = tmpX;
                        smallCY = tmpY;
                    } else {
                        smallCX = getTempSmallX( distance, tmpX);
                        smallCY = getTempSmallY( distance, tmpY);
                    }
                    speedX = getSpeedX(distance, tmpX);
                    speedY = getSpeedY(distance, tmpY);

                    break;
                case MotionEvent.ACTION_UP:
                    logPrintf("UP");
                    smallCX = bigCX;
                    smallCY = bigCY;
                    speedX = 0;
                    speedY = 0;
                    break;
            }
            return true;
        }else {
            smallCX=bigCX;
            smallCY=bigCY;
            return true;
        }
    }

    /*
     * 实体绘制
     * */
    private void bodyDraw(){
        ////获取画布
        Canvas canvas = null;
        try {
           canvas = sfh.lockCanvas();
            if(canvas != null) {
                canvas.drawColor(Color.BLACK);

                //绘制动画
                mSprite.draw(canvas,x,y,speedX,speedY);

                //绘制控制手柄
                canvas.drawCircle(bigCX,bigCY,bigCR ,bigCirclePaint);
                canvas.drawCircle(smallCX,smallCY,smallCr,smallCirclePaint);
                //绘制受控图片
               // canvas.drawBitmap(bitmap,x,y,bitmapPaint);
                //绘制当前受控的坐标位置及手柄的值
                canvas.drawText("X:"+speedX+"    "+"Y:"+speedY+"             "+"x:"+x+"    "+"y:"+y,50,50,textPaint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(canvas != null) {
                sfh.unlockCanvasAndPost(canvas);
            }
        }
    }

    /*
    * 开线程绘制界面
    * */
    private class DrawThread extends Thread {
        @Override
        public void run() {
            while(isRunning) {
                //图形绘制
                bodyDraw();
                coordinates();
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void coordinates() {
            x=x+speedX;
            y=y+speedY;
            if(x>screenH) x=0;
            if(x<0) x=screenH;
            if(y>screenW) y=0;
            if(y<0) y=screenW;
        }
    }

}
