package com.niko.slib.adapter;

import android.databinding.ViewDataBinding;

import com.niko.slib.fragment.SlibListFragment;


/**
 * @param <T> 单项数据结构
 * @param <D> ViewDataBinding
 */
public interface DataProvider<T, D extends ViewDataBinding> {
    //为ListView的单项赋值
    int initItemLayout();

    /**
     * @return 设定Fragment的跟布局
     */
    int initRootLayout();

    //获取数据
    void getData(SlibListFragment.Mode mode, int pageIndex);

    void bindData(D binding, T object, int position);
}