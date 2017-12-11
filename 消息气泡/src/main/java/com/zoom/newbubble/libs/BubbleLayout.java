package com.zoom.newbubble.libs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

//自定义布局(专门用于气泡在Window爆炸动画)
public class BubbleLayout extends FrameLayout {

	public BubbleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BubbleLayout(Context context) {
		super(context);
	}
	
	//当前触摸的坐标
	private float centerX;
	private float centerY;
	
	public void setCenterPoint(float x,float y){
		this.centerX = x;
		this.centerY = y;
	}
	
	//接下来你需要重新摆放子控件位置(创建布局，需要指定气泡显示位置：就是当前触摸的位置)
	//onLayout：摆放控件位置
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if(getChildCount() == 0){
			return;
		}
		//取第一个View(说白了就是BubbleView：气泡View)
		View view = getChildAt(0);
		//计算摆放位置
		//第一步：获取到当前这个气泡View宽度和高度
		int bubbleWidth = view.getMeasuredWidth();
		int bubbleHeight = view.getMeasuredHeight();
		int viewLeft = (int)(centerX - bubbleWidth/2);
		int viewTop = (int)(centerY - bubbleHeight / 2);
		int viewRight = viewLeft + bubbleWidth;
		int viewBottom = viewTop + bubbleHeight;
		view.layout(viewLeft, viewTop ,viewRight, viewBottom);
	}
	
	
	
}
