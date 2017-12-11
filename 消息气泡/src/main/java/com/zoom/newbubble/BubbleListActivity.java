package com.zoom.newbubble;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.top.proutils.BaseActivity;
import com.zoom.newbubble.libs.OnBubbleListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class BubbleListActivity extends BaseActivity {

	private List<String> messageList;

	// Map集合用来：保存已经被删除条目id
	// 将来我们要根据这个id，删除消息
	// 哪些条目已经被删除了，我就没有必要显示消息红点
	private HashSet<Integer> mRemoved = new HashSet<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bubble);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("消息气泡");

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		ListView lv_bubble = (ListView) findViewById(R.id.lv_bubble);

		// 给我们当前点击的视图添加气泡监听
		// 目的：为了创建气泡
		messageList = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			messageList.add("item_" + i);
		}
		lv_bubble.setAdapter(new MessageAdapter());
	}

	class MessageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return messageList.size();
		}

		@Override
		public Object getItem(int position) {
			return messageList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item,
						parent, false);
				viewHolder.textView = (TextView) convertView
						.findViewById(R.id.tv_message_bubble);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// 判断当前条目是否显示消息红点
			// 如果当前消息条目，之前已经被删除了，那么这个红点就不显示了
			boolean visiable = mRemoved.contains(position);
			viewHolder.textView.setVisibility(visiable ? View.GONE
					: View.VISIBLE);

			if (!visiable) {
				//静态红色的点
				viewHolder.textView.setText(String.valueOf(position));

				// 给我们的红点添加拖拽视图
				OnBubbleListener onBubbleListener = new OnBubbleListener(
						BubbleListActivity.this, viewHolder.textView) {
					@Override
					public void onDisapper(PointF point) {
						super.onDisapper(point);
						//消息气泡被干掉了
						mRemoved.add(position);
						//刷新adapter
						notifyDataSetChanged();
					}

					@Override
					public void onReset(boolean isOutOfRange,
							boolean isDisappear) {
						super.onReset(isOutOfRange, isDisappear);
						// 重置视图
						notifyDataSetChanged();
					}
				};
				// 绑定
				viewHolder.textView.setOnTouchListener(onBubbleListener);
			}

			return convertView;
		}

		class ViewHolder {
			TextView textView;
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
