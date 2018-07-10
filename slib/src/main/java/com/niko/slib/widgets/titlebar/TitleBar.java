package com.niko.slib.widgets.titlebar;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.niko.slib.R;
import com.niko.slib.databinding.LayoutTitleBarBinding;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by niko on 16/2/23.
 */
public class TitleBar extends LinearLayout implements Observer{
    private LayoutTitleBarBinding mTitleBarBinding;
    private OnTitleBarClickListener mOnTitleBarClickListener;
    private Menu mMenu = new Menu();

    public TitleBar(Context context) {
        super(context);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public EditText getEtCenter() {
        return mTitleBarBinding.etCenter;
    }

    private void init() {
        mTitleBarBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_title_bar, this, true);

        mTitleBarBinding.layoutLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleBarClickListener != null) {
                    mOnTitleBarClickListener.onLeftClick(v);
                }
            }
        });
//        setLeftIcon(R.drawable.common_ic_back);

        mTitleBarBinding.tvCenter.setText(((Activity) getContext()).getTitle());
        mTitleBarBinding.tvCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTitleBarClickListener != null) {
                    mOnTitleBarClickListener.onCenterClick(v);
                }
            }
        });
        //将TitleBar设置为菜单的观察者,观察菜单的变化,新增或者移除菜单,都会通知该TitleBar
        mMenu.addObserver(this);
    }

    @Override
    public void setBackgroundResource(int resid) {
        mTitleBarBinding.titleBar.setBackgroundResource(resid);
    }

    public void setCenterView(View customView) {
        hideAllCenterView();
        mTitleBarBinding.layoutCenter.addView(customView);
    }

    public void setCenterView(int layoutId) {
       hideAllCenterView();
        View view = LayoutInflater.from(getContext()).inflate(layoutId, mTitleBarBinding.layoutCenter, false);
        mTitleBarBinding.layoutCenter.addView(view);
    }

    public void hideAllCenterView() {
        for (int i = 0; i < mTitleBarBinding.layoutCenter.getChildCount(); i++) {
            mTitleBarBinding.layoutCenter.getChildAt(i).setVisibility(GONE);
        }
    }

    public Menu getMenu() {
        return mMenu;
    }

    public void setLeftEnable(boolean show) {
        if (show) {
            mTitleBarBinding.layoutLeft.setVisibility(VISIBLE);
        } else {
            mTitleBarBinding.layoutLeft.setVisibility(GONE);
        }
    }

    public void setLeftText(String text) {
        mTitleBarBinding.tvLeft.setVisibility(VISIBLE);
        mTitleBarBinding.tvLeft.setText(text);
    }

    public void setRightEnable(boolean show) {
        if (show) {
            mTitleBarBinding.layoutRight.setVisibility(VISIBLE);
        } else {
            mTitleBarBinding.layoutRight.setVisibility(GONE);
        }
    }

    public RelativeLayout getLayoutCenter() {
        return mTitleBarBinding.layoutCenter;
    }

    public void setLeftIcon(@DrawableRes int drawableId) {
        mTitleBarBinding.ivLeft.setImageResource(drawableId);
    }

    @Override
    public void setBackgroundColor(int color) {
        mTitleBarBinding.getRoot().setBackgroundColor(color);
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        mOnTitleBarClickListener = onTitleBarClickListener;
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return mOnTitleBarClickListener;
    }

    public void setTitle(String title) {
        hideAllCenterView();
        TextView titleView = mTitleBarBinding.tvCenter;
        if (titleView.getVisibility() == GONE) {
            titleView.setVisibility(VISIBLE);
        }
        titleView.setText(title);
    }

    private TextView mTitleView = null;
    public void setTitle(int titleResId) {
        hideAllCenterView();
        if (mTitleView == null){
            mTitleView = mTitleBarBinding.tvCenter;
        }
        if (mTitleView.getVisibility() == GONE) {
            mTitleView.setVisibility(VISIBLE);
        }
        mTitleView.setText(titleResId);
    }

    public TextView getTitleView(){
        if (mTitleView == null){
            mTitleView = mTitleBarBinding.tvCenter;
        }
        return mTitleView;
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable == null) {
            return;
        }
        Menu menu = (Menu)observable;
        mTitleBarBinding.layoutRight.removeAllViews();
        for (int i = 0; i < menu.getExpandList().size(); i++) {
            MenuItem item = menu.getExpandList().get(i);
            addExpandMenu(item);
        }
//        if (menu.getFoldList().size() > 0) {
//            MenuItem item = new MenuItem();
//            item.setId(MenuItem.EXPAND_MENU_ID);
//            item.setFold(false);
//            item.setIcon(加号);
//        }
    }

    private void addExpandMenu(final MenuItem item) {
        final View itemView = LayoutInflater.from(getContext()).inflate(R.layout.menu_item_horizontal, mTitleBarBinding.layoutRight, false);
        ImageView imageView = itemView.findViewById(R.id.iv_menu_icon);
        TextView textView = itemView.findViewById(R.id.tv_menu_name);
        imageView.setImageResource(item.getIcon());
        if (item.getIcon() != 0) {
            textView.setVisibility(VISIBLE);
            textView.setText(item.getIcon());
        } else {
            textView.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(item.getName())) {
            textView.setVisibility(VISIBLE);
            textView.setText(item.getName());
        } else {
            textView.setVisibility(GONE);
        }
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)itemView.getLayoutParams();
//        params.setMargins(0, 0, DimenUtils.dp2Px(10), 0);
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnTitleBarClickListener.onRightClick(itemView, item);
            }
        });
        if (item.isHide()) {
            itemView.setVisibility(GONE);
        } else {
            itemView.setVisibility(VISIBLE);
        }
        mTitleBarBinding.layoutRight.addView(itemView);
    }

    public interface OnTitleBarClickListener {
        void onLeftClick(View view);
        void onCenterClick(View view);
        void onRightClick(View view, MenuItem item);
    }
}
