package com.leer.googlemarket.ui.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.CategoryInfo;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Leer on 2017/5/16.
 */

public class CategoryViewHolder extends BaseViewHolder<CategoryInfo> {

    private ImageView iv1,iv2,iv3;
    private TextView tv1,tv2,tv3;
    private BitmapUtils mBitmapUtils;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.list_view_item_category);
        iv1 = (ImageView) view.findViewById(R.id.icon1);
        iv2 = (ImageView)view.findViewById(R.id.icon2);
        iv3 = (ImageView)view.findViewById(R.id.icon3);
        tv1 = (TextView) view.findViewById(R.id.name1);
        tv2 = (TextView) view.findViewById(R.id.name2);
        tv3 = (TextView) view.findViewById(R.id.name3);
        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }

    @Override
    protected void refreshView(CategoryInfo data) {
        mBitmapUtils.display(iv1, ConstantValues.ROOT_URL+"image?name="+data.url1);
        mBitmapUtils.display(iv2, ConstantValues.ROOT_URL+"image?name="+data.url2);
        mBitmapUtils.display(iv3, ConstantValues.ROOT_URL+"image?name="+data.url3);

        tv1.setText(data.name1);
        tv2.setText(data.name2);
        tv3.setText(data.name3);
    }
}
