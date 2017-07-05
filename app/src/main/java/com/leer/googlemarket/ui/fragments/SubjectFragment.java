package com.leer.googlemarket.ui.fragments;

import android.view.View;

import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.domain.SubjectInfo;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.viewholders.SubjectViewHolder;
import com.leer.googlemarket.ui.widgets.MyListView;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/9.
 */

public class SubjectFragment extends BaseFragment {

    private ArrayList<SubjectInfo> mSubjectInfos;

    @Override
    public View createSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());

        myListView.setAdapter(new MyAdapter(mSubjectInfos));
        return myListView;
    }

    @Override
    public LoadState onLoad() {
        String url = URLBuilder.subjectUrlBuilder("0");
        String result = MyHttpConnection.getData(url);

        mSubjectInfos = JsonParser.parserSubjectInfo(result);
        LogUtils.d("我是subject : "+result);
        return LoadState.LOAD_SUCCESS;
    }

    private class MyAdapter extends MyBaseAdapter<SubjectInfo>{
        public MyAdapter(ArrayList<SubjectInfo> data) {
            super(data);
        }

        @Override
        protected BaseViewHolder getViewHolder() {
            return new SubjectViewHolder();
        }

        @Override
        protected ArrayList<SubjectInfo> loadMore() {
            String url = URLBuilder.subjectUrlBuilder(mSubjectInfos.size()+"");
            String result = MyHttpConnection.getData(url);

            ArrayList<SubjectInfo> infoArrayList = JsonParser.parserSubjectInfo(result);
            return infoArrayList;
        }
    }
}
