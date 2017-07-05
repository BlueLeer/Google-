package com.leer.googlemarket.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.leer.googlemarket.global.ConstantValues;

/**
 * Created by Leer on 2017/5/11.
 */

public class URLBuilder {
    /**
     * 创建"首页"的URL创建器
     *
     * @param index 从第几页开始返回数据
     * @return "首页"对应的URL地址
     */
    public static String homeUrlBuilder(String index) {
        //如果传过来的index为空,则从第0页开始返回数据
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_HOME)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }

    public static String appUrlBuilder(String index) {
        //如果传过来的index为空,则从第0页开始返回数据
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_APP)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }

    public static String subjectUrlBuilder(String index){
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_SUBJECT)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }

    public static String recommendUrlBuilder(String index){
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_RECOMMEND)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }

    public static String hotUrlBuilder(String index){
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_HOT)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }

    public static String categoryUrlBuilder(String index){
        if (TextUtils.isEmpty(index)) {
            index = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_CATEGORY)
                .buildUpon()
                .appendQueryParameter("index", index)
                .toString();
        return url;
    }


    /**根据应用的包名,获取应用的具体信息
     * @param packageName 应用的包名
     * @return 返回该应用详细信息的Json数据
     */
    public static String appDetailUrlBuilder(String packageName){
        if (TextUtils.isEmpty(packageName)) {
            packageName = "0";
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + ConstantValues.URL_APP_DETAILS)
                .buildUpon()
                .appendQueryParameter("packageName", packageName)
                .toString();
        return url;
    }

    public static String appDownloadUrlBuilder(String appUrl){
        if (TextUtils.isEmpty(appUrl)) {
            return null;
        }
        String url = Uri.parse(ConstantValues.ROOT_URL + "startDownload?name=")
                .buildUpon()
                .appendQueryParameter("startDownload?name=", appUrl)
                .toString();
        return url;
    }
}
