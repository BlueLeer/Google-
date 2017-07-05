package com.leer.googlemarket.ui.fragments;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.domain.RecommendInfo;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.viewholders.RecommendViewHolder;
import com.leer.googlemarket.ui.widgets.MyListView;
import com.leer.googlemarket.ui.widgets.fly.ShakeListener;
import com.leer.googlemarket.ui.widgets.fly.StellarMap;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Leer on 2017/5/9.
 */

public class RecommendFragment extends BaseFragment {

    private ArrayList<RecommendInfo> mRecommendInfos;

    @Override
    public View createSuccessView() {
        final StellarMap stellar = new StellarMap(UIUtils.getContext());
        stellar.setAdapter(new RecommendAdapter());
        // 随机方式, 将控件划分为9行6列的的格子, 然后在格子中随机展示
        stellar.setRegularity(6, 9);

        // 设置内边距10dp
        int padding = UIUtils.dip2px(10);
        stellar.setInnerPadding(padding, padding, padding, padding);

        // 设置默认页面, 第一组数据
        stellar.setGroup(0, true);

        ShakeListener shake = new ShakeListener(UIUtils.getContext());
        shake.setOnShakeListener(new ShakeListener.OnShakeListener() {

            @Override
            public void onShake() {
                stellar.zoomIn();// 跳到下一页数据
            }
        });

        return stellar;
    }

    private class RecommendAdapter implements StellarMap.Adapter {
        @Override
        public int getGroupCount() {
            return 2;
        }

        //每组的个数
        @Override
        public int getCount(int group) {
            int count = mRecommendInfos.size()/getGroupCount();
            //如果最后一组除不尽,就将其加在最后一组上面
            if(group == getGroupCount()-1){
                count += mRecommendInfos.size()%getGroupCount();
            }
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            // 因为position每组都会从0开始计数, 所以需要将前面几组数据的个数加起来,才能确定当前组获取数据的角标位置
            position += (group) * getCount(group - 1);

            // System.out.println("pos:" + position);

            final String keyword = mRecommendInfos.get(position).recom;

            TextView view = new TextView(UIUtils.getContext());
            view.setText(keyword);

            Random random = new Random();
            // 随机大小, 16-25
            int size = 16 + random.nextInt(10);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);

            // 随机颜色
            // r g b, 0-255 -> 30-230, 颜色值不能太小或太大, 从而避免整体颜色过亮或者过暗
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            view.setTextColor(Color.rgb(r, g, b));

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), keyword,
                            Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }

        // 返回下一组的id
        // 当isZoomIn为true时代表往下滑
        // 当isZoomIn为false时代表往上滑
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                // 往下滑加载上一页
                if (group > 0) {
                    group--;
                } else {
                    // 跳到最后一页
                    group = getGroupCount() - 1;
                }
            } else {
                // 往上滑加载下一页
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    // 跳到第一页
                    group = 0;
                }
            }
            return group;
        }
    }

    @Override
    public LoadState onLoad() {
        String url = URLBuilder.recommendUrlBuilder("0");
        String data = MyHttpConnection.getData(url);
        LogUtils.d("Recommend : "+data);

        mRecommendInfos = JsonParser.parserRecommendInfo(data);

        return LoadState.LOAD_SUCCESS;
    }
}
