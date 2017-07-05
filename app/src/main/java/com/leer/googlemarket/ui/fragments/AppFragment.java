package com.leer.googlemarket.ui.fragments;

import android.view.View;

import com.leer.googlemarket.domain.AppInfo;
import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.ui.viewholders.AppViewHolder;
import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.widgets.MyListView;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/9.
 */

public class AppFragment extends BaseFragment {

    private ArrayList<AppInfo> mAppInfos;

    @Override
    public View createSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new MyAdapter(mAppInfos));
        return myListView;
    }

    @Override
    public LoadState onLoad() {
        String url = URLBuilder.appUrlBuilder("0");
        String result = MyHttpConnection.getData(url);

        mAppInfos = JsonParser.parserAppAppinfo(result);

        LogUtils.d(result);

        return LoadState.LOAD_SUCCESS;
    }

    private class MyAdapter extends MyBaseAdapter<AppInfo>{
        public MyAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        protected BaseViewHolder getViewHolder() {
            return new AppViewHolder();
        }

        @Override
        protected ArrayList<AppInfo> loadMore() {
            String url = URLBuilder.appUrlBuilder(mAppInfos.size()+"");
            String result = MyHttpConnection.getData(url);
            ArrayList<AppInfo> mAppInfos = JsonParser.parserHomeAppInfo(result);

            return mAppInfos;
        }
    }
}
