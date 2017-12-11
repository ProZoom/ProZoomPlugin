package com.zoom.thirdlevelmenu;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.top.proutils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    List<Node> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("三级菜单");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initDatas();
        NodeTreeView mp = new NodeTreeView(this);

        mp.setData(mDatas);
        setContentView(mp);


    }


    //准备好数据源(开发获取Json  xml数据源解析)
    private void initDatas() {
        mDatas = new ArrayList<>();
        // id , pid , label , 其他属性
        mDatas.add(new Node(1, 0, "游戏"));
        mDatas.add(new Node(2, 0, "文档"));
        mDatas.add(new Node(3, 0, "程序"));
        mDatas.add(new Node(4, 0, "视频"));
        mDatas.add(new Node(5, 0, "音乐"));
        mDatas.add(new Node(6, 0, "照片"));
        mDatas.add(new Node(7, 0, "学习"));
        mDatas.add(new Node(8, 0, "娱乐"));
        mDatas.add(new Node(9, 0, "美食"));
        mDatas.add(new Node(10, 0, "备忘录"));

        mDatas.add(new Node(11, 1, "DOTA"));
        mDatas.add(new Node(12, 1, "LOL"));
        mDatas.add(new Node(13, 1, "war3"));

        mDatas.add(new Node(14, 11, "剑圣"));
        mDatas.add(new Node(15, 11, "敌法"));
        mDatas.add(new Node(16, 11, "影魔"));

        mDatas.add(new Node(17, 12, "德玛西亚"));
        mDatas.add(new Node(18, 12, "潘森"));
        mDatas.add(new Node(19, 12, "蛮族之王"));

        mDatas.add(new Node(20, 13, "人族"));
        mDatas.add(new Node(21, 13, "兽族"));
        mDatas.add(new Node(22, 13, "不死族"));

        mDatas.add(new Node(23, 2, "需求文档"));
        mDatas.add(new Node(24, 2, "原型设计"));
        mDatas.add(new Node(25, 2, "详细设计文档"));

        mDatas.add(new Node(26, 23, "需求调研"));
        mDatas.add(new Node(27, 23, "需求规格说明书"));
        mDatas.add(new Node(28, 23, "需求报告"));

        mDatas.add(new Node(29, 24, "QQ原型"));
        mDatas.add(new Node(30, 24, "微信原型"));

        mDatas.add(new Node(31, 25, "刀塔传奇详细设计"));
        mDatas.add(new Node(32, 25, "羽禾直播设计"));
        mDatas.add(new Node(33, 25, "YNedut设计"));
        mDatas.add(new Node(34, 25, "微信详细设计"));

        mDatas.add(new Node(35, 3, "面向对象"));
        mDatas.add(new Node(36, 3, "非面向对象"));

        mDatas.add(new Node(37, 35, "C++"));
        mDatas.add(new Node(38, 35, "JAVA"));
        mDatas.add(new Node(39, 36, "Javascript"));
        mDatas.add(new Node(40, 36, "C"));

        mDatas.add(new Node(41, 4, "电视剧"));
        mDatas.add(new Node(42, 4, "电影"));
        mDatas.add(new Node(43, 4, "综艺"));
        mDatas.add(new Node(44, 4, "动画"));

        mDatas.add(new Node(45, 41, "花千骨"));
        mDatas.add(new Node(46, 41, "三国演义"));
        mDatas.add(new Node(47, 41, "匆匆那年"));
        mDatas.add(new Node(48, 41, "亮剑"));

        mDatas.add(new Node(49, 42, "金刚狼"));
        mDatas.add(new Node(50, 42, "复仇者联盟"));
        mDatas.add(new Node(51, 42, "碟中谍"));
        mDatas.add(new Node(52, 42, "谍影重重"));

        mDatas.add(new Node(53, 43, "极限挑战"));
        mDatas.add(new Node(54, 43, "奔跑吧兄弟"));
        mDatas.add(new Node(55, 43, "我去上学啦"));
        mDatas.add(new Node(56, 43, "中国好声音"));

        mDatas.add(new Node(57, 44, "火影忍者"));
        mDatas.add(new Node(58, 44, "海贼王"));
        mDatas.add(new Node(59, 44, "哆啦A梦"));
        mDatas.add(new Node(60, 44, "蜡笔小新"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
