package com.top.animation;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.top.proutils.BaseActivity;

public class MainActivity extends BaseActivity {


    private ListView lv_anim;

    public static String[] item=new String[]{
            "View Animation(视图动画)",
            "alpha",
            "scale",
            "translate",
            "rotate",
            "set",
            "View Animation(属性动画)",
            "animator",
            "objectAnimator ",
            "set"
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
        lv_anim=(ListView) findViewById(R.id.lv_animation);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Animation");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



    }

    private void initData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.listview_item,
                item);
        lv_anim.setAdapter(adapter);

    }

    private void initEvent() {
        lv_anim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 1:
                        Toast.makeText(MainActivity.this,"1",Toast.LENGTH_LONG).show();

                        AlphaAnimation aa=new AlphaAnimation(1,0);
                        aa.setDuration(3000);
                        /** 常用方法 */
                        //aa.setRepeatCount(5);//设置重复次数
                        //aa.setFillAfter(false);//动画执行完后是否停留在执行完的状态
                        //aa.setStartOffset(10);//执行前的等待时间
                        lv_anim.setAnimation(aa);
                        aa.start();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
