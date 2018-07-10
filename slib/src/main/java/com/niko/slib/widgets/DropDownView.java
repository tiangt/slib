package com.niko.slib.widgets;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.niko.slib.R;


/**
 * Created by niko on 15/8/24.
 */
public class DropDownView extends PopupWindow implements View.OnClickListener{
    private LinearLayout mRootView;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private int mViewIndex;

    public DropDownView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
        , LinearLayout.LayoutParams.MATCH_PARENT);
        mRootView = new LinearLayout(context);
        mRootView.setLayoutParams(lp);
        mRootView.setOrientation(LinearLayout.VERTICAL);
        mRootView.setBackgroundColor(context.getResources().getColor(R.color.white));
        this.setContentView(mRootView);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bg_white));
    }

    public DropDownView addItem(String text) {
        return addItem(text, false);
    }

    public DropDownView addItem(String text, boolean showRedDot) {
        if (mViewIndex > 0) {
            addDividerLine();
        }
        View itemView = mLayoutInflater.inflate(R.layout.widget_drop_down_item, null);
        itemView.setTag(mViewIndex);
        itemView.setOnClickListener(this);
        TextView textView = itemView.findViewById(R.id.tv_text);
        ImageView imageView = itemView.findViewById(R.id.iv_red_dot);
        textView.setText(text);
        if (showRedDot) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRootView.addView(itemView, layoutParams);
        mViewIndex += 1;
        return this;
    }

    private void addDividerLine() {
        View dividerLine = new View(mRootView.getContext());
        dividerLine.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        mRootView.addView(dividerLine, layoutParams);
    }

    public void showRedDot(int position) {
        View view = mRootView.findViewWithTag(position);
        if (view != null) {
            ImageView imageView = view.findViewById(R.id.iv_red_dot);
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(View.VISIBLE);
        }
    }

    public void hideRedDot(int position) {
        View view = mRootView.findViewWithTag(position);
        if (view != null) {
            ImageView imageView = view.findViewById(R.id.iv_red_dot);
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(View.GONE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener == null) {
            return;
        }
        mOnItemClickListener.onItemClick((Integer)v.getTag(), v);
        this.dismiss();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View itemRootView);
    }
}

