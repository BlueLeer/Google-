package com.leer.googlemarket.ui.viewholders;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfoDetail;
import com.leer.googlemarket.global.ConstantValues;
import com.leer.googlemarket.utils.BitmapUtilsHelper;
import com.leer.googlemarket.utils.LogUtils;
import com.leer.googlemarket.utils.UIUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by Leer on 2017/5/27.
 */

public class AppSafeViewHolder extends BaseViewHolder<AppInfoDetail> {

    private ImageView safe_icon_1;
    private ImageView safe_icon_2;
    private ImageView safe_icon_3;
    private ImageView safe_icon_4;
    private ImageView iv_safe_des_icon_1;
    private ImageView iv_safe_des_icon_2;
    private ImageView iv_safe_des_icon_3;
    private ImageView iv_safe_des_icon_4;
    private TextView tv_safe_des_text_1;
    private TextView tv_safe_des_text_2;
    private TextView tv_safe_des_text_3;
    private TextView tv_safe_des_text_4;
    private BitmapUtils mBitmapUtils;
    private ImageView arrow;

    private ArrayList<ImageView> mSafeIcons;
    private ArrayList<ImageView> mSafeDesIcons;
    private ArrayList<TextView> mSafeDesTexts;
    private boolean isOpen = false;
    private LinearLayout ll_safe_des_root;
    private int mSafeDesHeight;
    private LinearLayout.LayoutParams mParams;
    private ValueAnimator mAnimator;
    private LinearLayout ll_app_safe_icons;

    @Override
    protected View initView() {
        mSafeIcons = new ArrayList<>();
        mSafeDesIcons = new ArrayList<>();
        mSafeDesTexts = new ArrayList<>();

        View view = UIUtils.inflateRes(R.layout.view_app_safe);
        safe_icon_1 = (ImageView) view.findViewById(R.id.safe_icon_1);
        safe_icon_2 = (ImageView) view.findViewById(R.id.safe_icon_2);
        safe_icon_3 = (ImageView) view.findViewById(R.id.safe_icon_3);
        safe_icon_4 = (ImageView) view.findViewById(R.id.safe_icon_4);
        mSafeIcons.add(safe_icon_1);
        mSafeIcons.add(safe_icon_2);
        mSafeIcons.add(safe_icon_3);
        mSafeIcons.add(safe_icon_4);

        iv_safe_des_icon_1 = (ImageView) view.findViewById(R.id.iv_safe_des_icon_1);
        iv_safe_des_icon_2 = (ImageView) view.findViewById(R.id.iv_safe_des_icon_2);
        iv_safe_des_icon_3 = (ImageView) view.findViewById(R.id.iv_safe_des_icon_3);
        iv_safe_des_icon_4 = (ImageView) view.findViewById(R.id.iv_safe_des_icon_4);
        mSafeDesIcons.add(iv_safe_des_icon_1);
        mSafeDesIcons.add(iv_safe_des_icon_2);
        mSafeDesIcons.add(iv_safe_des_icon_3);
        mSafeDesIcons.add(iv_safe_des_icon_4);

        tv_safe_des_text_1 = (TextView) view.findViewById(R.id.tv_safe_des_text_1);
        tv_safe_des_text_2 = (TextView) view.findViewById(R.id.tv_safe_des_text_2);
        tv_safe_des_text_3 = (TextView) view.findViewById(R.id.tv_safe_des_text_3);
        tv_safe_des_text_4 = (TextView) view.findViewById(R.id.tv_safe_des_text_4);

        mSafeDesTexts.add(tv_safe_des_text_1);
        mSafeDesTexts.add(tv_safe_des_text_2);
        mSafeDesTexts.add(tv_safe_des_text_3);
        mSafeDesTexts.add(tv_safe_des_text_4);


        arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        ll_safe_des_root = (LinearLayout) view.findViewById(R.id.ll_safe_des_root);
        ll_app_safe_icons = (LinearLayout) view.findViewById(R.id.ll_app_safe_icons);

        mBitmapUtils = BitmapUtilsHelper.getBitmapUtils();
        return view;
    }


    @Override
    protected void refreshView(AppInfoDetail data) {
        for (int i = 0; i < 4; i++) {
            if (i < data.safe.size()) {
                AppInfoDetail.Safe safe = data.safe.get(i);
                //安全识别图片
                ImageView iv = mSafeIcons.get(i);
                mBitmapUtils.display(iv, ConstantValues.ROOT_URL + "image?name=" + safe.safeUrl);

                //安全描述图片
                ImageView iv1 = mSafeDesIcons.get(i);
                mBitmapUtils.display(iv1, ConstantValues.ROOT_URL + "image?name=" + safe.safeDesUrl);

                //安全描述文字
                TextView tv = mSafeDesTexts.get(i);
                tv.setText(safe.safeDes);
            } else {
                mSafeIcons.get(i).setVisibility(View.GONE);
                mSafeDesIcons.get(i).setVisibility(View.GONE);
                mSafeDesTexts.get(i).setVisibility(View.GONE);
            }
        }

        ll_safe_des_root.measure(0, 0);
        mSafeDesHeight = ll_safe_des_root.getMeasuredHeight();
        LogUtils.d("安全描述的高度是 : " + mSafeDesHeight);

        mParams = (LinearLayout.LayoutParams) ll_safe_des_root.getLayoutParams();
        mParams.height = 0;
        ll_safe_des_root.setLayoutParams(mParams);

        ll_app_safe_icons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

    }

    private void toggle() {
        isOpen = !isOpen;
        startAnim();

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                mParams.height = height;
                Log.d("xxxxxxxx", "height : " + height);
                ll_safe_des_root.setLayoutParams(mParams);
            }
        });

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束,更改箭头
                if (isOpen) {
                    arrow.setImageResource(R.drawable.arrow_up);
                } else {
                    arrow.setImageResource(R.drawable.arrow_down);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void startAnim() {
        mAnimator = null;
        if (!isOpen) {
            //执行关的动画
            mAnimator = ValueAnimator.ofInt(mSafeDesHeight, 0);
        } else {
            //执行开的动画
            mAnimator = ValueAnimator.ofInt(0, mSafeDesHeight);
        }
        mAnimator.setDuration(200);
        mAnimator.start();
    }
}
