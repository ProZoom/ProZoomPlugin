package com.top.proutils;

import android.content.Context;
import android.view.WindowManager;

/*
* 作者：ProZoom
* 时间：2017/8/2 - 下午5:31
* 描述：获取手机屏幕相关信息，包括屏幕尺寸，状态栏高度
*/
public class ScreenInfoUtils {


    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取屏幕尺寸
     * @param context
     * @return
     */
    public static int[] getScreen(Context context){
        int[] screen=new int[]{};
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        //Log.i("ScreenTAG","ScreenWidth---> "+width+"\nScreenHeight---> "+height);
        screen[0]=width;
        screen[1]=height;
        return screen;
    }
}
