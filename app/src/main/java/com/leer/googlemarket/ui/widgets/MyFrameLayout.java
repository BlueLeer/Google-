package com.leer.googlemarket.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.leer.googlemarket.R;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.manager.ThreadManager;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/9.
 */

public abstract class MyFrameLayout extends FrameLayout {

    private LinearLayout mLoadingView;
    private LinearLayout mFailedView;
    private LinearLayout mEmptyView;
    private View mSuccessView;

    //记录当前的加载状态,初始情况下是正在加载中
    private LoadState mCurrentLoadState = LoadState.LOAD_LOADING;

    public MyFrameLayout(Context context) {
        this(context, null);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addView();
    }

    public void addView() {
        //加载中的显示页面
        mLoadingView = (LinearLayout) UIUtils.inflateRes(R.layout.view_frame_layout_loading);
        mLoadingView.setVisibility(View.GONE);
        addView(mLoadingView);

        //加载失败的显示页面
        mFailedView = (LinearLayout) UIUtils.inflateRes(R.layout.view_frame_layout_failed);
        mFailedView.setVisibility(View.GONE);
        addView(mFailedView);

        //加载为空的显示页面
        mEmptyView = (LinearLayout) UIUtils.inflateRes(R.layout.view_frame_layout_empty);
        mEmptyView.setVisibility(View.GONE);
        addView(mEmptyView);


        //根据加载的状态,显示对应的加载页面
        showRightView();
    }

    public void showRightView() {
        mLoadingView.setVisibility(mCurrentLoadState ==
                LoadState.LOAD_LOADING ? View.VISIBLE : View.GONE);
        mFailedView.setVisibility(mCurrentLoadState ==
                LoadState.LOAD_FAILED ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(mCurrentLoadState ==
                LoadState.LOAD_EMPTY ? View.VISIBLE : View.GONE);

        //初始化加载成功后的布局
        if(mCurrentLoadState == LoadState.LOAD_SUCCESS && mSuccessView == null){
            mSuccessView = createSuccessView();
            if(mSuccessView != null){
                addView(mSuccessView);
            }
        }

        if(mSuccessView != null){
            mSuccessView.setVisibility(mCurrentLoadState ==
                    LoadState.LOAD_SUCCESS?View.VISIBLE:View.GONE);
        }
    }

    public void loadData() {
        mCurrentLoadState = LoadState.LOAD_LOADING;
//        new Thread() {
//            @Override
//            public void run() {
//                //回调onLoad()方法
//                LoadState loadState = onLoad();
//                if (loadState != null) {
//                    //更新现在的加载状态值
//                    mCurrentLoadState = loadState;
//                }
//                UIUtils.runOnMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showRightView();
//                    }
//                });
//            }
//        }.start();

        ThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //回调onLoad()方法
                LoadState loadState = onLoad();
                if (loadState != null) {
                    //更新现在的加载状态值
                    mCurrentLoadState = loadState;
                }
                UIUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        showRightView();
                    }
                });
            }
        });
    }


    //子类需要实现加载成功以后的页面
    protected abstract View createSuccessView();

    //子类需要返回加载后的状态
    protected abstract LoadState onLoad();
}
