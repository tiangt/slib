package com.niko.slib.manager;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.niko.slib.adapter.BaseListAdapter;
import com.niko.slib.adapter.DataProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niko on 16/5/28.
 * @param <T> 单项的数据类型
 * @param <V> ViewDataBinding
 */

public class DataManager<T, V extends ViewDataBinding> {
    private List<T> mDataList = new ArrayList<T>();
    private DataProvider<T,V> mDataProvider;
    private BaseListAdapter<T, V> mBasicAdapter;
    public DataManager(Context context, DataProvider<T, V> provider) {
        mBasicAdapter = new BaseListAdapter<>(context, mDataList);
        mDataProvider = provider;
        mBasicAdapter.setDataProvider(mDataProvider);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }

    public BaseListAdapter<T, V> getAdapter() {
        return mBasicAdapter;
    }

    public DataProvider getDataProvider() {
        return mDataProvider;
    }

}
