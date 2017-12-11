package com.top.network;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.top.network.okhttp.OkHttpSample;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rlv;
    private MyAdapter mAdapter;
    ConstraintLayout cl;
    private TextView tv_one, tv_two, tv_three,tv_four;
    private String[] datas = {
            "OkHttp网络框架"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rlv = (RecyclerView) findViewById(R.id.rv);



        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rlv.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(datas);
        rlv.setAdapter(mAdapter);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
        String[] datas = null;

        MyAdapter(String[] datas) {
            this.datas = datas;
        }

        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            view.setOnClickListener(this);

            return vh;
        }

        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.mTextView.setText(datas[position]);
        }

        //获取数据的数量
        @Override
        public int getItemCount() {
            return datas.length;
        }

        @Override
        public void onClick(View view) {
            int position = rlv.getChildAdapterPosition(view);
            Toast.makeText(MainActivity.this, "点击" + position, Toast.LENGTH_LONG).show();
            switch (position) {
                case 0:
                    startActivity(new Intent(MainActivity.this, OkHttpSample.class));
                    break;
            }
        }


        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            ViewHolder(View view) {
                super(view);
                mTextView = view.findViewById(R.id.text);
            }
        }
    }
}
