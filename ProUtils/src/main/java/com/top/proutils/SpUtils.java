package com.top.proutils;

import android.content.Context;
import android.content.SharedPreferences;

/*
* 作者：ProZoom
* 时间：2017/8/1 - 下午5:21
* 描述：SharedPreference工具类,Android平台给我们提供了一个SharedPreferences类，
* 它是一个轻量级的存储类，特别适合用于保存软件配置参数。
* 使用SharedPreferences保存数据，其背后是用xml文件存放数据，
* 文件存放在/data/data/<package name>/shared_prefs目录下
*/
public class SpUtils {


    public static void putString(Context context, String fileName,String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();//提交数据
    }


    public static String getString(Context context, String fileName,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);

        return sharedPreferences.getString(key,value);
    }




    public static void putBoolean(Context context, String fileName, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();//提交数据
    }


    public static boolean getBoolean(Context context, String fileName,String key,boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(key,value);
    }

}
