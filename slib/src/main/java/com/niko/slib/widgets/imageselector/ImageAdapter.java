package com.niko.slib.widgets.imageselector;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.niko.slib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niko on 15/9/16.
 */
public class ImageAdapter extends BaseAdapter {

    protected Context mContext;
    protected boolean mDeletable = true;
    protected boolean mIsDeleteMode = false;
    private List<String> mDataList;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemDeleteListener  mOnItemDeleteListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private OnItemOperate mOnItemOperate;
    private ImageSelector mImageSelector;

    public ImageAdapter(Context context){
        this.mContext =context;
        mOnItemClickListener = new OnItemClickListener();
        mOnItemDeleteListener = new OnItemDeleteListener();
        mOnItemLongClickListener = new OnItemLongClickListener();
        this.mDataList = new ArrayList<String>();
    }

    public void setImageSelector(ImageSelector imageSelector) {
        mImageSelector = imageSelector;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public String getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.widget_gallery_item, parent);
        }
        ImageView imageView = convertView.findViewById(R.id.iv_image);
        ImageView ivDelete = convertView.findViewById(R.id.iv_delete);
        String url = getItem(position); //获取当前的Item
        if (mOnItemOperate != null && !TextUtils.isEmpty(url)) {
            /**
             * 需要在实现接口的地方使用自己的图片加载库加载url
             * */
            mOnItemOperate.onItemDisplay(url, imageView);
        }

        if (mDeletable && mIsDeleteMode) {
            ivDelete.setTag(position);
            ivDelete.setVisibility(View.VISIBLE);
            ivDelete.setOnClickListener(mOnItemDeleteListener);
        } else {
            ivDelete.setVisibility(View.GONE);
        }
        convertView.setTag(position);
        convertView.setOnClickListener(mOnItemClickListener);
        convertView.setOnLongClickListener(mOnItemLongClickListener);
        return convertView;
    }

    public void setOnItemOperate(OnItemOperate onItemOperate) {
        mOnItemOperate = onItemOperate;
    }

    public void setDeletable(boolean deletable) {
        mDeletable = deletable;
    }

    public List<String> getDataList() {
        return mDataList;
    }

    private class OnItemDeleteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!(v.getTag() instanceof Integer)) {
                return;
            }
            int pos = (Integer)v.getTag();
            mDataList.remove(pos);
            if (mImageSelector != null) {
                mImageSelector.getFileList().remove(pos);
            }
            if (mOnItemOperate != null) {
                mOnItemOperate.onItemDelete(v, pos);
            }
            mIsDeleteMode = false;
            notifyDataSetChanged();
        }
    }

    private class OnItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!(v.getTag() instanceof Integer)) {
                return;
            }
            int pos = (Integer)v.getTag();
            if (mOnItemOperate != null) {
                mOnItemOperate.onItemClick(v, pos);
            }
        }
    }

    private class OnItemLongClickListener implements View.OnLongClickListener {

        @Override
        public boolean onLongClick(View v) {
            mIsDeleteMode = !mIsDeleteMode;
            if (mOnItemOperate != null) {
                mOnItemOperate.onItemLongClick(v, (Integer)v.getTag());
            }
            notifyDataSetChanged();
            return true;
        }
    }

    public interface OnItemOperate {
        /**
         * @param url url;
         * @param view view;
         * 需要在实现接口的地方使用自己的图片加载库加载url,url还包括本地路径
         * */
        void onItemDisplay(String url, ImageView view);
        //点击某张图片的回调
        void onItemClick(View view, int position);
        //删除某张图片的回调
        void onItemDelete(View view, int position);
        //长按某张图片的回调
        void onItemLongClick(View view, int position);
    }
}