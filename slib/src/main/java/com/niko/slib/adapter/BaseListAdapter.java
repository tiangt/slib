package com.niko.slib.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BaseListAdapter<T, V extends ViewDataBinding> extends BasicAdapter<T> {
    private DataProvider<T, V> mDataProvider;

    public void setDataProvider(DataProvider<T, V> dataProvider) {
        mDataProvider = dataProvider;
    }

    public BaseListAdapter(Context context, List<T> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        V binding = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mDataProvider.initItemLayout(), null, false);
        }
        if (convertView != null) {
            binding = DataBindingUtil.bind(convertView);
        }
        mDataProvider.bindData(binding, getItem(position), position);
        return binding == null? null:binding.getRoot();
    }
}