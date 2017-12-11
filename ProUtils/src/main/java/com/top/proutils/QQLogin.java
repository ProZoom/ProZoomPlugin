package com.top.proutils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

/*
* 作者：ProZoom
* 时间：2017/8/6 - 上午7:43
* 描述：
*/
public class QQLogin {

    public static Tencent mTencent;
    private UserInfo mInfo;
    private Context context;

    public QQLogin(Context context,UserInfo mInfo) {
        this.context=context;
        this.mInfo = mInfo;
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
            Log.i("tag", "response:" + response);
            //获取openid和token
            initOpenIdAndToken(response);
            //获取用户信息
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(context, "登录取消", Toast.LENGTH_SHORT).show();
        }
    }

    private void initOpenIdAndToken(Object object) {
        JSONObject jb = (JSONObject) object;
        try {
            String openID = jb.getString("openid");  //openid用户唯一标识
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");

            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        QQToken token = mTencent.getQQToken();
        mInfo = new UserInfo(context, token);
        mInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object object) {
                JSONObject jo = (JSONObject) object;
                Log.i("TAG",object.toString());

            }

            @Override
            public void onError(UiError uiError) {
            }

            @Override
            public void onCancel() {
            }
        });
    }

}
