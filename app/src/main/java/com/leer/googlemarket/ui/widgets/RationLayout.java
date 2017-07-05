package com.leer.googlemarket.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.leer.googlemarket.R;

/** 该类为了继承自FrameLayout,里面嵌套一个ImageView
 * 为了保证ImageView中显示的图片的长和宽根据图片实际的长和宽等比例的缩放
 * 定义了此类
 * Created by Leer on 2017/5/13.
 */

public class RationLayout extends FrameLayout {

    private float mRation = 0;;

    public RationLayout(Context context) {
        this(context,null);

    }

    public RationLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //通过命名空间和属性名获取属性值
//        mRation = attrs.getAttributeFloatValue("http://schemas.android.com/apk/res-auto","ration",0);

        //通过工具获取,在declare-styleable的时候,系统会自动的生成一个属性集所指向的的R文件
        //typeArray中可以获取到
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RationLayout);
        mRation = typedArray.getFloat(R.styleable.RationLayout_ration,0);
        typedArray.recycle();//提高性能
    }

    // widthMeasureSpec 是MeasureSpec的一个值,该值包含了Mode和Size
    //其中mode值有:
    //1.UNSPECIFIED:未指定控件的长和宽,需要根据所有的孩子的大小来决定
    //2.AT_MOST:该控件想多大就多大
    //3.EXACTLY:宽高写死
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //拿到属性(ration:宽和高的比)

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        //宽度是指定的,高度位指定,并且宽高比例值有效
        if(widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY && mRation > 0){
            //获取图片的实际宽度
            float imageWidth = width - getPaddingRight() - getPaddingLeft();
            //计算出图片实际的高度
            float imageHeight = imageWidth / mRation;

            height = (int) (imageHeight + getPaddingTop() + getPaddingBottom() + 0.5f);

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
