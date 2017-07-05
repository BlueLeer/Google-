package com.leer.googlemarket.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.leer.googlemarket.R;

/**
 * Created by Leer on 2017/5/12.
 */

public class AppInfoItemView extends LinearLayout {

    private View mView;
    public ImageView iv_app_icon;
    public TextView tv_app_name;
    public TextView tv_app_desc;
    public TextView tv_app_size;
    public RatingBar rb_app_star;

    public AppInfoItemView(Context context) {
        this(context,null);
    }

    public AppInfoItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AppInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mView = View.inflate(context, R.layout.view_appinfo_item,this);
        initView();
    }

    private void initView() {
        iv_app_icon = (ImageView) mView.findViewById(R.id.iv_app_icon);
        tv_app_name = (TextView) mView.findViewById(R.id.tv_app_name);
        tv_app_desc = (TextView) mView.findViewById(R.id.tv_app_desc);
        tv_app_size = (TextView) mView.findViewById(R.id.tv_app_size);
        rb_app_star = (RatingBar) mView.findViewById(R.id.rb_app_star);
    }

}
