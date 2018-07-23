package com.niko.slib.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.niko.slib.MyActivityManager;
import com.niko.slib.R;
import com.niko.slib.utils.DimenUtils;
import com.niko.slib.utils.NetUtils;
import com.niko.slib.widgets.LoadingDialog;
import com.niko.slib.widgets.PopupBannerView;
import com.niko.slib.widgets.titlebar.Menu;
import com.niko.slib.widgets.titlebar.MenuItem;
import com.niko.slib.widgets.titlebar.TitleBar;

import java.lang.reflect.Field;

/**
 * Created by niko on 15/10/14.
 */
public class SlibActivity<T extends ViewDataBinding> extends AppCompatActivity implements TitleBar.OnTitleBarClickListener {
    protected final String TAG = this.getClass().getName();
    protected T mDataBinding;
    private View mStatusBarBackgroundView;
    private TitleBar mTitleBar;
    private LoadingDialog mLoadingDialog;
    //    private NetStateReceiver mNetStateReceiver;
    private PopupBannerView mPopupBannerView;
    private boolean mEnableNetNotify = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar();

        //往栈中插入一个
        MyActivityManager.getManager().push(this);
        mLoadingDialog = new LoadingDialog(this);

        mPopupBannerView = new PopupBannerView(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mNetStateReceiver = new NetStateReceiver();
    }

    protected void initTitleBar() {
        //初始化TitleBar
        mTitleBar = new TitleBar(this);
        mTitleBar.setOnTitleBarClickListener(this);
        initActionBar();
        onCreateMenu(mTitleBar.getMenu());
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(mNetStateReceiver, filter);
    }

    public PopupBannerView getPopupBannerView() {
        return mPopupBannerView;
    }

    public LoadingDialog getLoadingDialog() {
        return mLoadingDialog;
    }

    public void showLoading() {
        mLoadingDialog.show();
    }

    public void hideLoading() {
        mLoadingDialog.dismiss();
    }

    protected void onCreateMenu(Menu menu) {
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    @Override
    public void onLeftClick(View view) {
        finish();
    }

    @Override
    public void onCenterClick(View view) {

    }

    /**
     * @param itemView 被点击的itemView
     * @param menuItem 被点击的item对应的数据
     */
    @Override
    public void onRightClick(View itemView, MenuItem menuItem) {

    }

    protected void bindContentView(@LayoutRes int layoutId) {
        mDataBinding = DataBindingUtil.setContentView(this, layoutId);
    }

    protected void initActionBar() {
        translucentStatusBarActionBar();
        View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int h = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            h = getStatusBarHeight();
        view.setPadding(0, DimenUtils.dp2Px(50) + h, 0, 0);
    }

    private void translucentStatusBarActionBar() {
        int height = getStatusBarHeight();
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        addStatusBarView(height, viewGroup);
        addTitleBarView(height, viewGroup);
    }

    private void addStatusBarView() {
        int height = getStatusBarHeight();
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        addStatusBarView(height, viewGroup);
    }

    private void addStatusBarView(int height, ViewGroup viewGroup) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mStatusBarBackgroundView = new View(this);

            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    height);

            mStatusBarBackgroundView.setLayoutParams(lParams);
            viewGroup.addView(mStatusBarBackgroundView);

        }
    }

    private void addTitleBarView(int height, ViewGroup viewGroup) {
        viewGroup.addView(mTitleBar);
        mTitleBar.setPadding(0, height, 0, 0);
    }

    public void setFitContentWindow(boolean fitContentWindow) {
        if (fitContentWindow) {
            View view = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            view.setPadding(0, 0, 0, 0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarColor(R.color.transparent);
        }
        getTitleBar().setBackgroundColor(Color.TRANSPARENT);
    }

    public void setStatusBarColor(@ColorRes int color) {
        if (mStatusBarBackgroundView != null) {
            mStatusBarBackgroundView.setBackgroundColor(getResources().getColor(color));
        }
    }

    public void setStatusBarBackgroundResource(@DrawableRes int drawable) {
        if (mStatusBarBackgroundView != null) {
            mStatusBarBackgroundView.setBackgroundResource(drawable);
        }
    }

    public View getStatusBarBackgroundView() {
        return mStatusBarBackgroundView;
    }

    public void setStatusBarBackgroundView(View statusBarBackgroundView) {
        mStatusBarBackgroundView = statusBarBackgroundView;
    }

    // 获取手机状态栏高度
    public int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }


    public Context getContext() {
        return this;
    }

    @Override
    protected void onStop() {
//        unregisterReceiver(mNetStateReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MyActivityManager.getManager().finishActivity(this);
        hideLoading();
//        mLoadingDialog = null;
        super.onDestroy();
    }

    public void setEnableNetNotify(boolean enableNetNotify) {
        mEnableNetNotify = enableNetNotify;
    }

    /**
     * 网络状态监听器
     */
    private class NetStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (mEnableNetNotify && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                if (NetUtils.isNetworkAvailableNoBroadcast()) {
                    onNetReconnect();
                } else {
                    onNetDisconnect();
                }
            }
        }
    }

    /**
     * 断网
     */
    protected void onNetDisconnect() {
        if (mLoadingDialog != null) {
            hideLoading();
        }
        if (mPopupBannerView != null && !mPopupBannerView.isShowing()) {
            int h = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                h = getStatusBarHeight();
            int height = h + mTitleBar.getLayoutCenter().getBottom();
            mPopupBannerView.showAtLocation(mTitleBar, Gravity.TOP, 0, height);
        }
    }

    /**
     * 网络重连
     */
    protected void onNetReconnect() {
        mPopupBannerView.dismiss();
    }
}
