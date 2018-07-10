package com.niko.slib.manager;


import com.niko.slib.fragment.SlibListFragment;

/**
 * Created by niko on 16/5/27.
 */

public class PageIndexManager {
    public static final int START_PAGE_INDEX = 1;//起始页码
    private int mPageIndex = START_PAGE_INDEX;//页码,从第一页开始
    private int mPageSize = 20;


    public int getPageIndex() {
        return mPageIndex;
    }

    public void setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }

    /**
     * @param mode 根据下拉刷新的模式自动管理页码
     */
    public void autoManagerIndexByMode(SlibListFragment.Mode mode) {
        switch (mode) {
            //下拉刷新,页码归为起始页码
            case PULL_FROM_START: {
                setPageIndex(START_PAGE_INDEX);
                break;
            }
            //上拉加载更多,页码自增
            case PULL_FROM_END: {
                setPageIndex(mPageIndex + 1);
                break;
            }
            default:
                break;
        }
    }
}
