package com.top.circlemenu;

/*
 * Copyright 2013 Csaba Szugyiczki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.top.proutils.BaseActivity;

/**
 * 圆形旋转菜单(自定义Layout+ImageView)
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity implements CircleLayout.OnItemSelectedListener, CircleLayout.OnItemClickListener {
	private TextView selectedTextView;
	private CircleLayout circleMenu;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_circleimage_main);

		ActionBar actionBar=getSupportActionBar();
		actionBar.setTitle("CircleMenu");

		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		initView();
		setListener();
	}


	public void initView() {
		circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);

		selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
		
	}

	public void setListener() {
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
		
	}
	
	@Override
	public void onItemSelected(View view, int position, long id, String name) {		
		selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.app_name) + " " + name, Toast.LENGTH_SHORT).show();
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
