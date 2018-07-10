package com.niko.slib.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.niko.slib.R;
import com.niko.slib.bean.LetterBean;
import com.niko.slib.bean.Sortable;
import com.niko.slib.fragment.SortResult;
import com.niko.slib.utils.PinYinUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by niko on 15/10/25.
 */
public class ListViewWithAlphabet extends RelativeLayout {

    public static final List<String> ALPHABET_LIST = Arrays.asList(AlphabetView.ALPHABETS);
    private TextView mTvLetter;
    private ListView mListView;
    private PullToRefreshListView mRefreshView;
    private AdapterView.OnItemClickListener mListener;
    private List<Sortable> mDataList = new ArrayList<Sortable>();
    private HashMap<String, Integer> mAlphaIndexMap = new HashMap<String, Integer>();

    public ListViewWithAlphabet(Context context) {
        super(context);
        init(context);
    }

    public ListViewWithAlphabet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public ListViewWithAlphabet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public <T extends Sortable> SortResult sortAndOrder(List<T> data, int headerViewCount) {
        SortResult sortResult = new SortResult();
        HashMap<String, Integer> alphaIndexMap = new HashMap<String, Integer>();
        alphaIndexMap.clear();
        //排序
        Collections.sort(data, new PinYinComparator());
        //分类
        int categorySize = 0;
        List<Sortable> dataList = new ArrayList<Sortable>();
        for (int i = 0; i < data.size(); i++) {
            Sortable bean = data.get(i);
            String key = bean.getKey();

            String category = getCategory(key);
            if (!alphaIndexMap.containsKey(category)) {
                LetterBean letterBean = new LetterBean();
                letterBean.setLetter(category);
                dataList.add(letterBean);
                alphaIndexMap.put(category, i + categorySize + headerViewCount);
                categorySize += 1;
            }
            dataList.add(bean);
        }
        sortResult.setAlphaIndexMap(alphaIndexMap);
        sortResult.setSortableList(dataList);
        return sortResult;
    }

    private static String getCategory(String key) {
        String category = PinYinUtils.getPinYin(key);
        if (TextUtils.isEmpty(category)) {
            category = "#";
        } else {
            category = String.valueOf(category.charAt(0));
            if (!ALPHABET_LIST.contains(category)) {
                category = "#";
            }
        }
        return category;
    }

    public HashMap<String, Integer> getAlphaIndexMap() {
        return mAlphaIndexMap;
    }

    public void setAlphaIndexMap(HashMap<String, Integer> alphaIndexMap) {
        mAlphaIndexMap = alphaIndexMap;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_view_with_alphabet, this);
        mTvLetter = this.findViewById(R.id.tv_letter);
        mRefreshView = this.findViewById(R.id.lv_city);
        mListView = mRefreshView.getRefreshableView();
        mRefreshView.setMode(PullToRefreshBase.Mode.DISABLED);
        AlphabetView alphabetView = this.findViewById(R.id.alphabet_view);
        alphabetView.setOnLetterTouchListener(new AlphabetView.OnLetterTouchListener() {
            @Override
            public void onLetterTouch(int index, String letter) {
                mTvLetter.setVisibility(View.VISIBLE);
                mTvLetter.setText(letter);

                if (letter.equals("↑")) {
                    mListView.setSelection(0);
                } else if (mAlphaIndexMap.containsKey(letter)) {
                    Integer letterIndex = mAlphaIndexMap.get(letter);
                    mListView.setSelection(letterIndex);
                }
            }

            @Override
            public void onTouchUp() {
                mTvLetter.setVisibility(View.GONE);
            }
        });
    }

    public void setSortResult(SortResult sortResult) {
        if (sortResult == null) {
            return;
        }
        mDataList.addAll(sortResult.getSortableList());
        mAlphaIndexMap = sortResult.getAlphaIndexMap();
    }

    public List<Sortable> getDataList() {
        return mDataList;
    }

    public void setDataList(List<Sortable> dataList) {
        mDataList = dataList;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
        mListView.setOnItemClickListener(mListener);
    }

    public ListView getListView() {
        return mListView;
    }

    public PullToRefreshListView getRefreshView() {
        return mRefreshView;
    }

    private static class PinYinComparator implements Comparator<Sortable> {

        @Override
        public int compare(Sortable sortable, Sortable t1) {
            String c1 = getCategory(sortable.getKey());
            String c2 = getCategory(t1.getKey());

            if (c1.equals("#") && !c2.equals("#")) {
                return Integer.MAX_VALUE;
            } else if (!c1.equals("#") && c2.equals("#")) {
                return Integer.MIN_VALUE;
            }
            return c1.compareTo(c2);
        }

        @Override
        public boolean equals(Object o) {
            return false;
        }
    }
}
