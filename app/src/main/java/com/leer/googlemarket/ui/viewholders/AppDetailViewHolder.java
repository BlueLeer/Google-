package com.leer.googlemarket.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfoDetail;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Leer on 2017/5/27.
 */

public class AppDetailViewHolder extends BaseViewHolder<AppInfoDetail> {
    private ImageView iv_app_icon;
    private TextView tv_app_name;
    private TextView tv_size;
    private TextView tv_download_num;
    private TextView tv_date;
    private RatingBar rb_star;
    private BitmapUtils mBitmapUtils;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.view_appinfo_detail);
        iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
        tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
        tv_size = (TextView)view.findViewById(R.id.tv_size);
        tv_download_num = (TextView) view.findViewById(R.id.tv_download_num);
        tv_date = (TextView) view.findViewById(R.id.tv_date);
        rb_star = (RatingBar) view.findViewById(R.id.rb_star);

        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }

    @Override
    protected void refreshView(AppInfoDetail data) {
        //显示应用的图标
        String iconUrl = ConstantValues.ROOT_URL+"image?name="+data.iconUrl;
        mBitmapUtils.display(iv_app_icon,iconUrl);

        tv_app_name.setText(data.name);
        tv_size.setText(android.text.format.Formatter.formatFileSize(UIUtils.getContext(),data.size));
        tv_download_num.setText(data.downloadNum);
        rb_star.setRating(data.stars);
        tv_date.setText(data.date);
    }

    @Override
    public void setData(AppInfoDetail data) {
        super.setData(data);
    }
}
