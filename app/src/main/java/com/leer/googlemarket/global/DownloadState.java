package com.leer.googlemarket.global;

/**
 * 下载状态
 * Created by Leer on 2017/6/2.
 */

public enum DownloadState {
    //声明所有的枚举值
    UNDO(1), //未下载
    WAITING(2),//等待下载
    DOWNLOADING(3), //正在下载
    SUCCESS(4), //下载成功
    ERROR(5),//下载错误
    PAUSE(6);

    private int mValue;

    //当访问枚举值的时候,该构造方法将会被调用
    DownloadState(int value) {
        this.mValue = value;
    }

    //获取枚举值
    public int getValue() {
        return mValue;
    }
}
