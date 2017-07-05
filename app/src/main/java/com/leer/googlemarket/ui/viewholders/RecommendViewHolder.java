package com.leer.googlemarket.ui.viewholders;

import android.view.View;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.RecommendInfo;
import com.leer.googlemarket.ui.widgets.FlowLayout;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/13.
 */

public class RecommendViewHolder extends BaseViewHolder<RecommendInfo> {

    private FlowLayout flowLayout;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.list_view_item_hot);

        return view;
    }

    @Override
    protected void refreshView(RecommendInfo data) {

    }
}
