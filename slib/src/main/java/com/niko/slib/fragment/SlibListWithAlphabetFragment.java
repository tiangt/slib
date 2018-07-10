package com.niko.slib.fragment;

import android.databinding.ViewDataBinding;
import android.widget.ListView;

import com.niko.slib.bean.Sortable;

import java.util.List;

/**
 * Created by niko on 16/2/13.
 */
public abstract class SlibListWithAlphabetFragment<E, V extends ViewDataBinding> extends SlibListFragment<E, V> {

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void beforeDataInitialized() {
        super.beforeDataInitialized();
        if (getType() == 2) {
            mViewManager.getListViewWithAlphabet().setDataList((List<Sortable>) getDataList());
        }
    }

    public <T extends Sortable> SortResult sortAndOrder(List<T> data) {
        int headerViewCount = mViewManager.getListViewWithAlphabet().getListView().getHeaderViewsCount();
        return mViewManager.getListViewWithAlphabet().sortAndOrder(data, headerViewCount);
    }

    public void setSortResult(SortResult sortResult) {
        mViewManager.getListViewWithAlphabet().setSortResult(sortResult);
    }

    public ListView getListView() {
        if (getType() == 2) {
            return mViewManager.getListViewWithAlphabet().getListView();
        } else {
            return super.getListView();
        }
    }

    public void clear() {
        getDataList().clear();
        mViewManager.getListViewWithAlphabet().getAlphaIndexMap().clear();
    }
}
