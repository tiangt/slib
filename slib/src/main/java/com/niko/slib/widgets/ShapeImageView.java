package com.niko.slib.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by niko on 15/10/4.
 */
public class ShapeImageView extends android.support.v7.widget.AppCompatImageView {

    private RectF mRectF;
    private Rect mRect;
    Path mRoundRectPath;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mProgressPercent = -1f;

    public ShapeImageView(Context context) {
        super(context);
        mPaint.setARGB(0x66, 0, 0, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setARGB(0x66, 0, 0, 0);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setARGB(0x66, 0, 0, 0);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mProgressPercent != -1) {
            if (mRectF == null) {
                mRectF = new RectF(0, 0, getWidth(), getHeight());
                mRect = new Rect(0, 0, getWidth(), getHeight());
                mRoundRectPath = new Path();
                mRoundRectPath.addRoundRect(mRectF, 0, 0, Path.Direction.CW);
            }

            canvas.clipPath(mRoundRectPath);
            mRect.top = (int)(mRect.bottom * mProgressPercent);
            canvas.drawRect(mRect, mPaint);
        }
    }

    public void setProgress(float percent) {
        mProgressPercent = percent;
        invalidate();
    }
}
