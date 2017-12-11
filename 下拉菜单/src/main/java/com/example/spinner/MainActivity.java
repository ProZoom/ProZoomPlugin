package com.example.spinner;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.top.proutils.BaseActivity;

public class MainActivity extends BaseActivity implements android.view.View.OnClickListener, android.widget.AdapterView.OnItemClickListener {

    android.widget.ImageView mIvArrow;
    android.widget.EditText mEtInput;
    android.widget.PopupWindow window;
    java.util.List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("下拉菜单");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mEtInput= (android.widget.EditText) findViewById(com.example.spinner.R.id.et_input);
        mIvArrow= (android.widget.ImageView) findViewById(com.example.spinner.R.id.iv_arrow);

        mIvArrow.setOnClickListener(this);

        // 模拟数据
        mDatas = new java.util.ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            mDatas.add(1000 + i+"");
        }


    }

    @Override
    public void onClick(android.view.View view) {
        if(view==mIvArrow){
            clickArrow();
        }
    }

    /*
    * 尖头点击事件
    * */
    private void clickArrow() {

        android.widget.ListView contentView=new android.widget.ListView(this);

        int width=mEtInput.getWidth();
        int height=800;
        contentView.setAdapter(new com.example.spinner.MainActivity.myAdapter());

        window=new android.widget.PopupWindow(contentView,width,height);

        window.setOutsideTouchable(true);

        window.showAsDropDown(mEtInput);

        contentView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(android.widget.AdapterView<?> adapterView, android.view.View view, int i, long l) {
        mEtInput.setText(mDatas.get(i));

        window.dismiss();

    }

    /*
    * 适配器
    * */
    class myAdapter extends android.widget.BaseAdapter{

        @Override
        public int getCount() {
            if(mDatas!=null){
                return mDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if(mDatas!=null){
               return mDatas.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public android.view.View getView(final int i, android.view.View view, android.view.ViewGroup viewGroup) {

            ViewHolder holder;

            if(view==null){
                //没有复用
                //加载布局
                view= android.view.View.inflate(MainActivity.this, com.example.spinner.R.layout.item,null);
                //初始化holder
                holder=new com.example.spinner.MainActivity.ViewHolder();
                //设置标记
                view.setTag(holder);
                //初始化item的view
                holder.ivDelete= (android.widget.ImageView) view.findViewById(com.example.spinner.R.id.iv_delete);
                holder.ivUser= (android.widget.ImageView) view.findViewById(com.example.spinner.R.id.iv_user);
                holder.tvData= (android.widget.TextView) view.findViewById(com.example.spinner.R.id.tv_data);


            }else{
                //有复用
                holder= (com.example.spinner.MainActivity.ViewHolder) view.getTag();
            }

            //给view设置数据
            final String data=mDatas.get(i);
            android.util.Log.i("data",data);
            holder.tvData.setText(data);

            //删除按钮,去除数据
            holder.ivDelete.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    mDatas.remove(data);
                    //通知UI刷新
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }

    class ViewHolder{
        android.widget.ImageView ivUser;
        android.widget.TextView tvData;
        android.widget.ImageView ivDelete;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
