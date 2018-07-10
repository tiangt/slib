package com.niko.slib.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.niko.slib.R;


/**
 * Created by niko on 15/8/5.
 */
public class SquareImageView  extends android.support.v7.widget.AppCompatImageView {
    private String mOrientation = "w";//"h"

    public SquareImageView(Context context) {
        super(context);
        init(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.squareimageview);
        mOrientation = typedArray.getString(R.styleable.squareimageview_reference);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mOrientation != null && mOrientation.startsWith("h")) {
            int height = getMeasuredHeight();
            setMeasuredDimension(height, height);
        } else {
            int width = getMeasuredWidth();
            setMeasuredDimension(width, width);
        }
    }

}
