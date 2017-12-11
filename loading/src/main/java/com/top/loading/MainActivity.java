package com.top.loading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.top.loading.ProgressBar.ProgressBar_Color;
import com.top.loading.loadingDialog.LoadingDialog;
import com.top.proutils.BaseActivity;


/*
* 作者：ProZoom
* 时间：2017/7/23 - 下午3:06
* 描述：
*/
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Loading");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    public void loadingDialog(View view){
        LoadingDialog dialog;
        dialog = new LoadingDialog(this);
        dialog.show();
    }

    public void LoadingProgressBar(View view){

        startActivity(new Intent(this, ProgressBar_Color.class));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
