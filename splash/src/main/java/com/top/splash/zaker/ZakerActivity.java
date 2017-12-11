package com.top.splash.zaker;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.top.splash.R;


public class ZakerActivity extends Activity implements OnClickListener{
	
	private Button btnBelow,btnAbove;
	private TextView tvHint;
	private int lastDownY = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_zaker);
		initView();
		setListener();
	}


	public void initView() {
		btnBelow = (Button)this.findViewById(R.id.btn_Below);
		btnAbove = (Button)this.findViewById(R.id.btn_above);
		tvHint   = (TextView)this.findViewById(R.id.tv_hint);
		
		Animation ani = new AlphaAnimation(0f,1f);
		ani.setDuration(1500);
		ani.setRepeatMode(Animation.REVERSE);
		ani.setRepeatCount(Animation.INFINITE);
		tvHint.startAnimation(ani);
		
	}

	public void setListener() {
		btnBelow.setOnClickListener(this);
		btnAbove.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_Below:
			Toast.makeText(ZakerActivity.this, "这是下面一层按钮", 1000).show();
			break;
		case R.id.btn_above:
			Toast.makeText(ZakerActivity.this, "这是上面一层按钮", 1000).show();	
			break;

		default:
			break;
		}
		
	}
}
