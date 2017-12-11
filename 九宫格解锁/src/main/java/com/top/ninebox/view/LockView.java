package com.top.ninebox.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.top.ninebox.Cell;


/*
* 作者：ProZoom
* 时间：2017/8/26-下午4:49
* 描述：九宫格
*/

public class LockView extends View {

    private static final int DEFAULT_CELL_WIDTH=60;
    private static final int DEFAULT_CELL_SPACE=DEFAULT_CELL_WIDTH>>1;;
    private static final int DEFAULT_CELL_STROKE_WIDTH=2;

    private Cell mCell[]=new Cell[9];
    private int mCellWidth=50;
    private int mCellRadus=50;
    private int mCellStrokeWidth;

    private Paint mPaintNormal;
    private Paint mPaintSelected;

    private  int mWidth;
    private int mHeight;

    int mSpace=150;

    private float mCurrentX;
    private float mCurrentY;

    private boolean mFinish=false;//手势操作1结束

    private StringBuffer mSbSelected=new StringBuffer(20);



    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaintNormal=new Paint();
        mPaintNormal.setColor(Color.WHITE);
        mPaintNormal.setStrokeWidth(mCellStrokeWidth);
        mPaintNormal.setStyle(Paint.Style.STROKE);
        mPaintNormal.setAntiAlias(true);

        mPaintSelected=new Paint();
        mPaintSelected.setColor(Color.RED);
        mPaintSelected.setStrokeWidth(mCellStrokeWidth);
        mPaintSelected.setStyle(Paint.Style.STROKE);
        mPaintSelected.setAntiAlias(true);

        //初始化九宫格
        Cell cell;
        float x,y;
        for (int i=0;i<9;i++){
            x=mSpace*(i%3+1)+mCellRadus+mCellWidth*(i%3);
            y=mSpace*(i/3+1)+mCellRadus+mCellWidth*(i/3);
            cell=new Cell(x,y);
            mCell[i]=cell;
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);

//        画九宫格
        for(int i=0;i<9;i++){
            canvas.drawCircle(mCell[i].getCenterx(),mCell[i].getCentery(),mCellRadus,mCell[i].isSelected()?mPaintSelected:mPaintNormal);
        }
        //划线
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        //没有被选中任何圆圈不需要划线
        if("".equals(mSbSelected.toString())){return;}

        String[] selectedIndexs=mSbSelected.toString().split(",");
        //先找到第一个小圆圈
        Cell cell=mCell[Integer.valueOf(selectedIndexs[0])];
        Cell nextCell;

        //把之前选中的圆圈用圆心连接起来
        if(selectedIndexs.length>1){
            for(int i=1;i<selectedIndexs.length;i++){
                nextCell=mCell[Integer.valueOf(selectedIndexs[i])];
                canvas.drawLine(cell.getCenterx(),cell.getCentery(),nextCell.getCenterx(),nextCell.getCentery(),mPaintSelected);
                //每画完一次线，要改遍起始圆
                cell=nextCell;
            }
        }
        //当前位置与之前选中的圆圆心连线
        if(!mFinish){
            canvas.drawLine(cell.getCenterx(),cell.getCentery(),mCurrentX,mCurrentY,mPaintSelected);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mFinish){
                    for(int i=0;i<9;i++){//把所有小圆圈复位
                        mCell[i].setSelected(false);
                    }
                    mFinish=false;
                    mSbSelected.delete(0,mSbSelected.length());
                    invalidate();
                    return false;
                }
                handleDownEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                mFinish=true;
                invalidate();
                Toast.makeText(getContext(),mSbSelected.toString(),Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //Move事件
    private void handleMoveEvent(MotionEvent event) {

        //先获取小圆圈的位置
        int index=findCellIndex(event.getX(),event.getY());
        if(index!=-1){
            mCell[index].setSelected(true);
            mSbSelected.append(index).append(",");
        }
        //不管是否被选中，都刷新
        invalidate();
        mCurrentX=event.getX();
        mCurrentY=event.getY();

    }

    //处理down事件
    private void handleDownEvent(MotionEvent event) {

        mCurrentX=event.getX();
        mCurrentY=event.getY();

        //先获取小圆圈的位置
        int index=findCellIndex(mCurrentX,mCurrentY);
        if(index!=-1){//只有当你选中这个圆圈才舒心界面
            mCell[index].setSelected(true);
            mSbSelected.append(index).append(",");
            invalidate();
        }
    }

    //寻找小圆圈的位置
    private int findCellIndex(float mCurrentX, float mCurrentY) {
        int result=-1;
        float cellX;
        float cellY;
        for(int i=0;i<9;i++){
            if(mCell[i].isSelected()){
                continue;
            }

            cellX=mCell[i].getCenterx();
            cellY=mCell[i].getCentery();

            float tempX=cellX-mCurrentX;
            float tempY=cellY-mCurrentY;

            float distance= (float) Math.sqrt(tempX*tempX+tempY*tempY);
            if(distance<=mCellRadus){
                result=i;
                break;
            }
        }
        return result;
    }
}
