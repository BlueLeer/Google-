package com.leer.googlemarket.ui.adapters;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.viewholders.LoadMoreViewHolder;
import com.leer.googlemarket.utils.UIUtils;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/9.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    //adapter需要数据来填充页面
    private ArrayList<T> mData;

    //ListView显示的item视图类型
    //普通的每个条目的视图类型
    private static final int LOAD_NORMAL_TYPE = 0;
    //脚布局的师徒类型(显示加载状态)
    private static final int LOAD_FOOTER_TYPE = 1;

    //加载结果
    //加载结束后还有数据
    public static final int LOAD_MORE_LOADING = 100;
    //加载结束后没有数据了
    public static final int LOAD_MORE_NOMORE = 101;
    //加载结束后,没有获取到数据
    public static final int LOAD_MORE_ERROR = 102;

    private int mCurrentLoadMoreState = LOAD_MORE_LOADING;


    public MyBaseAdapter(ArrayList<T> data) {
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        //最后一个item显示加载更多的footer
        if (position == getCount() - 1) {
            return LOAD_FOOTER_TYPE;
        } else {
            return LOAD_NORMAL_TYPE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public T getItem(int position) {
        if (position == getCount() - 1) {
            return null;
        } else {
            return mData.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder viewHolder;
        if (convertView == null) {
            //实例化每一个item的view,并且将此viewholder已经设置成tag给该item的view
            if (getItemViewType(position) == LOAD_NORMAL_TYPE) {

                viewHolder = getViewHolder();

            } else {
                viewHolder = getLoadMoreHolder();
                LoadMoreViewHolder loadMoreHolder = (LoadMoreViewHolder) viewHolder;
                if (mCurrentLoadMoreState != LOAD_MORE_NOMORE) {
                    //若是当前数据还有,或者上次没有加载成功,就应该加载数据
                    loadData(loadMoreHolder);
                } else {
                    loadMoreHolder.setCurrentState(mCurrentLoadMoreState);
                }
            }
            convertView = viewHolder.getRootView();
        } else {
            viewHolder = (BaseViewHolder) convertView.getTag();
        }

        if (getItemViewType(position) == LOAD_NORMAL_TYPE) {
            viewHolder.setData(getItem(position));
        }
        return convertView;
    }


    protected abstract BaseViewHolder getViewHolder();

    private boolean isLoading = false;

    private void loadData(final LoadMoreViewHolder holder) {
        if (!isLoading) {
            new Thread() {
                @Override
                public void run() {
                    isLoading = true;
                    ArrayList<T> dataList = loadMore();
                    if (dataList == null) {
                        mCurrentLoadMoreState = LOAD_MORE_ERROR;
                    } else if (dataList.size() < 20) {
                        mCurrentLoadMoreState = LOAD_MORE_NOMORE;
                        mData.addAll(dataList);
                    } else {
                        mCurrentLoadMoreState = LOAD_MORE_LOADING;
                        mData.addAll(dataList);
                    }

                    //睡眠2秒钟
                    SystemClock.sleep(2000);

                    //只有等到数据加载结束以后,才能知道加载的结果
                    UIUtils.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            //根据加载数据后的结果显示对应脚布局的内容
                            holder.setCurrentState(mCurrentLoadMoreState);
                            MyBaseAdapter.this.notifyDataSetChanged();
                        }
                    });

                    //数据加载完毕,更改加载状态
                    isLoading = false;
                }
            }.start();
        }

    }

    //具体加载更多数据由子类来实现
    protected abstract ArrayList<T> loadMore();

    //每一个ListView都有一个脚布局,显示加载状态,成功,失败,或者没有更多数据了
    public BaseViewHolder getLoadMoreHolder() {
        return new LoadMoreViewHolder();
    }
}
