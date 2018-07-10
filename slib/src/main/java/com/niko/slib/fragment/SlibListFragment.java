package com.niko.slib.fragment;

import android.databinding.ViewDataBinding;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.niko.slib.R;
import com.niko.slib.adapter.BaseListAdapter;
import com.niko.slib.databinding.ViewContentBinding;
import com.niko.slib.manager.DataManager;
import com.niko.slib.manager.PageIndexManager;
import com.niko.slib.manager.ViewManager;

import java.util.List;

/**
 * Created by niko on 16/2/13.
 * 这个是列表页面的基类,集合了以下功能
 * 1.能够自动管理页码
 * 2.下拉刷新,上拉加载更多
 * 3.自动显示空页面以及错误页
 * 4.如果你只想传入一个View而不用ListView，请重写initRootLayout方法
 */
public abstract class SlibListFragment<T, V extends ViewDataBinding> extends SlibFragment<T, V> {
    public enum Mode {
        PULL_FROM_START,
        PULL_FROM_END
    }
    private boolean mCurrentVisible;
    /**
     * 数据管理者
     */
    protected DataManager<T, V> mDataManager;
    /**
     * View管理者
     */
    protected ViewManager mViewManager = new ViewManager();
    /**
     * 页码管理者
     */
    protected PageIndexManager mPageIndexManager = new PageIndexManager();

    @Override
    public int initRootLayout() {
        return R.layout.view_content;
    }

    @Override
    public void initViewOnlyOnce(V binding) {

    }

    @Override
    public void initViewOnlyOnce(ViewContentBinding binding, boolean needInitListView) {
        if (needInitListView) {
            mViewManager.initView(binding.getRoot(), getType());
            mViewManager.getRefreshView().setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getData(Mode.PULL_FROM_START, PageIndexManager.START_PAGE_INDEX);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getData(Mode.PULL_FROM_END, mPageIndexManager.getPageIndex() + 1);
                }
            });
        }
    }

    public int getType() {
        return 1;//1为普通listView 2为字母表listview
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrentVisible = true;
    }

    @Override
    public void onPause() {
        mCurrentVisible = false;
        super.onPause();
    }

    public boolean isCurrentVisible() {
        return mCurrentVisible;
    }

    @Override
    public void beforeDataInitialized() {
        super.beforeDataInitialized();
        mDataManager = new DataManager<>(getActivity(), this);
        if (initRootLayout() == R.layout.view_content) {
            getListView().setAdapter(mDataManager.getAdapter());
        }
    }

    /**
     * 获取数据成功时调用此方法更新界面
     *
     * @param mode 下拉刷新还是上啦加载更多
     */
    public void showSuccessView(Mode mode) {
        //数据为空
        if (mDataManager.getAdapter().getCount() == 0) {
            mViewManager.showEmptyView();
        } else {

            mViewManager.showListView();
        }
        mViewManager.getRefreshView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewManager.getRefreshView().onRefreshComplete();
            }
        }, 500);
        mDataManager.getAdapter().notifyDataSetChanged();
        mPageIndexManager.autoManagerIndexByMode(mode);
    }

    public void setRefreshMode(PullToRefreshBase.Mode mode) {
        mViewManager.getRefreshView().setMode(mode);
    }

    public void showSuccessView() {
        showSuccessView(Mode.PULL_FROM_START);
    }

    public int getPageSize() {
        return mPageIndexManager.getPageSize();
    }

    public void setPageSize(int pageSize) {
        mPageIndexManager.setPageSize(pageSize);
    }

    public List<T> getDataList() {
        return getAdapter().getDataList();
    }

    /**
     * 失败时调用此方法更新界面
     */
    public void showErrorView() {
        mViewManager.getRefreshView().onRefreshComplete();
        mViewManager.showErrorView();
    }

    /**
     * 数据初始化完毕之后,自动获取一次数据,如果不需要自动获取一次,可以重写这个方法
     * 并且不要调用super.onDataInitialized();
     */
    @Override
    public void onDataInitialized() {
        super.onDataInitialized();
        getData(Mode.PULL_FROM_START, PageIndexManager.START_PAGE_INDEX);
    }

    /**
     * @param headerView 加了headerView之后需要setAdapter才能看得到
     */
    public void addHeaderView(View headerView) {
        mViewManager.addHeaderView(headerView);
    }

    /**
     * @return 返回ListView
     */
    public ListView getListView() {
        return mViewManager.getListView();
    }

    public BaseListAdapter<T, V> getAdapter() {
        return mDataManager.getAdapter();
    }
}
