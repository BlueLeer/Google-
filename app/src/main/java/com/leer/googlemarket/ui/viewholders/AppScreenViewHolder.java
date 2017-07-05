package com.leer.googlemarket.ui.viewholders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfoDetail;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/27.
 */

public class AppScreenViewHolder extends BaseViewHolder<AppInfoDetail> {

    private BitmapUtils mBitmapUtils;
    private LinearLayout ll_screens;
    private ImageView[] iv_screens;

    @Override
    protected View initView() {
        View view = UIUtils.inflateRes(R.layout.view_app_screen);
        ll_screens = (LinearLayout) view.findViewById(R.id.ll_screens);

        iv_screens = new ImageView[5];

        iv_screens[0] = (ImageView) view.findViewById(R.id.iv_screen_1);
        iv_screens[1] = (ImageView) view.findViewById(R.id.iv_screen_2);
        iv_screens[2] = (ImageView) view.findViewById(R.id.iv_screen_3);
        iv_screens[3] = (ImageView) view.findViewById(R.id.iv_screen_4);
        iv_screens[4] = (ImageView) view.findViewById(R.id.iv_screen_5);

        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }

    @Override
    protected void refreshView(AppInfoDetail data) {
        ArrayList<String> screen = data.screen;
        for (int i = 0; i < screen.size(); i++) {
            if(i<screen.size()){
                String screenUrl = ConstantValues.ROOT_URL + "image?name=" + screen.get(i);
                mBitmapUtils.display(iv_screens[i], screenUrl);
            }else{
                iv_screens[i].setVisibility(View.GONE);
            }
        }

    }
}
