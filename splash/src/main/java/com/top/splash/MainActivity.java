package com.top.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.top.proutils.BaseActivity;
import com.top.splash.lensFocus.LensFocusActivity;
import com.top.splash.rotate3d.Rotate3DActivity;
import com.top.splash.splitActivity.Activity1;
import com.top.splash.viewpager.ViewPagerActivity;
import com.top.splash.zaker.ZakerActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private ListView lv_splash;

    public static String[] item=new String[]{
            "Zaker",
            "viewpager",
            "镜头风格-由远及近",
            "3D翻转效果",
            "中心打开方式"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();

    }


    private void initView() {
        lv_splash=(ListView) findViewById(R.id.lv_splash);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Splash");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.listview_item,
                item);
        lv_splash.setAdapter(adapter);

    }

    private void initEvent() {

        lv_splash.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:
                startActivity(new Intent(this, ZakerActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, LensFocusActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, Rotate3DActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, Activity1.class));
                break;
        }
    }


}
