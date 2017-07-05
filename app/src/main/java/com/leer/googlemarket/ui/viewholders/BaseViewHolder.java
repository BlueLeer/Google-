package com.leer.googlemarket.ui.viewholders;

import android.view.View;

/**
 * ListView的ViewHolder的基类
 * Created by Leer on 2017/5/9.
 */

public abstract class BaseViewHolder<T> {
    private View mRootView;
    private T mData;

    public BaseViewHolder() {
        View view = initView();
        if (view != null) {
            this.mRootView = initView();
            mRootView.setTag(this);
        }
    }

    public View getRootView() {
        return mRootView;
    }

    public void setData(T data){
        this.mData = data;
        refreshView(data);
    }

    public T getData(){
        return mData;
    }

    /**
     * 由具体的子类实现
     * 返回ListView的item的View,可以在里面实现findViewById来找到子view
     * @return ListView的item的view对象
     */
    protected abstract View initView();

    /**
     * @param data 传入需要设置给控件的数据
     */
    protected abstract void refreshView(T data);
}
