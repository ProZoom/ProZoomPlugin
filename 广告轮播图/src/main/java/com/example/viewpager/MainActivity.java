package com.example.viewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.top.proutils.BaseActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {


    ViewPager viewpager;
    List<ImageView> mListData;
    LinearLayout mPoint;
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("广告轮播图");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        viewpager= (ViewPager) findViewById(R.id.viewpager);

        mPoint= (LinearLayout) findViewById(R.id.point_container);
        tv_title= (TextView) findViewById(R.id.tv_title);

        //Listview--->setAdapter----->adapter-----集合list<数据>

        //c初始化数据
        int[] imageResource= new int[]{R.mipmap.icon_1,
                        R.mipmap.icon_2,
                        com.example.viewpager.R.mipmap.icon_3,
                        com.example.viewpager.R.mipmap.icon_4,
                        com.example.viewpager.R.mipmap.icon_5};
        final String[] title=new String[]{getString(com.example.viewpager.R.string.title_one),
                getString(com.example.viewpager.R.string.title_two),
                getString(com.example.viewpager.R.string.title_three),
                getString(com.example.viewpager.R.string.title_four),
                getString(com.example.viewpager.R.string.title_five)};

        mListData=new ArrayList<>();
        for(int i=0;i<imageResource.length;i++){
            //给集合添加数据
            ImageView iv=new android.widget.ImageView(this);
            iv.setImageResource(imageResource[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mListData.add(iv);
            //添加点
            View point=new View(this);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            if(i!=0){
                params.leftMargin=20;
            }else {
                point.setBackgroundResource(R.drawable.point_selected);
                tv_title.setText(title[i]);
            }
            mPoint.addView(point,params);
        }

        //设置数据的格式
        viewpager.setAdapter(new myadapter());

        //设置item默认选中中间的
        int middle=(Integer.MAX_VALUE)/2;
        int extra=middle%mListData.size();
        viewpager.setCurrentItem(middle-extra);

        //设置监听器
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //回调方法，pager滚动时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //android.util.Log.i("wwww","positionOffset=    "+positionOffset);
                //android.util.Log.i("wwww","positionOffsetPixels=    "+positionOffsetPixels);

            }

            //当页面选中时回调
            @Override
            public void onPageSelected(int position) {
                android.util.Log.i("eee","onPageSelected+  "+position);

                position=position%mListData.size();


                //设置选中点的样式
                int itemcount=mPoint.getChildCount();
                for(int i=0;i<itemcount;i++){
                    View view=mPoint.getChildAt(i);
                   /* if(position==i){
                        view.setBackgroundResource(com.example.viewpager.R.drawable.point_selected);
                    }else {
                        view.setBackgroundResource(com.example.viewpager.R.drawable.point_normal);
                    }*/
                view.setBackgroundResource((position==i) ? (R.drawable.point_selected):R.drawable.point_normal);
                }
                tv_title.setText(title[position]);
            }

            //当页面变化时回调
            /*
            *SCROLL_STATE_IDLE   //闲置状态
            *SCROLL_STATE_DRAGGING  ／／拖动状态
            *SCROLL_STATE_SETTLING ／／选择状态
            */
            @Override
            public void onPageScrollStateChanged(int state) {
                android.util.Log.i("eee","onPageScrollStateChanged+   "+state);
            }
        });


    }


    class myadapter extends android.support.v4.view.PagerAdapter{

        /*
        *返回的页面的数量
        * */
        @Override
        public int getCount() {
            android.util.Log.i("tag","getCount");
            if(mListData!=null){
                return  Integer.MAX_VALUE;
            }
            return 0;
        }


        /*
        *标记方法，用来判断缓存标记
        * view  要显示的洁面
        * object  标记
        * */
        @Override
        public boolean isViewFromObject(android.view.View view, Object object) {
            android.util.Log.i("tag","isviewFromObject");
            return view==object;
        }

        /*
        * 初始化item
        * */
        @Override
        public Object instantiateItem(android.view.ViewGroup container, int position) {
            android.util.Log.i("tag","instantiateitem");
            position=position%mListData.size();
            //用来添加要显示的view
            ImageView iv=mListData.get(position);
            //用来要添加显示的view
            viewpager.addView(iv);

            return iv;
        }

        /*
        * 销毁item条目
        * */
        @Override
        public void destroyItem(android.view.ViewGroup container, int position, Object object) {
            android.util.Log.i("tag","destroyitem");
            position=position%mListData.size();
            //销毁移除的item
            ImageView iv= mListData.get(position);

            //记录缓存标记
            viewpager.removeView(iv);
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
