package com.top.proutils;

import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

/*
* 作者：ProZoom
* 时间：2017/8/1 - 下午5:15
* 描述：TextView工具类
*/
public class TextViewUtils {

    /**
     * textview跑马灯
     * @param tv
     */
    public  void setTextMarquee(TextView tv) {
        if (tv != null) {
            tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv.setSingleLine(true);
            tv.setSelected(true);
            tv.setFocusable(true);
            tv.setFocusableInTouchMode(true);
        }
    }

    /**
     * 添加删除线
     * @param tv
     */
    public static void AddUDeleteLine(TextView tv){
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 加粗
     * @param tv
     */
    public static void AddStrike(TextView tv){
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    /**
     * 添加下划线
     * @param tv
     */
    public static void AddUnderLine(TextView tv){
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


}
