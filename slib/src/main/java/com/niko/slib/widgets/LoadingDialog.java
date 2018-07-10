package com.niko.slib.widgets;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.niko.slib.R;
import com.niko.slib.databinding.ViewLoadingBinding;


/**
 * Created by niko on 16/6/21.
 */

public class LoadingDialog extends Dialog {
    private ViewLoadingBinding mViewLoadingBinding;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        mViewLoadingBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_loading, null, false);

        // 使用ImageView显示动画
        setContentView(mViewLoadingBinding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.loading);
        mViewLoadingBinding.ivImage.startAnimation(hyperspaceJumpAnimation);
    }

    public void setContent(String tips) {
        if (TextUtils.isEmpty(tips)) {
            mViewLoadingBinding.tvTips.setVisibility(View.GONE);
        } else {
            mViewLoadingBinding.tvTips.setVisibility(View.VISIBLE);
            mViewLoadingBinding.tvTips.setText(tips);
        }
    }
}
