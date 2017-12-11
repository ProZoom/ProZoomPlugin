package com.zoom.newbubble.libs;


import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zoom.newbubble.R;

//我要自定义监听器（实现了OnTouchListener）
//重点：封装
public class OnBubbleListener implements OnTouchListener, OnDisapperListener {

	private Context context;
	private View pointView;
	private BubbleView bubbleView;
	private WindowManager windowManager;
	private WindowManager.LayoutParams params;
	
	private Handler handler = new Handler();

	// pointView:静态的红点
	public OnBubbleListener(Context context, View pointView) {
		this.context = context;
		this.pointView = pointView;

		// 创建气泡
		this.bubbleView = new BubbleView(context);

		windowManager = (WindowManager) this.context
				.getSystemService(Context.WINDOW_SERVICE);
		params = new WindowManager.LayoutParams();
		// Window背景颜色透明(指定一个样式)
		params.format = PixelFormat.TRANSLUCENT;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// 触摸按下立马创建气泡，将气泡添加到Window上
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			// 我们要去拦截父容器事件(ListView、GridView等等...)
			ViewParent parent = v.getParent();
			// 请求父容器不要拦截触摸事件，传递给子View
			parent.requestDisallowInterceptTouchEvent(true);
			
			// 隐藏静态的红点
			this.pointView.setVisibility(View.INVISIBLE);

			// 显示拖拽的红点(当前触摸的位置，你触摸在哪里，那么这个拖拽的红点初始位置就在那个地方)
			this.bubbleView.setCenterPoint(event.getRawX(), event.getRawY());
			this.bubbleView.setText("10");

			// 添加到Windows上面
			this.windowManager.addView(bubbleView, params);
			
			//设置客户端回调监听
			this.bubbleView.setOnDisapperListener(this);
			break;

		default:
			break;
		}
		// 拦截触摸事件
		//你需要将触摸事件传递给红色气泡
		this.bubbleView.onTouchEvent(event);
		return true;
	}

	@Override
	public void onReset(boolean isOutOfRange, boolean isDisappear) {
		// 显示原始的静态的红点,移除Window上的消息气泡
		this.windowManager.removeView(bubbleView);
		this.pointView.setVisibility(View.VISIBLE);
	}

	@Override
	public void onDisapper(PointF point) {
		if (this.bubbleView.getParent() != null) {
			// 第一步：删除气泡
			this.windowManager.removeView(bubbleView);

			// 第二步：创建气泡爆炸的布局
			final BubbleLayout bubbleLayout = new BubbleLayout(context);
			bubbleLayout.setCenterPoint(point.x, point.y);

			// 第三步：创建气泡爆炸ImageView
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(R.drawable.anim_bubble_pop);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getDrawable();

			// 第四步：将气泡添加到Layout上
			bubbleLayout.addView(imageView, new FrameLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			//第五步：将气泡布局绑定到Windows上
			this.windowManager.addView(bubbleLayout, params);
			
			//第六步：启动动画
			animationDrawable.start();
			
			//第七步：动画结束，移除气泡布局(延时加载)
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					windowManager.removeView(bubbleLayout);
				}
			}, 500);
		}
	}

}
