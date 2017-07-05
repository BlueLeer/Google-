package com.leer.googlemarket.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.ui.widgets.MyFrameLayout;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/8.
 */

public abstract class BaseFragment extends Fragment {

    private MyFrameLayout mFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mFrameLayout == null){
            mFrameLayout = new MyFrameLayout(UIUtils.getContext()) {
                @Override
                protected View createSuccessView() {
                    //创建加载成功后具体的页面,交给子类去做
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadState onLoad() {
                    //加载状态由具体的子类来实现
                    return BaseFragment.this.onLoad();
                }
            };
        }

        LogUtils.v("我要初始化啦------:"+getClass().getSimpleName());

        return mFrameLayout;
    }

    public abstract View createSuccessView();
    public abstract LoadState onLoad();

    //开始加载数据
    public void startLoadData(){
        if(mFrameLayout != null){
            mFrameLayout.loadData();
        }
    }
}
