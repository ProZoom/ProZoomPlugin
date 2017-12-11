package com.example.menu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.top.proutils.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    ImageView mImvhHome,mLevel2Home;
    boolean isLevel1Display=true;
    boolean isLevel2Display=true;
    boolean isLevel3Display=true;
    RelativeLayout level2,level3,level1;

    int animCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("优酷菜单");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    private void initView() {
        mImvhHome= (ImageView) findViewById(R.id.img_home);
        mLevel2Home= (android.widget.ImageView) findViewById(R.id.level2_menu);
        level1= (RelativeLayout) findViewById(R.id.level1);
        level2= (android.widget.RelativeLayout) findViewById(com.example.menu.R.id.level2);
        level3= (android.widget.RelativeLayout) findViewById(com.example.menu.R.id.level3);
        mImvhHome.setOnClickListener(this);
        mLevel2Home.setOnClickListener(this);

    }


    @Override
    public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_MENU){
            android.util.Log.i("tag","点击了硬件的Menu");
            //如果三个菜单都可见，都隐藏
            if(isLevel1Display&&isLevel2Display&&isLevel3Display){
                hideLevel(level1,100);
                hideLevel(level2,100);
                hideLevel(level3,100);
                isLevel1Display=false;
                isLevel2Display=false;
                isLevel3Display=false;
                return true;
            }
            //如果一二级菜单都可见，隐藏一二级菜单
            if(isLevel1Display&&isLevel2Display){
                hideLevel(level1,100);
                hideLevel(level2,100);
                isLevel1Display=false;
                isLevel2Display=false;
                return true;
            }
            // 如果一级菜单都可见，隐藏一级菜单
            if(isLevel1Display){
                hideLevel(level1,100);
                isLevel1Display=false;
                return true;
            }
            //如果三个都不可见，显示一级菜单
            if(!isLevel1Display&&!isLevel2Display&&!isLevel3Display){
            showLevel(level1,100);
            isLevel1Display=true;
            return true;
        }

        }

        if(keyCode== android.view.KeyEvent.KEYCODE_BACK){
            android.util.Log.i("tag","点击了硬件的Back");
        }
        return super.onKeyUp(keyCode, event);

    }

    @Override
    public void onClick(android.view.View view) {
        if(view==mImvhHome){
            if(animCount>0){//动画中
                return;
            }
            //如果二三级菜单都显示的话，隐藏二级菜单和三级菜单
            if(isLevel2Display&&isLevel3Display){
                hideLevel(level2,100);
                hideLevel(level3,100);
                isLevel2Display=false;
                isLevel3Display=false;
                return;
            }
            //如果二级菜单显示的话，隐藏二级菜单
            if(isLevel2Display&&!isLevel3Display){
                hideLevel(level2,100);
                isLevel2Display=false;
                return;
            }
            //如果二三级菜单都不显示，显示第二菜单
            if(!isLevel3Display&&!isLevel2Display){
                showLevel(level2,100);
                isLevel2Display=true;
                return;
            }
        }else if(view==mLevel2Home){
            if(animCount>0){//动画中
                return;
            }
            // 如果三级菜单可见,隐藏三级菜单
            if (isLevel3Display) {
                hideLevel(level3, 100);

                // 改变状态
                isLevel3Display = false;
            } else {
                showLevel(level3, 0);

                // 改变状态
                isLevel3Display = true;
            }
        }
    }

    private void showLevel(android.widget.RelativeLayout container,long startOffset) {
        //container.setVisibility(android.view.View.VISIBLE);

       /* 参数说明

        public RotateAnimation (float fromDegrees, float toDegrees,
                                int pivotXType, float pivotXValue,
                                int pivotYType, float pivotYValue)
        fromDegrees：旋转的开始角度。

        toDegrees：旋转的结束角度。

        pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。

        pivotXValue：X坐标的伸缩值。

        pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。

        pivotYValue：Y坐标的伸缩值。
        * */
        RotateAnimation anim=
                new RotateAnimation(-180,0,
                        RotateAnimation.RELATIVE_TO_SELF,0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,1.0f);
        anim.setDuration(400);
        anim.setFillAfter(true);
        anim.setStartOffset(startOffset);
        anim.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                animCount++;
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                animCount--;
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
        container.startAnimation(anim);


    }

    //隐藏菜单
    private void hideLevel(android.widget.RelativeLayout container,long startOffset) {
        //container.setVisibility(android.view.View.GONE);
        android.view.animation.RotateAnimation anim =
                new android.view.animation.RotateAnimation(0, -180,
                        android.view.animation.RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        android.view.animation.RotateAnimation.RELATIVE_TO_SELF, 1.0f);
        anim.setDuration(400);
        anim.setFillAfter(true);
        anim.setStartOffset(startOffset);
        anim.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {
                animCount++;
            }

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                animCount--;
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {

            }
        });
        container.startAnimation(anim);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
