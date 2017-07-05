package com.leer.googlemarket.ui.acitvities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfo;
import com.leer.googlemarket.domain.AppInfoDetail;
import com.leer.googlemarket.domain.DownloadInfo;
import com.leer.googlemarket.global.DownloadState;
import com.leer.googlemarket.global.LoadState;
import com.leer.googlemarket.http.MyHttpConnection;
import com.leer.googlemarket.manager.DownloadManager;
import com.leer.googlemarket.ui.viewholders.AppDetailViewHolder;
import com.leer.googlemarket.ui.viewholders.AppIntroViewHolder;
import com.leer.googlemarket.ui.viewholders.AppSafeViewHolder;
import com.leer.googlemarket.ui.viewholders.AppScreenViewHolder;
import com.leer.googlemarket.ui.widgets.MyFrameLayout;
import com.leer.googlemarket.ui.widgets.ProgressHorizontal;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.leer.googlemarket.utils.URLBuilder;

/**
 * Created by Leer on 2017/5/26.
 */
public class HomeAppDetailsActivity extends BaseActivity implements DownloadManager.DownloadObserver, View.OnClickListener {
    private static AppInfo mAppInfo;
    private AppInfoDetail appInfoDetail;
    private Button mDownloadBtn;
    private DownloadManager mDownloadManager;
    private FrameLayout mFl_progress;
    private DownloadState mCurrentState = DownloadState.UNDO;
    private float mProgress;
    private DownloadInfo mDownloadInfo;
    private ProgressHorizontal mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyFrameLayout mFrameLayout = new MyFrameLayout(UIUtils.getContext()) {

            @Override
            protected View createSuccessView() {
                return HomeAppDetailsActivity.this.createSuccessView();
            }

            @Override
            protected LoadState onLoad() {
                String url = URLBuilder.appDetailUrlBuilder(mAppInfo.packageName);
                String result = MyHttpConnection.getData(url);
                LogUtils.d("我是应用的详情信息页面 : " + result);

                Gson gson = new Gson();
                appInfoDetail = gson.fromJson(result, AppInfoDetail.class);
                LogUtils.d("appInfoDetail.safe.get(0).safeDes : " + appInfoDetail.safe.get(0).safeDes);

                return LoadState.LOAD_SUCCESS;
            }
        };
        setContentView(mFrameLayout);
        mFrameLayout.loadData();

        initActionBar();
    }

    //将该界面分割成几个不同的部分
    //分别是:应用信息部分(例如:应用名称,应用评分,应用版本,下载日期等等)
    //应用安全部分,认证的部分
    //应用截图部分
    //应用介绍部分
    private View createSuccessView() {
        View view = UIUtils.inflateRes(R.layout.view_appinfo_detail_success);
        mDownloadBtn = (Button) view.findViewById(R.id.bt_download);


        mFl_progress = (FrameLayout) view.findViewById(R.id.fl_progress);
        mProgressBar = new ProgressHorizontal(UIUtils.getContext());
        mProgressBar.setProgressBackgroundResource(R.drawable.progress_bg);
        mProgressBar.setProgressResource(R.drawable.progress_normal);
        mProgressBar.setProgressTextColor(Color.WHITE);
        mProgressBar.setProgressTextSize(UIUtils.dip2px(16));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        mProgressBar.setLayoutParams(params);
        mFl_progress.addView(mProgressBar);


        FrameLayout appInfoView = (FrameLayout) view.findViewById(R.id.fl_app_info);
        FrameLayout appSafeView = (FrameLayout) view.findViewById(R.id.fl_app_safe);
        FrameLayout appScreens = (FrameLayout) view.findViewById(R.id.fl_app_screens);
        FrameLayout appIntro = (FrameLayout) view.findViewById(R.id.fl_app_intro);

        //应用信息部分
        AppDetailViewHolder appDetailViewHolder = new AppDetailViewHolder();
        View mRootView = appDetailViewHolder.getRootView();
        appInfoView.addView(mRootView);
        appDetailViewHolder.setData(appInfoDetail);

        //应用安全认证界面
        AppSafeViewHolder appSafeViewHolder = new AppSafeViewHolder();
        View safeView = appSafeViewHolder.getRootView();
        appSafeView.addView(safeView);
        appSafeViewHolder.setData(appInfoDetail);

        //应用截图部分
        AppScreenViewHolder appScreenViewHolder = new AppScreenViewHolder();
        View appScreensView = appScreenViewHolder.getRootView();
        appScreens.addView(appScreensView);
        appScreenViewHolder.setData(appInfoDetail);

        //应用介绍部分
        AppIntroViewHolder appIntroViewHolder = new AppIntroViewHolder();
        View appIntroView = appIntroViewHolder.getRootView();
        appIntro.addView(appIntroView);
        appIntroViewHolder.setData(appInfoDetail);

        //获取被观察者
        mDownloadManager = DownloadManager.getInstance();
        //注册观察者
        mDownloadManager.registerObserver(this);

        initDownload();

        return view;
    }

    private void initDownload() {
        mDownloadBtn.setOnClickListener(this);
        mProgressBar.setOnClickListener(this);
        mDownloadInfo = mDownloadManager.getDownloadInfo(mAppInfo);
        if (mDownloadInfo != null) {
            mCurrentState = mDownloadInfo.getCurrentState();
            mProgress = mDownloadInfo.getProgress();
        } else {
            mCurrentState = DownloadState.UNDO;
            mProgress = 0;
        }


        //刷新界面
        refreshUI(mCurrentState, mProgress);
    }

    private void refreshUI(DownloadState currentState, float progress) {
        switch (currentState) {
            case UNDO:
            case WAITING:
                mDownloadBtn.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                break;
            case DOWNLOADING:
                mDownloadBtn.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
//                mProgressBar.setCenterText("下载中...");
                mProgressBar.setProgress(progress);
                break;
            case PAUSE:
                mDownloadBtn.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setCenterText("暂停");
                break;
            case SUCCESS:
                mDownloadBtn.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setCenterText("安装");
                break;
            case ERROR:
                mDownloadBtn.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setCenterText("错误");
                break;
            default:
                break;
        }
    }

    //标题栏上面设置应用的logo
    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置标题栏上的返回按钮是否显示,默认情况下是一个向左的箭头
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public static Intent newIntent(AppInfo appInfo) {
        mAppInfo = appInfo;
        return new Intent(UIUtils.getContext(), HomeAppDetailsActivity.class);
    }

    private void testDownload() {
//        DownloadManager.getInstance().download(mAppInfo);
    }

    @Override
    public void onDownloadStateChanged(DownloadInfo info) {
        DownloadState currentState = info.getCurrentState();
        mCurrentState = currentState;
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                //刷新界面
                refreshUI(mCurrentState, mProgress);
            }
        });
    }

    @Override
    public void onDownloadProgress(DownloadInfo info) {
        float progress = info.getProgress();
        mProgress = progress;
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                //刷新界面
                refreshUI(mCurrentState, mProgress);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (mCurrentState) {
            case UNDO:
            case WAITING:
                mCurrentState = DownloadState.DOWNLOADING;
                //启动下载
                mDownloadManager.download(mAppInfo);
                mDownloadInfo = mDownloadManager.getDownloadInfo(mAppInfo);
                break;
            case DOWNLOADING:
                mCurrentState = DownloadState.PAUSE;
                //暂停下载
                mDownloadManager.pause(mAppInfo);
                break;
            case PAUSE:
                mCurrentState = DownloadState.DOWNLOADING;
                mDownloadManager.download(mAppInfo);
                break;
            case SUCCESS:
                mCurrentState = DownloadState.SUCCESS;
                mDownloadManager.install(mAppInfo);
                break;
            case ERROR:
                mCurrentState = DownloadState.DOWNLOADING;
                break;
            default:
                break;
        }

        refreshUI(mCurrentState, mDownloadInfo.getProgress());
    }
}
