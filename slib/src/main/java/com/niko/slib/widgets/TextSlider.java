package com.niko.slib.widgets;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import com.niko.slib.R;
import com.niko.slib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scanor on 2016/1/12.
 */
public class TextSlider<T> extends ImageSwitcher {
   
    private int mCurPosition;
    private GestureDetector mDetector;
    private Animation mTopIn, mBottomIn, mBottomOut, mTopOut;
    private List<T> mUrls = new ArrayList<>();
    private MyCountDownTimer mMyCountDownTimer = new MyCountDownTimer(3000, 1000);
    private OnItemClickListener mOnItemClickListener;
    private TextPlayer<T> mTextPlayer;
    private boolean mIsStop = false;
    private boolean mAnimDirectionLeft = true;//动画方向是否左边

    public TextSlider(Context context) {
        super(context);
        init();
    }

    public TextSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public boolean isStop() {
        return mIsStop;
    }

    private void init() {
        mDetector = new GestureDetector(getContext(), new MyOnGestureListener());
        mTopIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_top);
        mTopOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_bottom);

        mBottomIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_from_bottom);
        mBottomOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_to_top);

        setInAnimation(mTopIn);
        setOutAnimation(mTopOut);
        setBackgroundColor(Color.WHITE);
        setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                TextView imageView = new TextView(getContext());
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mMyCountDownTimer.start();
                }
                mDetector.onTouchEvent(event);
                return true;
            }
        });
    }


    public void start() {
        mIsStop = false;
        mMyCountDownTimer.start();
    }

    public void stop() {
        mIsStop = true;
    }

    public void setUrls(List<T> urls) {
        if (urls == null) {
            mUrls = new ArrayList<>();
        } else {
            mUrls.clear();
            mUrls = urls;
        }
    }

    public void setTextPlayer(TextPlayer textPlayer) {
        mTextPlayer = textPlayer;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int index, View imageView);
    }

    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mMyCountDownTimer.cancel();
            LogUtils.d("广告:onSingleTapConfirmed");
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(mCurPosition, getCurrentView());
            }
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            LogUtils.d("广告:onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtils.d("广告:onFling");
            if (mUrls.isEmpty()) {
                return false;
            }
            if (velocityX > 0) {
                --mCurPosition;
                if (mCurPosition < 0) {
                    mCurPosition = mUrls.size() - 1;
                }
                displayLeftImage(mCurPosition);
            } else {//右边
                ++mCurPosition;
                mCurPosition = mCurPosition % mUrls.size();
                autoDisplayImage(mCurPosition);
            }
            return false;
        }
    }

    private class ImageDisplayRunnable implements Runnable {
        private int position;

        public ImageDisplayRunnable(int position) {
            this.position = position;
        }

        @Override
        public void run() {
            autoDisplayImage(position);
        }
    }

    private void autoDisplayImage(int position) {
        if (mAnimDirectionLeft) {
            setInAnimation(mBottomIn);
            setOutAnimation(mBottomOut);
            mAnimDirectionLeft = false;
        }
        TextView imageView = (TextView) getNextView();
        if (mTextPlayer != null) {
            mTextPlayer.onTextDisPlay(imageView, mUrls.get(position));
        }
//        LogUtils.d("下标" + mCurPosition);
        showNext();
    }

    private void displayLeftImage(int position) {
        if (!mAnimDirectionLeft) {
            setInAnimation(mTopIn);
            setOutAnimation(mTopOut);
            mAnimDirectionLeft = true;
        }
        TextView imageView = (TextView) getNextView();
        if (mTextPlayer != null) {
            mTextPlayer.onTextDisPlay(imageView, mUrls.get(position));
        }
        showPrevious();
    }

    private class MyCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link
         *                          #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            if (mIsStop) {
                cancel();
                return;
            }
//            LogUtils.d("onFinish(long millisUntilFinished)");
            if (mUrls.isEmpty()) {
                return;
            }
            ++mCurPosition;
            mCurPosition = mCurPosition % mUrls.size();
            post(new ImageDisplayRunnable(mCurPosition));
            start();
        }
    }

    public interface TextPlayer<T>{
        void  onTextDisPlay(TextView imageView, T object);
    }
}

