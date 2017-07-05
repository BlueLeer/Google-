package com.leer.googlemarket.global;

/**
 * Created by Leer on 2017/5/9.
 */

public enum LoadState {
    //声明所有的枚举值
    LOAD_LOADING(1), LOAD_FAILED(2), LOAD_EMPTY(3), LOAD_SUCCESS(4);

    private int value;

    //当访问枚举值得时候,该构造方法将会被调用
    LoadState(int state) {
        this.value = state;
    }

    //获取枚举值
    public int getLoadState() {
        return value;
    }
}
