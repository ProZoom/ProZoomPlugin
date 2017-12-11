package com.zoom.thirdlevelmenu;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class NodeTreeView extends ScrollView {


    private final Context mContext;
    private List<Node> mDatas;
    private LinearLayout.LayoutParams innerLayoutParams;
    private LinearLayout.LayoutParams dividerParams;


    public NodeTreeView(Context context) {
        super(context);
        this.mContext = context;
        innerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dividerParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
    }

    public void setData (List<Node> mDatas) {
        this.mDatas = mDatas;
        // 更新界面
        removeAllViews(); // 删除空间里的所有其他空间
        initViews(); // 创建新的视图
    }


    /*初始化界面*/
    private void initViews() {

        LinearLayout rootLayout = new LinearLayout(mContext);
        LayoutParams rootLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 垂直方向
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        // 把最根视图设置到scrollview
        addView(rootLayout, rootLayoutParams);

        // 加载一级菜单
        // 获取一级菜单节点
        List<Node> rootNodes = getListByParentId(0);
        Log.e("xxx - 一级菜单", " == "+rootNodes.size());
        for (int i = 0; i < rootNodes.size(); i++) {

                /* 获取当前节点 */
            Node rootNOde = rootNodes.get(i);
            final TextView firstLevelView = new TextView(mContext);
            firstLevelView.setLayoutParams(innerLayoutParams); //
            firstLevelView.setText(rootNOde.getName());
            firstLevelView.setTextSize(18);
            firstLevelView.setId(rootNOde.getId());
            firstLevelView.setTextColor(Color.RED);
            firstLevelView.setBackgroundColor(Color.BLACK);
            firstLevelView.setPadding(UNIT,UNIT,UNIT,UNIT);

            // 添加二级菜单。
            firstLevelView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    closeSameRank(firstLevelView);

                    // 我的孩子是谁？
                    int myId = view.getId();
                    // 判断之前有没有添加过视图？
                    LinearLayout lnycontainer  = (LinearLayout) view.getTag();
                    if (lnycontainer.getChildCount() == 0) {
                        // 没有添加过
                        addingMenuChild(lnycontainer, myId);
                    } else if (lnycontainer.isShown()) { // Visibility = VISIBLE
                        lnycontainer.setVisibility(GONE);
                    } else {  // Visibility = GONE
                        lnycontainer.setVisibility(VISIBLE);
                    }
                }
            });


            // 创建二级菜单容器
            LinearLayout secondLayoutContainer = new LinearLayout(mContext);
            secondLayoutContainer.setLayoutParams(innerLayoutParams);
            secondLayoutContainer.setOrientation(LinearLayout.VERTICAL);
            secondLayoutContainer.setVisibility(GONE);

            firstLevelView.setTag(secondLayoutContainer); // 绑定一级菜单以及对应容器
            rootLayout.addView(firstLevelView); // 添加一级菜单
            rootLayout.addView(secondLayoutContainer); // 二级菜单容器
            rootLayout.addView(getDivider()); // 区分线
        }
    }

    public int UNIT = 40;

    private void addingMenuChild(LinearLayout container, int myId) {

        container.setVisibility(VISIBLE); //

        // 当前选项的字儿
        List<Node> childNodes = getListByParentId(myId);
        if (childNodes.size() != 0) {
            for (int i = 0; i < childNodes.size(); i++) {
                Node rootNOde = childNodes.get(i);
                final TextView childLevelView = new TextView(mContext);
                childLevelView.setLayoutParams(innerLayoutParams); //
                childLevelView.setText(rootNOde.getName());
                childLevelView.setTextSize(16);
                childLevelView.setId(rootNOde.getId());
                childLevelView.setTextColor(Color.BLACK);
                childLevelView.setBackgroundColor(Color.LTGRAY);
                childLevelView.setPadding(UNIT+(myId*10),UNIT,UNIT,UNIT);

                // 添加二级菜单。
                childLevelView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 如果目前已经有隐藏其他在显示的，
                        // 应该吧他们斗隐藏掉。
                        closeSameRank(childLevelView);

                        // 我的孩子是谁？
                        int myId = view.getId();
                        // 判断之前有没有添加过视图？
                        LinearLayout lnycontainer  = (LinearLayout) view.getTag();
                        if (lnycontainer.getChildCount() == 0) {
                            // 没有添加过
                            addingMenuChild(lnycontainer, myId);
                        } else if (lnycontainer.isShown()) { // Visibility = VISIBLE
                            lnycontainer.setVisibility(GONE);
                        } else {  // Visibility = GONE
                            lnycontainer.setVisibility(VISIBLE);
                        }
                    }
                });


                // 创建字儿的下面菜单的容器
                LinearLayout childChildLayoutContainer = new LinearLayout(mContext);
                childChildLayoutContainer.setLayoutParams(innerLayoutParams);
                childChildLayoutContainer.setOrientation(LinearLayout.VERTICAL);
                childChildLayoutContainer.setVisibility(GONE);

                childLevelView.setTag(childChildLayoutContainer);
                container.addView(childLevelView);
                container.addView(childChildLayoutContainer);
                container.addView(getDivider());
            }
        } else {
            Toast.makeText(mContext, "没有孩子了！别点了！", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeSameRank(TextView childLevelView) {


        if (childLevelView.getParent() instanceof LinearLayout) {
            //
            LinearLayout lnyContainer = (LinearLayout) childLevelView.getParent();
            for (int i = 0; i < lnyContainer.getChildCount(); i++) {
                if (lnyContainer.getChildAt(i) instanceof TextView) {
                    TextView tvitem = (TextView) lnyContainer.getChildAt(i);
                    if (tvitem != null) {
                        LinearLayout lny = (LinearLayout) tvitem.getTag();
                        if (!childLevelView.equals(tvitem)) {
                            lny.setVisibility(GONE);
                        }
                    }
                }
            }
        } else {
            // scrollview
        }

    }


    private View getDivider() {

        View view = new View(mContext);
        view.setLayoutParams(dividerParams);
        view.setBackgroundColor(Color.YELLOW);
        return view;
    }


    private List<Node> getListByParentId(int i) {

        List<Node> resultNode = new ArrayList<>();
        for (int ii = 0; ii < mDatas.size(); ii++) {
            Node node = mDatas.get(ii);
            if (node.getpId() == i)
                resultNode.add(node);
        }
        return resultNode;
    }


}
