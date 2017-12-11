package com.top.network.okhttp;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author：ProZoom
 * Time：2017/9/7-下午3:53
 * Github: https://github.com/ProZoom/Blog
 * QQ Group：368666299
 * Description：
 */

public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    private Request request; //请求
    private Call call;


    /**
     * 单例模式
     * 懒汉式
     */
    private static OkHttpUtils mOkHttpUtils;

    private OkHttpUtils() {
    }

    public static OkHttpUtils getInstance() {
        if (mOkHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (mOkHttpUtils == null) {
                    mOkHttpUtils = new OkHttpUtils();
                }
            }
        }
        return mOkHttpUtils;
    }


    /**
     * 同步的Get请求
     * @param url
     * @return
     */
    public Response getSnyc(String url) throws IOException {

        request = new Request.Builder()
                .url(url)
                .build();
        call = mOkHttpClient.newCall(request);

        return call.execute();
    }

    /**
     * 异步的Get请求
     * @param url
     * @param callback
     */
    public void getAsnyc(String url, Callback callback) {

        request = new Request.Builder()
                .url(url)
                .build();

        call = mOkHttpClient.newCall(request);

        call.enqueue(callback);
    }


    /**
     * 同步的Post请求---提交键值对
     * @param url
     * @param requestBody
     * RequestBody requestBody = new FormEncodingBuilder()
     *                      .add("platform", "android")
     *                      .add("name", "bug")
     *                      .build();
     * @return
     * @throws IOException
     */
    public Response postSnyc(String url,RequestBody requestBody) throws IOException {


        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();

        return response;
    }

    /**
     * 异步的Post请求---提交键值对
     * @param url
     * @param requestBody
     * RequestBody requestBody = new FormEncodingBuilder()
     *                      .add("platform", "android")
     *                      .add("name", "bug")
     *                      .build();
     * @param callback
     */
    public void postAsnyc(String url,RequestBody requestBody,Callback callback) {

        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        call=mOkHttpClient.newCall(request);

        call.enqueue(callback);
    }


    /**
     * 异步下载文件
     * @param fileUrl
     * @param localFileileDir
     */
    public void downloadFileAsyn(final String fileUrl, final String localFileileDir){
        request = new Request.Builder()
                .url(fileUrl)
                .build();
        call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;

                is = response.body().byteStream();
                File file = new File(localFileileDir, getFileName(fileUrl));
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();

                if (is != null) is.close();

                if (fos != null) fos.close();

            }
        });
    }

    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }


    //////////////////////////////////////////////////////////////

}
