package com.niko.slib.manager;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.niko.slib.R;
import com.niko.slib.widgets.ListViewWithAlphabet;

/**
 * Created by niko on 16/5/27.
 */

public class ViewManager {
    private View mEmptyView, mErrorView;
    private ListView mListView;
    private ViewGroup mLayoutRoot;
    private View mRootView;
    private PullToRefreshListView mRefreshView;
    private ListViewWithAlphabet mListViewWithAlphabet;

    private int mDefaultHeaderViewCount = 1, mDefaultFooterViewCount = 1;

    public void initView(View view, int type) {
        mRootView = view;
        mLayoutRoot = mRootView.findViewById(R.id.layout_root);

        if (type == 1) {
            mRefreshView = (PullToRefreshListView) ((ViewStub) mLayoutRoot.findViewById(R.id.view_data)).inflate();
            mListView = mRefreshView.getRefreshableView();
        } else {
            mListViewWithAlphabet = (ListViewWithAlphabet) ((ViewStub) mLayoutRoot.findViewById(R.id.view_alpha_list)).inflate();
            mRefreshView = mListViewWithAlphabet.getRefreshView();
            mListView = mListViewWithAlphabet.getListView();
        }
        //初始化默认的header和footer的数量
        mDefaultHeaderViewCount = mListView.getHeaderViewsCount();
        mDefaultFooterViewCount = mListView.getFooterViewsCount();
    }

    public void initErrorView(@DrawableRes int resId, String text) {
        if (mErrorView == null) {
            mErrorView = ((ViewStub) mLayoutRoot.findViewById(R.id.view_error)).inflate();
            mErrorView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            TextView textView = mErrorView.findViewById(R.id.tv_error);
            textView.setText(text);
        }
        if (resId != 0) {
            ImageView textView = mErrorView.findViewById(R.id.iv_error);
            textView.setImageResource(resId);
        }
    }

    public void initEmptyView(@DrawableRes int resId, String text) {
        if (mEmptyView == null) {
            mEmptyView = ((ViewStub) mLayoutRoot.findViewById(R.id.view_empty)).inflate();
            mEmptyView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(text)) {
            TextView textView = mErrorView.findViewById(R.id.tv_empty);
            textView.setText(text);
        }
        if (resId != 0) {
            ImageView textView = mErrorView.findViewById(R.id.iv_empty);
            textView.setImageResource(resId);
        }
    }

    public View getErrorView() {
        return mErrorView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void stopRefresh() {
        getRefreshView().onRefreshComplete();
    }

    public View getRootView() {
        return mRootView;
    }

    public ViewGroup getLayoutRoot() {
        return mLayoutRoot;
    }

    public ListView getListView() {
        return mListView;
    }

    public PullToRefreshListView getRefreshView() {
        return mRefreshView;
    }

    public ListViewWithAlphabet getListViewWithAlphabet() {
        return mListViewWithAlphabet;
    }

    /**
     * 数据为空并且没有header以及footer的情况下才显示空view(自带的不算)
     */
    public void showEmptyView() {
        if (getListView().getHeaderViewsCount() == mDefaultHeaderViewCount
            //// TODO: 2016/6/17  处理footerView的情况
//                && getListView().getFooterViewsCount() == mDefaultFooterViewCount
                ) {
            if (mEmptyView == null) {
                mEmptyView = ((ViewStub) mLayoutRoot.findViewById(R.id.view_empty)).inflate();
            }
            mEmptyView.setVisibility(View.VISIBLE);
            mLayoutRoot.bringChildToFront(mEmptyView);
            if (getListView() != null) {
                getListView().setVisibility(View.INVISIBLE);
            }
            if (mErrorView != null) {
                mErrorView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 数据为空并且没有header以及footer的情况下才显示空view(自带的不算)
     */
    public void showListView() {
        getListView().setVisibility(View.VISIBLE);
        mLayoutRoot.bringChildToFront(getListView());
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.INVISIBLE);
        }
        if (mErrorView != null) {
            mErrorView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 失败时调用此方法更新界面
     */
    public void showErrorView() {
        if (getListView().getHeaderViewsCount() == mDefaultHeaderViewCount) {
            if (mErrorView == null) {
                mErrorView = ((ViewStub) mLayoutRoot.findViewById(R.id.view_error)).inflate();
            }
            mErrorView.setVisibility(View.VISIBLE);
            mLayoutRoot.bringChildToFront(mErrorView);
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.INVISIBLE);
            }
            if (getListView() != null) {
                getListView().setVisibility(View.INVISIBLE);
            }
        }
    }

    public void addHeaderView(View headerView) {
        if (headerView == null) {
            return;
        }
        getListView().addHeaderView(headerView);
    }
}
