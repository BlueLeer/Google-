package com.leer.googlemarket.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.SubjectInfo;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Leer on 2017/5/13.
 */

public class SubjectViewHolder extends BaseViewHolder<SubjectInfo> {

    private ImageView iv_subject_icon;
    private TextView tv_subject_des;
    private BitmapUtils mBitmapUtils;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.list_view_item_subject);
        iv_subject_icon = (ImageView) view.findViewById(R.id.iv_subject_icon);
        tv_subject_des = (TextView) view.findViewById(R.id.tv_subject_des);

        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }

    @Override
    protected void refreshView(SubjectInfo data) {
        String iconUrl = ConstantValues.ROOT_URL+"image?name="+data.imgUrl;
        mBitmapUtils.display(iv_subject_icon,iconUrl);

        tv_subject_des.setText(data.des);
    }
}
