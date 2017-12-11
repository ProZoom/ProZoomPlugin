package com.top.proutils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;

/*
* 作者：ProZoom
* 时间：2017/8/1 - 下午4:50
* 描述：获取手机信息工具类
*/
public class AppInfoUtils {
    /**
     * 获取VersionName
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            String pkName = context.getPackageName();
            return context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前应用程序的版本号
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 根据包名判断手机上是否安装了此app
     * @param context
     * @param packageName
     * @return
     */
    public static final boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断网络是否有效.
     * @param context the context
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 判断是否Wi-Fi
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting()
                && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }






    public static String fromMilliToSecond(int duration) {

        // 60000 ==> 60s
        // 1分钟 -》 60毫秒
        int minute = duration/60000;
        int sec = (duration - minute*60000)/1000;
        return (minute<10?"0":"")+minute+":"+(sec<10?"0":"")+sec;
    }

    /**
     * 分享多张图片
     * @param context
     * @param image
     */
    private void shareMoreImage(Context context,ArrayList image) {

        String path= Environment.getExternalStorageDirectory()+ File.separator;

        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putExtra(Intent.EXTRA_STREAM,image);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent,"分享到"));

    }
    /**
     * 分享一张图片
     * @param context
     * @param imagepath
     */
    private void shareOneImage(Context context,String imagepath) {

        //由文件得到uri
        Uri imageurl=Uri.fromFile(new File(imagepath));

        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM,imageurl);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent,"分享到"));

    }

    /**
     * 分享一段文字
     * @param context
     * @param msg
     */
    private void shareText(Context context,String msg) {

        Intent shareIntent=new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT,msg);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent,"分享到"));
    }

}
