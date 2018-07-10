package com.niko.slib.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.niko.slib.R;
import com.niko.slib.utils.DimenUtils;


/**
 * Created by niko on 15/10/19.
 */
public class AlphabetView extends View{

    public static final String ALPHABETS[] = {"↑", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint = new Paint();
    private OnLetterTouchListener mOnLetterTouchListener;

    public AlphabetView(Context context) {
        super(context);
        init();
    }

    public AlphabetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlphabetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getResources().getDimension(R.dimen.alphabet_text_size));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerWidth = getWidth() / 2;
        int height = canvas.getHeight();
        int paddingVertical = DimenUtils.dp2Px(getContext(), 5);

        //间隔的个数 = 字母个数 + 1
        int letterHeight = (height - (ALPHABETS.length + 1) * paddingVertical) / ALPHABETS.length;
        for (int i = 0; i < ALPHABETS.length; i++) {
            float fontWidth = mPaint.measureText(ALPHABETS[i]);
            canvas.drawText(ALPHABETS[i], centerWidth - fontWidth / 2, (i + 1) * (paddingVertical + letterHeight), mPaint);
        }
    }

    public void setOnLetterTouchListener(OnLetterTouchListener onLetterTouchListener) {
        mOnLetterTouchListener = onLetterTouchListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 被触摸的是字母表中的第几个字符
        int currentIndex = (int) (event.getY() / getMeasuredHeight() * ALPHABETS.length);
        if (currentIndex < 0) {
            currentIndex = 0;
        } else if (currentIndex >= ALPHABETS.length) {
            currentIndex = ALPHABETS.length - 1;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                if (mOnLetterTouchListener != null) {
                    mOnLetterTouchListener.onLetterTouch(currentIndex, ALPHABETS[currentIndex]);
                    setBackgroundColor(getResources().getColor(R.color.alpha_gray));
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                if (mOnLetterTouchListener != null) {
                    mOnLetterTouchListener.onTouchUp();
                    setBackgroundColor(Color.TRANSPARENT);
                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (mOnLetterTouchListener != null) {
                    mOnLetterTouchListener.onLetterTouch(currentIndex, ALPHABETS[currentIndex]);
                    setBackgroundColor(getResources().getColor(R.color.alpha_gray));
                    invalidate();
                }
                break;
            }
            default:
                break;
        }
        return true;
    }

    public interface OnLetterTouchListener {
        void onLetterTouch(int index, String letter);
        void onTouchUp();
    }
}
