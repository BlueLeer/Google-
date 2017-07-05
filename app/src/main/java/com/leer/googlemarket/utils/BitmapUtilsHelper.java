package com.leer.googlemarket.utils;

import com.lidroid.xutils.BitmapUtils;

/**
 * 单例模式,创建一个BitmapUtils对象
 * Created by Leer on 2017/5/12.
 */

public class BitmapUtilsHelper {
    private static BitmapUtils bitmapUtils = null;

    private BitmapUtilsHelper() {
    }

    public static BitmapUtils getBitmapUtils() {
//        为了保证该应用只创建一个BitmapUtils对象,进行synchronized加锁
//        注意:一个方法可以加锁,方法当中的一段代码片段也能后加锁
//        示例方法进行加锁,对拥有该示例方法的对象进行加锁
//        静态方法进行加锁,对该类(.class)进行加锁
        if (bitmapUtils == null) {
            synchronized (BitmapUtilsHelper.class) {
                bitmapUtils = new BitmapUtils(UIUtils.getContext());
            }
        }
        return bitmapUtils;
    }
}
