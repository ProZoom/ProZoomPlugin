package com.example.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李阳
 * @创建时间 2017/3/23 - 下午1:11
 * @描述
 * @ 当前版本:
 */

public class broadcasts extends RelativeLayout {

    private Context context;

    private ViewPager viewpager;

    private List<ImageView> mListData;
    private LinearLayout mPoint;
    private int[] srcDatas;

    public broadcasts(Context context) {
        super(context);
        this.context=context;
        initView();

        initData();
        initEvent();
    }



    public broadcasts(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
        initData();
    }

    public broadcasts(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;

        initView();
        initData();
    }

    private void initView() {
        View rootView=View.inflate(context,R.layout.broadcasts_view,null);

        viewpager= (ViewPager) rootView.findViewById(R.id.viewpager_demo);
        mPoint= (LinearLayout) rootView.findViewById(R.id.point_container_demo);
    }

    private void initData() {

        //Listview--->setAdapter----->adapter-----集合list<数据>

        //c初始化数据

        srcDatas=new int[]{R.mipmap.icon_1,
                R.mipmap.icon_2,
                R.mipmap.icon_3,
                R.mipmap.icon_4,
                R.mipmap.icon_5 };

        mListData=new ArrayList<>();
        for(int i=0;i<srcDatas.length;i++){
            //给集合添加数据
            ImageView iv=new ImageView(context);
            iv.setImageResource(srcDatas[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            mListData.add(iv);
            //添加点
            View point=new View(context);
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            if(i!=0){
                params.leftMargin=20;
            }else {
                point.setBackgroundResource(R.drawable.point_selected);
            }
            mPoint.addView(point,params);
        }

        //设置数据的格式
        viewpager.setAdapter(new myAdapter());

        //设置item默认选中中间的
        int middle=(Integer.MAX_VALUE)/2;
        int extra=middle%mListData.size();
        viewpager.setCurrentItem(middle-extra);

    }

    private void initEvent() {
        //设置监听器
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //回调方法，pager滚动时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当页面选中时回调
            @Override
            public void onPageSelected(int position) {
                Log.i("eee","onPageSelected+  "+position);

                position=position%mListData.size();
                //设置选中点的样式
                int itemcount=mPoint.getChildCount();
                for(int i=0;i<itemcount;i++){
                    View view=mPoint.getChildAt(i);
                    view.setBackgroundResource((position==i) ? (R.drawable.point_selected):R.drawable.point_normal);
                }
            }

            //当页面变化时回调
            /*
            *SCROLL_STATE_IDLE   //闲置状态
            *SCROLL_STATE_DRAGGING  ／／拖动状态
            *SCROLL_STATE_SETTLING ／／选择状态
            */
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("eee","onPageScrollStateChanged+   "+state);
            }
        });
    }

    private class myAdapter extends PagerAdapter {

        /*
        *返回的页面的数量
        * */
        @Override
        public int getCount() {
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
            return view==object;
        }

        /*
        * 初始化item
        * */
        @Override
        public Object instantiateItem(android.view.ViewGroup container, int position) {
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
            position=position%mListData.size();
            //销毁移除的item
            ImageView iv= mListData.get(position);
            //记录缓存标记
            viewpager.removeView(iv);
        }
    }

}
