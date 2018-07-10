package com.niko.slib.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by niko on 16/6/5.
 */

public class BannerView extends android.support.v7.widget.AppCompatImageView {
    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec/2);
    }
}
