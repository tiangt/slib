package com.niko.slib.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by niko on 15/10/15.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    private List<T> mDataList;
    protected Context mContext;

    public BasicAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }
    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        if (mDataList == null) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDataList() {
        return mDataList;
    }
}
