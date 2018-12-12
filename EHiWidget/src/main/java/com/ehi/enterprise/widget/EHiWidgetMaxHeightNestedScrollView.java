package com.ehi.enterprise.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

/**
 * time   : 2018/12/07
 *
 * @author : xiaojinzi 30212
 */
public class EHiWidgetMaxHeightNestedScrollView extends NestedScrollView {

    public EHiWidgetMaxHeightNestedScrollView(Context context) {
        this(context, null);
    }

    public EHiWidgetMaxHeightNestedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EHiWidgetMaxHeightNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.EHiWidgetMaxHeightNestedScrollView);
        maxHeight = a.getDimensionPixelSize(R.styleable.EHiWidgetMaxHeightNestedScrollView_ehiMaxHeight,maxHeight);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modelHeight = View.MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        if (maxHeight > 0 && sizeHeight > maxHeight) {
            sizeHeight = maxHeight;
        }
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(sizeHeight,modelHeight));


    }

    private int maxHeight = -1;

}
