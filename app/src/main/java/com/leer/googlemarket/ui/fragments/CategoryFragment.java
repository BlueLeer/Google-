package com.leer.googlemarket.ui.fragments;

import android.util.Log;
import android.view.View;

import com.leer.googlemarket.domain.CategoryInfo;
import com.leer.googlemarket.domain.JsonParser;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.ui.adapters.MyBaseAdapter;
import com.leer.googlemarket.ui.viewholders.BaseViewHolder;
import com.leer.googlemarket.ui.viewholders.CategoryViewHolder;
import com.leer.googlemarket.ui.widgets.MyListView;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/9.
 */

public class CategoryFragment extends BaseFragment {

    private ArrayList<CategoryInfo> mCategoryInfos;

    @Override
    public View createSuccessView() {
        MyListView myListView = new MyListView(UIUtils.getContext());
        myListView.setAdapter(new MyAdapter(mCategoryInfos));
        return myListView;
    }

    private class MyAdapter extends MyBaseAdapter<CategoryInfo>{
        public MyAdapter(ArrayList<CategoryInfo> data) {
            super(data);
        }

        @Override
        protected BaseViewHolder getViewHolder() {
            return new CategoryViewHolder();
        }

        @Override
        protected ArrayList<CategoryInfo> loadMore() {
            return null;
        }
    }

    @Override
    public LoadState onLoad() {
        String url = URLBuilder.categoryUrlBuilder("0");
        String result = MyHttpConnection.getData(url);
        mCategoryInfos = JsonParser.parserCategoryInfo(result);
        Log.d("xxxxx",result);
        return LoadState.LOAD_SUCCESS;
    }
}
