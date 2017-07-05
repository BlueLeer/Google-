package com.leer.googlemarket.ui.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/9.
 */

public class GameFragment extends BaseFragment {
    @Override
    public View createSuccessView() {
        return null;
    }

    @Override
    public LoadState onLoad() {
        return LoadState.LOAD_EMPTY;
    }
}
