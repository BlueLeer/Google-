package com.leer.googlemarket.ui.viewholders;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfo;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;




/**
 * Created by Leer on 2017/5/9.
 */

public class HomeViewHolder extends BaseViewHolder<AppInfo> {

    private ImageView ivIcon;
    private TextView tvName,tvSize,tvDes;
    private RatingBar rbStar;
    private BitmapUtils mBitmapUtils;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.list_view_item_home);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        tvDes = (TextView) view.findViewById(R.id.tv_des);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        rbStar = (RatingBar) view.findViewById(R.id.rb_star);

        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }

    @Override
    protected void refreshView(AppInfo s) {
        String iconUrl = ConstantValues.ROOT_URL+ "image?name="
                +s.iconUrl;
        LogUtils.d(iconUrl);

        mBitmapUtils.display(ivIcon,iconUrl);

        tvName.setText(s.name);
        String appSize = Formatter.formatFileSize(UIUtils.getContext(), s.size);
        tvSize.setText(appSize);
        tvDes.setText(s.des);
        rbStar.setRating(s.stars);
    }
}
