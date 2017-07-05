package com.leer.googlemarket.utils;

import android.util.Log;

/**用于Log打印日志的封装,可以手动设置mCurrentLevel的级别来控制当前打印日志的最低级别
 * 当设置成
 * Created by Leer on 2017/5/10.
 */

public class LogUtils {
    private static final int LEVEL_NO_LOG = 0;
    private static final int LEVEL_VERBOSE = 1;
    private static final int LEVEL_DEBUG = 2;
    private static final int LEVEL_INFO = 3;
    private static final int LEVEL_WARN = 4;
    private static final int LEVEL_ERROR = 5;

    private static final String mTag = "LogUtils---";

    //当前打印日志的最高级别
    private static final int mCurrentHighestLevel = LEVEL_ERROR;

    /** 以级别为 d 的形式输出LOG */
    public static void v(String msg) {
        if (mCurrentHighestLevel >= LEVEL_VERBOSE) {
            Log.v(mTag, msg);
        }
    }

    /** 以级别为 d 的形式输出LOG */
    public static void d(String msg) {
        if (mCurrentHighestLevel >= LEVEL_DEBUG) {
            Log.d(mTag, msg);
        }
    }

    /** 以级别为 i 的形式输出LOG */
    public static void i(String msg) {
        if (mCurrentHighestLevel >= LEVEL_INFO) {
            Log.i(mTag, msg);
        }
    }

    /** 以级别为 w 的形式输出LOG */
    public static void w(String msg) {
        if (mCurrentHighestLevel >= LEVEL_WARN) {
            Log.w(mTag, msg);
        }
    }

    /** 以级别为 w 的形式输出Throwable */
    public static void w(Throwable tr) {
        if (mCurrentHighestLevel >= LEVEL_WARN) {
            Log.w(mTag, "", tr);
        }
    }

    /** 以级别为 w 的形式输出LOG信息和Throwable */
    public static void w(String msg, Throwable tr) {
        if (mCurrentHighestLevel >= LEVEL_WARN && null != msg) {
            Log.w(mTag, msg, tr);
        }
    }

    /** 以级别为 e 的形式输出LOG */
    public static void e(String msg) {
        if (mCurrentHighestLevel >= LEVEL_ERROR) {
            Log.e(mTag, msg);
        }
    }

    /** 以级别为 e 的形式输出Throwable */
    public static void e(Throwable tr) {
        if (mCurrentHighestLevel >= LEVEL_ERROR) {
            Log.e(mTag, "", tr);
        }
    }

    /** 以级别为 e 的形式输出LOG信息和Throwable */
    public static void e(String msg, Throwable tr) {
        if (mCurrentHighestLevel >= LEVEL_ERROR && null != msg) {
            Log.e(mTag, msg, tr);
        }
    }

}
