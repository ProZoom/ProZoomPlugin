package com.top.outticket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.top.proutils.BaseActivity;

public class OutTicketActivity extends BaseActivity {

	private RelativeLayout rl_payInfo;
	private Animation anim;
	private Toolbar mToolbar;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				startAnimation();
				break;

			default:
				break;
			}

		};
	};


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imitate_outticket);
		rl_payInfo = (RelativeLayout) findViewById(R.id.rl_payInfo);
		mToolbar=(Toolbar) findViewById(R.id.toolbar);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("火车出票效果");

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		newThread() ;
	}
	
	
	/**
	 * 测试用的,开启子线程
	 */
	private void newThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

			}
		}).start();
	}

	/**
	 * 启动打印小票动画
	 */
	private void startAnimation() {
		anim = AnimationUtils.loadAnimation(this, R.anim.slide_down_in);
		anim.setDuration(1000);
		anim.setFillAfter(true);
		rl_payInfo.startAnimation(anim);
	}

}
