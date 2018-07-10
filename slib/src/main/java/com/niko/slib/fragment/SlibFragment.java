package com.niko.slib.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niko.slib.R;
import com.niko.slib.SlibApp;
import com.niko.slib.adapter.DataProvider;
import com.niko.slib.databinding.ViewContentBinding;


/**
 * Created by scanor on 15/10/14.
 */
public abstract class SlibFragment<T, V extends ViewDataBinding> extends Fragment implements DataProvider<T, V> {
    protected final String TAG = this.getClass().getName();
    private V mBinding;//缓存Fragment view
    private boolean mFirstInitData = true;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null && initRootLayout() != 0) {
            mRootView = inflater.inflate(initRootLayout(), container, false);
            if (mRootView != null) {
                if (initRootLayout() != R.layout.view_content) {
                    mBinding = DataBindingUtil.bind(mRootView);
                    initViewOnlyOnce(mBinding);
                } else {
                    ViewContentBinding binding = DataBindingUtil.bind(mRootView);
                    initViewOnlyOnce(binding, true);
                }
            }
        }
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.empty_view, container, false);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mFirstInitData) {
            mFirstInitData = false;
            beforeDataInitialized();
            initDataOnlyOnce();
            onDataInitialized();
        }
    }

    public V getBinding() {
        return mBinding;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public abstract void initViewOnlyOnce(V binding);

    public abstract void initViewOnlyOnce(ViewContentBinding binding, boolean isRoot);

    public abstract void initDataOnlyOnce();
    //初始化完毕的回调
    public void onDataInitialized() {}

    /**
     * 初始化开始前调用,一般用于系统自带的初始化,例如初始化调用onCreateMenu创建TitleBar菜单
     * 同时不影响initDataOnlyOnce子类必须实现的属性
     */
    public void beforeDataInitialized() {}
}
