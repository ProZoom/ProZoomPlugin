package com.top.network.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.top.network.R;
import com.top.proutils.BaseActivity;

import okhttp3.OkHttpClient;

/*
* 作者：ProZoom
* 时间：2017/9/7 - 上午8:31
* 描述：
*/
public class OkHttpSample extends BaseActivity {

    OkHttpClient client = new OkHttpClient();

    private String urlPath = "http://192.168.0.104:8080/test";

    OkHttpUtils okHttpUtils;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        okHttpUtils = OkHttpUtils.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {


            }
        }).start();
    }

}
