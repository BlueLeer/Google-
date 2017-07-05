package com.leer.googlemarket.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.leer.googlemarket.domain.HotInfo;
import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.ui.widgets.FlowLayout;
import com.leer.googlemarket.utils.BackgroundUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Leer on 2017/5/9.
 */

public class HotFragment extends BaseFragment {

    private ArrayList<HotInfo> mHotInfos;
    private FlowLayout flowView;

    @Override
    public View createSuccessView() {
        flowView = new FlowLayout(UIUtils.getContext());
        int padding = UIUtils.dip2px(10);
        flowView.setPadding(padding, padding, padding, padding);
        flowView.setHorizontalSpacing(UIUtils.dip2px(8));
        flowView.setVerticalSpacing(UIUtils.dip2px(5));

        Random random = new Random();
        for (int i = 0; i < mHotInfos.size(); i++) {
            final String appName = mHotInfos.get(i).name;

            TextView textView = new TextView(UIUtils.getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            int paddingIn = UIUtils.dip2px(10);
            textView.setPadding(paddingIn,paddingIn,paddingIn,paddingIn);

            int r = random.nextInt(200) + 30;
            int g = random.nextInt(200) + 30;
            int b = random.nextInt(200) + 30;

            int normalColor = Color.rgb(r, g, b);
            int pressColor = 0x848d84;

            StateListDrawable selector = BackgroundUtils.getSelector(normalColor, pressColor, UIUtils.dip2px(3));
            textView.setBackgroundDrawable(selector);
            textView.setText(appName);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(),appName,Toast.LENGTH_SHORT).show();
                }
            });

            flowView.addView(textView);

        }

        ScrollView scrollView = new ScrollView(UIUtils.getContext());
        scrollView.addView(flowView);

        return scrollView;
    }


    @Override
    public LoadState onLoad() {
        String url = URLBuilder.hotUrlBuilder("0");
        String result = MyHttpConnection.getData(url);
        mHotInfos = JsonParser.parserHotInfo(result);
        Log.d("xxxxxxxxxx", result);
        return LoadState.LOAD_SUCCESS;
    }
}
