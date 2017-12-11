package com.zoom.newbubble.libs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.zoom.newbubble.utils.BubbleUtils;
import com.zoom.newbubble.utils.GeometryUtil;


@SuppressLint({ "ClickableViewAccessibility", "NewApi" })
public class BubbleView extends View {

	// 1.1 绘制连线(贝塞尔曲线)
	// 固定圆的半径
	private float stickCricleRadiusMax;
	// 1.1 绘制连线(贝塞尔曲线)
	// 固定圆的半径
	private float stickCricleRadiusMin;

	// 1.1 绘制连线(贝塞尔曲线)
	// 拖拽圆的半径(半径固定)
	private float dragCricleRadius;

	// 固定圆的坐标
	private PointF stickCenter;
	// 拖拽圆的坐标
	private PointF dragCenter;

	// 固定圆和拖拽圆圆心之间的距离(临界距离)
	// 动态计算
	private float maxDistance;

	// 气泡大小
	private Rect bubbleSize;

	// 当前固定圆的半径
	private float currentStickRadius;

	// 绘制固定圆画笔
	private Paint redPaint;

	// 状态栏的高度
	private int statusHeight;
	
	
	private String text;
	private Paint textPaint;
	
	public void setText(String text) {
		this.text = text;
	}

	public BubbleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initParams();
	}

	public BubbleView(Context context) {
		super(context);
		initParams();
	}

	// 初始化气泡参数
	private void initParams() {
		// 1.1 绘制连线(贝塞尔曲线)
		// 初始化固定圆的半径
		this.stickCricleRadiusMax = BubbleUtils
				.dipToDimension(10, getContext());
		this.stickCricleRadiusMin = BubbleUtils.dipToDimension(2, getContext());
		// 1.1 绘制连线(贝塞尔曲线)
		// 初始化拖拽圆的半径
		this.dragCricleRadius = this.stickCricleRadiusMax;

		// 1.1 绘制连线(贝塞尔曲线)
		// 初始化两个圆心之间的最大距离(临界点)
		this.maxDistance = BubbleUtils.dipToDimension(80, getContext());

		// 初始化气泡的大小
		this.bubbleSize = new Rect(0, 0, 50, 50);

		this.statusHeight = BubbleUtils.getStatusBarHeight(getContext());

		this.redPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.redPaint.setColor(Color.RED);
		
		this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		this.textPaint.setColor(Color.WHITE);
		this.textPaint.setTextAlign(Align.CENTER);
		this.textPaint.setTextSize(dragCricleRadius);

		setCenterPoint(100, 100);
	}

	// 当前触摸点(当我们触摸按下)
	public void setCenterPoint(float x, float y) {
		// 初始化固定圆和拖拽圆的默认位置(默认重叠)
		this.stickCenter = new PointF(x, y);
		this.dragCenter = new PointF(x, y);
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!isDisappear) {
			// 有问题吗？（写死？）
			// 通过处理状态栏的高度
			// 当我们添加到了Windows上就不会出现这个标题栏的高度问题(只有状态栏的高度)
			canvas.translate(0, -this.statusHeight);

			if (!isOutOfRange) {
				// 1.1 绘制连线(贝塞尔曲线)
				ShapeDrawable shapeDrawable = drawShapeDrawable();
				// 设置画布大小
				shapeDrawable.setBounds(bubbleSize);
				// 绘制
				shapeDrawable.draw(canvas);

				// 1.2 绘制固定圆
				canvas.drawCircle(this.stickCenter.x, stickCenter.y,
						currentStickRadius, this.redPaint);
			}

			// 1.3 绘制拖拽圆
			canvas.drawCircle(this.dragCenter.x, this.dragCenter.y,
					dragCricleRadius, redPaint);

			// 1.4 绘制显示文本
			//目的：文本在红色消息气泡中进行居中
			if(!TextUtils.isEmpty(text)){
				canvas.drawText(text, dragCenter.x, dragCenter.y + dragCricleRadius / 2, textPaint);
			}
		}
	}

	// 1.1 绘制连线(贝塞尔曲线)
	private ShapeDrawable drawShapeDrawable() {
		// 分为很多步骤
		// 第一步：计算两个圆心直接的距离(计算固定圆和拖拽圆圆心之间距离)
		// 已知：圆心的坐标，圆的半径，动态计算圆心之间的距离
		float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,
				stickCenter);
		// 不断的拖拽，当前半径不断的发送改变(固定圆的大小是不是会发生改变)
		this.currentStickRadius = getCurrentRadius(distance);

		// 第二步：已知两个圆心之间的距离，圆心坐标，还有两个圆半径，计算正切值？
		// 高中几何？
		// 目的：为了实现贝塞尔曲线斜率，计算两个圆心之间的连线的垂线，与两个圆的交点？
		// 首先计算斜率?
		// 计算邻边
		float xDistance = stickCenter.x - dragCenter.x;
		// 计算对边
		float yDistance = stickCenter.y - dragCenter.y;
		// 计算斜率
		double tan = 0;
		// 分母不能够为0
		if (xDistance != 0) {
			tan = yDistance / xDistance;
		}

		// 第三步：获取两条垂线和两个圆之间的四个交点坐标
		// 获取交点的目的是什么？
		// 绘制贝塞尔曲线(起点和终点)
		// 两条贝塞尔曲线(四个点)
		// 条件：已知圆心的坐标，半径，斜率，计算垂线与圆的交点坐标？
		// 获取固定圆交点
		PointF[] stickPointArray = GeometryUtil.getIntersectionPoints(
				this.stickCenter, this.currentStickRadius, tan);

		// 获取拖拽圆交点
		PointF[] dragPointArray = GeometryUtil.getIntersectionPoints(
				this.dragCenter, this.dragCricleRadius, tan);

		// 第四步：绘制贝塞尔曲线
		// 分析：贝塞尔曲线原理
		// 绘制贝塞尔曲线需要哪些条件?
		// 第一个条件：起点、终点、控制点
		// 1、获取控制点坐标
		PointF pointByPercent = GeometryUtil.getPointByPercent(dragCenter,
				stickCenter, 0.618f);

		// 2、绘制第一条贝塞尔曲线(轨迹)
		Path path = new Path();
		// 绘制起点坐标
		path.moveTo(stickPointArray[0].x, stickPointArray[0].y);
		// 绘制终点坐标
		// quadTo：用于绘制贝塞尔曲线
		// 第一个参数：控制点X坐标
		// 第二个参数：控制点Y坐标
		// 第三个参数：终点X坐标
		// 第四个参数：终点Y坐标
		path.quadTo(pointByPercent.x, pointByPercent.y, dragPointArray[0].x,
				dragPointArray[0].y);

		// 3、绘制第二条贝塞尔曲线(轨迹)
		// 绘制起点坐标
		path.lineTo(dragPointArray[1].x, dragPointArray[1].y);
		// 绘制终点坐标
		// 第一个参数：控制点X坐标
		// 第二个参数：控制点Y坐标
		// 第三个参数：终点X坐标
		// 第四个参数：终点Y坐标
		path.quadTo(pointByPercent.x, pointByPercent.y, stickPointArray[1].x,
				stickPointArray[1].y);
		// 绘制完毕
		path.close();

		// 创建图片
		ShapeDrawable shapeDrawable = new ShapeDrawable(new PathShape(path,
				this.bubbleSize.width(), bubbleSize.height()));
		shapeDrawable.getPaint().setColor(Color.RED);
		return shapeDrawable;
	}

	private float getCurrentRadius(float distance) {
		// distance有可能超出了我们拖拽的临界范围
		if (distance > this.maxDistance) {
			distance = maxDistance;
		}
		// 拖拽的最小范围(计算变化的百分比)
		// 固定圆的大小由拖拽的距离百分比计算
		// 范围：(0.2-1.0之间)
		// distance越大，那么fraction越大
		// distance越小，那么fraction越小
		// 例如：范围10
		// 控制在(2,10)
		// 距离越大，百分比也就越大
		// 计算当前拖拽的百分比
		float fraction = 0.2f + (distance / maxDistance) * 0.8f;

		// 圆的大小：相反
		return GeometryUtil.evaluateValue(fraction, stickCricleRadiusMax,
				stickCricleRadiusMin);
	}

	public void updateDragCenterPoint(float x, float y) {
		this.dragCenter.x = x;
		this.dragCenter.y = y;
		invalidate();
	}

	// 是否超出了范围
	private boolean isOutOfRange = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (MotionEventCompat.getActionMasked(event)) {
		case MotionEvent.ACTION_DOWN:
			// 更新当前的坐标
			updateDragCenterPoint(event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_MOVE:
			// 4.1 实现控制偏移量，超出范围，弹簧消失，否在显示
			float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,
					stickCenter);
			if (distance > this.maxDistance) {
				// 标记
				isOutOfRange = true;
				updateDragCenterPoint(event.getRawX(), event.getRawY());
				return false;
			}
			// 你是否绘制固定圆（说白了：就是说如果你超出了范围，那么我就不去绘制固定圆和弹簧）
			isOutOfRange = false;
			// 拖拽的时候，判断是否超出了范围
			updateDragCenterPoint(event.getRawX(), event.getRawY());
			break;
		case MotionEvent.ACTION_UP:
			// 气泡要消失(最后实现)
			handleActionUp();
			break;

		default:
			break;
		}
		return true;
	}

	// 4.2 实现控制气泡的消失
	// 当我们手势弹起的时候，我们要让这个气泡消失
	private void handleActionUp() {
		// 消失两种情况？
		// 第一种情况：在我们的拖拽的范围之内
		// 第二种情况：超出了我们的拖拽的范围
		if (isOutOfRange) {
			// 说白了有存在两种情况
			// 第一种：超出了范围，没有拖拽回到偏移量范围之内，再弹起
			// 第二种：超出了范围，后来又拖拽回到了偏移范围之内，再弹起
			float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,
					stickCenter);
			if (distance > this.maxDistance) {
				// 超出了范围
				// 执行气泡消失的动画
				disappear();
			} else {
				// 回来了
				// 执行气泡还原原始位置动画(回弹效果：来回晃动)
				backAnimation();
			}
		} else {
			backAnimation();
		}
	}

	private OnDisapperListener onDisapperListener;
	private boolean isDisappear;

	public void setOnDisapperListener(OnDisapperListener onDisapperListener) {
		this.onDisapperListener = onDisapperListener;
	}

	// 执行气泡还原原始位置动画(回弹效果：来回晃动)
	private void backAnimation() {
		// 属性动画
		// 参数一：动画执行时间（1秒）
		ValueAnimator animator = ValueAnimator.ofFloat(1.0f);
		// 插值器(回弹插值器、加速度等等...)
		// 向前甩一定值后再回到原来位置
		// 参数：甩动的偏移大小
		animator.setInterpolator(new OvershootInterpolator(3.0f));

		// 监听动画执行的过程(执行回调)
		// 起点和终点
		// 起点(当前拖拽弹起的点)
		final PointF startPoint = new PointF(this.dragCenter.x,
				this.dragCenter.y);
		// 终点(固定的点)
		final PointF endPoint = new PointF(this.stickCenter.x,
				this.stickCenter.y);
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// 可以告诉我们动画执行的进度
				// 根据动画执行的百分比，动态的更新气泡回弹的位置(过度动画)
				float fraction = animation.getAnimatedFraction();
				// 之前我们是手动拖拽，现在交给了动画帮助我们完成位置还原
				// 动态计算拖拽圆的圆心坐标(以前是通过手势拖拽实现，那么现在动画框架自动完成)
				PointF percent = GeometryUtil.getPointByPercent(startPoint,
						endPoint, fraction);
				// 更新拖拽圆的位置
				updateDragCenterPoint(percent.x, percent.y);
			}
		});
		animator.setDuration(500);
		animator.start();

		animator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// 动画结束，显示静态点或者隐藏，或者其他的逻辑处理(更新ListView、删除数据库数据等等...)
				if (onDisapperListener != null) {
					onDisapperListener.onReset(isOutOfRange, isDisappear);
				}
			}
		});

	}

	// 执行消息气泡消失动画
	private void disappear() {
		this.isDisappear = true;
		if (this.onDisapperListener != null) {
			this.onDisapperListener.onDisapper(dragCenter);
		}
		invalidate();
	}

}
