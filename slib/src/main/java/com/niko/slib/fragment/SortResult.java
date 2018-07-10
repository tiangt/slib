package com.niko.slib.fragment;


import com.niko.slib.bean.Sortable;

import java.util.HashMap;
import java.util.List;

public class SortResult {
    private List<Sortable> mSortableList;
    private HashMap<String, Integer> mAlphaIndexMap;

    public List<Sortable> getSortableList() {
        return mSortableList;
    }

    public void setSortableList(List<Sortable> sortableList) {
        mSortableList = sortableList;
    }

    public HashMap<String, Integer> getAlphaIndexMap() {
        return mAlphaIndexMap;
    }

    public void setAlphaIndexMap(HashMap<String, Integer> alphaIndexMap) {
        mAlphaIndexMap = alphaIndexMap;
    }
}