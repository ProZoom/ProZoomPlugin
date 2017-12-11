package com.top.proutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
* 作者：ProZoom
* 时间：2017/8/1 - 下午5:06
* 描述：加密工具
*/
public class EncryptionUtils {

    /**
     * 使用MD5算法对传入的key进行加密并返回
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            byte[] bytes = mDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            cacheKey = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }


    public static String md5(String str) {
        StringBuilder mess = new StringBuilder();

        try {
            //获取MD5密码加密器
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] bytes = str.getBytes();
            byte[] digest = md.digest(bytes);


            for (byte b : digest) {
                int d = b & 0xff;//把每个字节转换成16进制
                String hexString = Integer.toHexString(d);
                if (hexString.length() == 1) {//字节的高四位为0
                    hexString = "0" + hexString;
                }
                mess.append(hexString);//拼接
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return mess + "";
    }

}
