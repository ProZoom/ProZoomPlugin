package com.top.ninebox;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.top.proutils.BaseActivity;

/*
* 作者：ProZoom
* 时间：2017/8/26-下午4:49
* 描述：九宫格
*/
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionbar=getSupportActionBar();
        actionbar.setTitle("九宫格解锁");
        if (actionbar!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
