package com.zoom.newbubble.libs;

import android.graphics.PointF;

//回调监听(提供给客户端)
public interface OnDisapperListener {

	//参数一：是否超出范围
	//参数二：是否消失了
	public void onReset(boolean isOutOfRange, boolean isDisappear);
	
	//执行爆炸动画
	public void onDisapper(PointF point);
}
