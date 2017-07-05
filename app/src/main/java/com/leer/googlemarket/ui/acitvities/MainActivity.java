package com.leer.googlemarket.ui.acitvities;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.leer.googlemarket.R;
import com.leer.googlemarket.ui.fragments.BaseFragment;
import com.leer.googlemarket.ui.fragments.FragmentFactory;
import com.leer.googlemarket.ui.widgets.PagerTab;
import com.leer.googlemarket.utils.UIUtils;

public class MainActivity extends BaseActivity {

    private ViewPager view_pager;
    private PagerTab pager_tab;
    private MyPagerAdapter mPagerAdapter;
    private String[] mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //显示工具栏上面的应用logo,显示太丑
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.drawable.ic_launcher);
//        actionBar.setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_main);
        initUI();

        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置标题栏上的返回按钮是否显示,默认情况下是一个向左的箭头
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);


        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        //设置侧边栏的开关
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close);
//        toggle.onOptionsItemSelected(ActionBar的item的id);
    }

    private void initUI() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        pager_tab = (PagerTab) findViewById(R.id.pager_tab);

        //tab上面显示的页签
        mTabs = UIUtils.getStringArrRes(R.array.tab_names);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        view_pager.setAdapter(mPagerAdapter);
        pager_tab.setViewPager(view_pager);

        pager_tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //直接从工厂当中去取,由工厂自己决定是创建还是直接返回
                BaseFragment baseFragment = FragmentFactory.creatFragment(position);
                //在用户点击一个页面的时候再加载数据
                baseFragment.startLoadData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //使用PagerTab就需要设置此方法
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs[position];
        }

        @Override
        public BaseFragment getItem(int position) {
            return FragmentFactory.creatFragment(position);
        }

        @Override
        public int getCount() {
            return mTabs.length;
        }
    }
}
