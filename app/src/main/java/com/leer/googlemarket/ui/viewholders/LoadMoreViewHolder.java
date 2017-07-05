package com.leer.googlemarket.ui.viewholders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/10.
 */

public class LoadMoreViewHolder extends BaseViewHolder<String> {

    private TextView tv_load_more;
    //默认该viewholder的状态是正在加载更多
    private int mCurrentState = MyBaseAdapter.LOAD_MORE_LOADING;
    private LinearLayout ll_loading;
    private TextView tv_load_more_other;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.list_view_item_load_more);
        tv_load_more = (TextView) view.findViewById(R.id.tv_load_more);
        ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        tv_load_more_other = (TextView) view.findViewById(R.id.tv_load_more_other);

//        tv_load_more_other.setVisibility(View.GONE);
        ll_loading.setVisibility(View.GONE);
        return view;
    }

    public void setCurrentState(int state) {
        this.mCurrentState = state;
        refreshView(null);
    }

    @Override
    protected void refreshView(String data) {
        switch (mCurrentState) {
            case MyBaseAdapter.LOAD_MORE_LOADING:
                ll_loading.setVisibility(View.VISIBLE);
                tv_load_more_other.setVisibility(View.GONE);
                tv_load_more.setText("正在加载...");
                break;
            case MyBaseAdapter.LOAD_MORE_NOMORE:
                ll_loading.setVisibility(View.GONE);
                tv_load_more_other.setVisibility(View.VISIBLE);
                tv_load_more_other.setText("没有更多数据啦");
//                Toast.makeText(UIUtils.getContext(), "没有更多数据啦", Toast.LENGTH_SHORT).show();
                break;
            case MyBaseAdapter.LOAD_MORE_ERROR:
                ll_loading.setVisibility(View.GONE);
                tv_load_more_other.setVisibility(View.VISIBLE);
                tv_load_more_other.setText("加载失败");
//                Toast.makeText(UIUtils.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
