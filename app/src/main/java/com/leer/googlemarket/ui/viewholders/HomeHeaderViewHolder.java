package com.leer.googlemarket.ui.viewholders;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.leer.googlemarket.R;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/16.
 */

public class HomeHeaderViewHolder extends BaseViewHolder<ArrayList<String>> {

    private ViewPager viewPager;
    private BitmapUtils mBitmapUtils;
    private RelativeLayout rl;
    private int mLastIndex = 0;

    @Override
    protected View initView() {
        rl = new RelativeLayout(UIUtils.getContext());
        //rl对应的上层布局是AbsListView,也就是ListView对应
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(150));
        rl.setLayoutParams(params);


        viewPager = new ViewPager(UIUtils.getContext());

        rl.addView(viewPager);
        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return rl;
    }

    @Override
    public void setData(ArrayList<String> data) {
        //父类通过调用此方法,调用refreshView()方法
        super.setData(data);
    }

    @Override
    protected void refreshView(final ArrayList<String> data) {
        viewPager.setAdapter(new MyAdapter(data));
        viewPager.setCurrentItem(data.size() * 10000);
        final LinearLayout ll = new LinearLayout(UIUtils.getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.
                WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ll.setLayoutParams(params);


        for(int i = 0;i<data.size();i++){
            ImageView point = new ImageView(UIUtils.getContext());
            int size = UIUtils.dip2px(5);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(size,size);
            params1.gravity = Gravity.CENTER_HORIZONTAL;
            params1.leftMargin = size;
            point.setLayoutParams(params1);
            point.setImageResource(R.drawable.selector_indicator);

            ll.addView(point);
            //初始化的时候,设置选中状态
            if(i == 0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
        }

        rl.addView(ll);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mLastIndex >=0 ){
                    ImageView lastPoint = (ImageView) ll.getChildAt(mLastIndex);
                    lastPoint.setEnabled(false);
                }

                ImageView currentPoint = (ImageView) ll.getChildAt(position%data.size());
                currentPoint.setEnabled(true);

                //记录上次选中的点
                mLastIndex = position%data.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        HomeHeaderTask task = new HomeHeaderTask();
        task.start();
    }

    private class HomeHeaderTask implements Runnable{
        public void start(){
            //清除掉之前发送的所有信息
            UIUtils.getHandler().removeCallbacksAndMessages(null);
            UIUtils.getHandler().post(this);
        }

        @Override
        public void run() {
            int current = viewPager.getCurrentItem();
            current++;
            viewPager.setCurrentItem(current);
            UIUtils.getHandler().postDelayed(this,3000);
        }
    }

    private class MyAdapter extends PagerAdapter {
        ArrayList<String> pics = new ArrayList<>();

        public MyAdapter(ArrayList<String> data) {
            this.pics = data;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String picUrl = pics.get(position%pics.size());
            ImageView imageView = new ImageView(UIUtils.getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mBitmapUtils.display(imageView, ConstantValues.ROOT_URL + "image?name=" + picUrl);
            container.addView(imageView);
            Log.d("xxxxxx", picUrl);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
