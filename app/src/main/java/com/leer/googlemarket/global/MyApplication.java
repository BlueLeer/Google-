package com.leer.googlemarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**因为程序中会经常用到Handler,context,以及有时候需要判断代码是否运行在主线程
 * 在该类中提供出这些方法,以便后面开发是使用
 * Created by Leer on 2017/5/8.
 */

public class MyApplication extends Application {
    private static Handler mHandler;
    private static Context mContext;
    private static int mMainThreadID;

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mContext = getApplicationContext();
        mMainThreadID = android.os.Process.myTid();
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Context getContext() {
        return mContext;
    }

    public static int getMainThreadID() {
        return mMainThreadID;
    }
}
