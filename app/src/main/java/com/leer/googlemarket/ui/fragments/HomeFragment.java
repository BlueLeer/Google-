package com.leer.googlemarket.ui.fragments;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;

import com.leer.googlemarket.domain.AppInfo;
import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.manager.DownloadManager;
import com.leer.googlemarket.manager.ThreadManager;
import com.leer.googlemarket.ui.acitvities.HomeAppDetailsActivity;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.viewholders.HomeHeaderViewHolder;
import com.leer.googlemarket.ui.viewholders.HomeViewHolder;
import com.leer.googlemarket.ui.widgets.MyListView;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/9.
 */

public class HomeFragment extends BaseFragment {

    private ArrayList<AppInfo> mList;
    private ArrayList<String> mPictures;

    @Override
    public View createSuccessView() {
        final MyListView mListView = new MyListView(UIUtils.getContext());

        HomeHeaderViewHolder header = new HomeHeaderViewHolder();
        View rootView = header.getRootView();
        mListView.addHeaderView(rootView);

        //此处应该注意:先添加头布局,再设置数据适配器
        mListView.setAdapter(new MyAdapter(mList));
        if(mPictures != null){
            header.setData(mPictures);
        }

//        testDownload();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取当前点击的位置处的AppInfo
                AppInfo appInfo = mList.get(position-1);
                //跳转到应用信息的详情页
                Intent i = HomeAppDetailsActivity.newIntent(appInfo);
                startActivity(i);
            }
        });

        return mListView;
    }

    private class MyAdapter extends MyBaseAdapter<AppInfo> {

        public MyAdapter(ArrayList<AppInfo> data) {
            super(data);
        }

        @Override
        protected BaseViewHolder getViewHolder() {
            return new HomeViewHolder();
        }

        @Override
        protected ArrayList<AppInfo> loadMore() {
            String moreData = MyHttpConnection.getData(URLBuilder.homeUrlBuilder(mList.size()+""));
            ArrayList<AppInfo> appInfos = JsonParser.parserHomeAppInfo(moreData);
            return appInfos;
        }

    }


//    private void testDownload(){
//        DownloadManager.getInstance().download(mList.get(0));
//    }

    @Override
    public LoadState onLoad() {
        if (mList == null) {
            mList = new ArrayList<>();
        }

        //获取json数据
        String result = MyHttpConnection.getData(URLBuilder.homeUrlBuilder("0"));


        LogUtils.d("我是homeFragment的数据 : "+result);
        //解析result数据
        mList = JsonParser.parserHomeAppInfo(result);

        mPictures = JsonParser.parserHomePicture(result);

//        LogUtils.d(result);
        return LoadState.LOAD_SUCCESS;
    }
}
