package com.top.gamehandle;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class BasicUtils {

    /*
  * 将指定的textview资源设置成跑马灯效果
  * */
	public  void setTextMarquee(TextView textView_setTextMarquee) {
        if (textView_setTextMarquee != null) {
            textView_setTextMarquee.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            textView_setTextMarquee.setSingleLine(true);
            textView_setTextMarquee.setSelected(true);
            textView_setTextMarquee.setFocusable(true);
            textView_setTextMarquee.setFocusableInTouchMode(true);
        }
    }
    /*
  * 简单的Toast显示
  * 当前类名：字符串
  * */
	public static void mT(Activity activity, String MSG){
        boolean Toast_Enable=true;
        if(Toast_Enable){
            Toast.makeText(activity,activity.getClass().getSimpleName()+":"+MSG,Toast.LENGTH_SHORT).show();
        }
    }

    public static void logPrintf(String MSG){
        Boolean Log_Enable=true;
        String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
        if(Log_Enable){
            Log.i("TAG",MSG);
        }
    }


    public static String getTraceInfo(){
        StringBuffer sb = new StringBuffer();

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        int stacksLen = stacks.length;

        sb.append("class: " ).append(stacks[1].getClassName()).append("; method: ").append(stacks[1].getMethodName()).append("; number: ").append(stacks[1].getLineNumber());

        return sb.toString();
    }
}

