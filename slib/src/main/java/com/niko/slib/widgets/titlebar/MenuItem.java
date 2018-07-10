package com.niko.slib.widgets.titlebar;

import android.os.Bundle;
import android.support.annotation.DrawableRes;

/**
 * Created by niko on 16/6/3.
 */

public class MenuItem {
    public static final String ARGUMENT = "argument";//右上角加号的id
    public static final int EXPAND_MENU_ID = 1001;//右上角加号的id
    private int mId;
    private boolean mFold;//是否折叠,默认不折叠
    private @DrawableRes
    int mIcon;
    private String mIconUrl;
    private String mName;
    private Class mCls;
    private Bundle mBundle;
    private boolean mHide;//是否隐藏

    public boolean isFold() {
        return mFold;
    }

    public void setFold(boolean fold) {
        mFold = fold;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(@DrawableRes int icon) {
        mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Class getCls() {
        return mCls;
    }

    public void setCls(Class cls) {
        mCls = cls;
    }

    public boolean isHide() {
        return mHide;
    }

    public void setHide(boolean hide) {
        mHide = hide;
    }

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
    }
}
