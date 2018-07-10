package com.niko.slib.widgets.titlebar;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by niko on 16/6/3.
 */

public class Menu extends Observable {
    private List<MenuItem> mFoldList = new ArrayList<MenuItem>();//折叠的菜单
    private List<MenuItem> mExpandList = new ArrayList<MenuItem>();//展开的菜单

    public List<MenuItem> getFoldList() {
        return mFoldList;
    }

    public List<MenuItem> getExpandList() {
        return mExpandList;
    }

    /**
     * @param menuItem 添加右侧菜单
     */
    public void addMenuItem(MenuItem menuItem) {
        if (menuItem.isFold()) {
            mFoldList.add(menuItem);
        } else {
            mExpandList.add(menuItem);
        }
        notifyMenuChanged();
    }

    /**
     * @param menuItem  移除右侧菜单
     */
    public void deleteMenu(MenuItem menuItem) {
        if (menuItem.isFold()) {
            for (MenuItem item :
                    mFoldList) {
                if (item.getId() == menuItem.getId()) {
                    mFoldList.remove(item);
                    break;
                }
            }
        } else {
            for (MenuItem item :
                    mExpandList) {
                if (item.getId() == menuItem.getId()) {
                    mExpandList.remove(item);
                    break;
                }
            }
        }
        notifyMenuChanged();
    }

    public void notifyMenuChanged() {
        setChanged();
        notifyObservers();
    }

    /**
     * 隐藏右侧菜单栏
     */
    public void hideMenu() {
        for (MenuItem item : mExpandList) {
            item.setHide(true);
        }
        notifyMenuChanged();
    }

    /**
     * 显示右侧菜单栏
     */
    public void showMenu() {
        for (MenuItem item : mExpandList) {
            item.setHide(false);
        }
        notifyMenuChanged();
    }
}
