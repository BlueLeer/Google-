package com.leer.googlemarket.ui.viewholders;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.leer.googlemarket.R;
import com.leer.googlemarket.domain.AppInfoDetail;
import com.leer.googlemarket.utils.UIUtils;

/**
 * Created by Leer on 2017/5/27.
 */

public class AppIntroViewHolder extends BaseViewHolder<AppInfoDetail> {

    private TextView tv_app_intro;
    private RelativeLayout ll_arrow;
    private ImageView iv_arrow;

    //默认情况下"应用介绍"是只显示7行的,也就是"关"
    private boolean isOpen = false;
    private View view;
    private TextView tv_author;

    @Override
    protected View initView() {
        view = UIUtils.inflateRes(R.layout.view_app_intro);
        tv_app_intro = (TextView) view.findViewById(R.id.tv_app_intro);

        ll_arrow = (RelativeLayout) view.findViewById(R.id.ll_arrow);
        iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
        tv_author = (TextView) view.findViewById(R.id.tv_author);

        return view;
    }

    @Override
    protected void refreshView(AppInfoDetail data) {

        String introStr = data.des;
        String author = data.author;
        tv_app_intro.setText(introStr);
        tv_author.setText(author);

        //将界面更新的逻辑放入主线程的消息队列中,保证前面UI更新以后才会走到该方法
        tv_app_intro.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams pa = (LinearLayout.LayoutParams) tv_app_intro.getLayoutParams();
                int a = getShortHeight();
                int b = getFullHeight();
                Log.d("xxx", "测量7行的高度是 : " + a);
                Log.d("xxx", "测量完整的高度是 : " + b);

                //默认情况下,只显示7行的文字
                pa.height = getShortHeight();
                tv_app_intro.setLayoutParams(pa);

            }
        });

        //设置点击以后的折叠事件
        ll_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                toggle();
            }
        });
    }

    private void toggle() {
        //获取动画
        ValueAnimator animator = getAnim(isOpen);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams pa = (LinearLayout.LayoutParams) tv_app_intro.getLayoutParams();
                pa.height = height;
                tv_app_intro.setLayoutParams(pa);
            }
        });

        //动画结束后,改变箭头方向
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isOpen) {
                    iv_arrow.setImageResource(R.drawable.arrow_up);
                } else {
                    iv_arrow.setImageResource(R.drawable.arrow_down);
                }

//                动画执行结束了,此时,ScrollView应该滑动到底部
//                有时候,scrollview折叠的内容还没有完全的绘制出来,此时,ScrollView滑动到底部
//                就会导致bug产生
//                因此,最稳妥的方式,就是将fullScroll()方法,放在主线程的消息队列中进行处理
                tv_app_intro.post(new Runnable() {
                    @Override
                    public void run() {
                        getScrollView().fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * ScrollView不一定是该布局的直接父控件,因此,需要一层层的来找ScrollView
     *
     * @return 返回父布局是ScrollView的控件
     */
    public ScrollView getScrollView() {
        ViewParent parent = tv_app_intro.getParent();

        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }
        return (ScrollView) parent;
    }

    private ValueAnimator getAnim(boolean isOpen) {
        ValueAnimator animator = null;
        if (isOpen) {
            //执行展开的动画
            animator = ValueAnimator.ofInt(getShortHeight(), getFullHeight());
        } else {
            //执行关闭的动画
            animator = ValueAnimator.ofInt(getFullHeight(), getShortHeight());
        }
        animator.setDuration(200);
        return animator;
    }

    //需要折叠7行文字,因此这里模拟7行文字进行折叠,测出包含7行文字的高度
    public int getShortHeight() {
        //获取控件的宽度
        int width = tv_app_intro.getMeasuredWidth();

        TextView textView = new TextView(UIUtils.getContext());
        textView.setText(getData().des);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setMaxLines(7);

//        //注意:这里不能填textView.measure(0,0);
//        //因为:textView不是由xml文件当中设置的宽和高决定的
        int newWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int newHeight = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        textView.measure(newWidth, newHeight);
//        textView.measure(0,0);
        int measuredHeight = textView.getMeasuredHeight();

        return measuredHeight;
    }

    public int getFullHeight() {
        //获取控件的宽度
        int width = tv_app_intro.getMeasuredWidth();

        TextView textView = new TextView(UIUtils.getContext());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//        textView.setMaxLines(7);
        textView.setText(getData().des);

//        //注意:这里不能填textView.measure(0,0);
//        //因为:textView不是由xml文件当中设置的宽和高决定的
        int newWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int newHeight = View.MeasureSpec.makeMeasureSpec(2000, View.MeasureSpec.AT_MOST);
        textView.measure(newWidth, newHeight);
//        textView.measure(0,0);
        int measuredHeight = textView.getMeasuredHeight();

        return measuredHeight;
    }


}
