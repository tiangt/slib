package com.niko.slib;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by niko on 16/6/1.
 */

public class MyActivityManager {
    private static MyActivityManager mManager = new MyActivityManager();
    private Stack<Activity> mActivityStack = new Stack<Activity>();

    private MyActivityManager() {

    }

    public static MyActivityManager getManager() {
        return mManager;
    }

    public void push(Activity activity) {
        if (activity == null) {
            return;
        }
        mActivityStack.push(activity);
    }

    /**
     * @return 返回栈顶的Activity
     */
    public Activity pop() {
        if(mActivityStack.isEmpty()) {
            return null;
        }
        return mActivityStack.pop();
    }

    /**
     * @return 返回栈顶的Activity
     */
    public Activity getTop() {
        if(mActivityStack.isEmpty()) {
            return null;
        }
        return mActivityStack.lastElement();
    }

    /**
     * @param activity 移除栈中第一个与activity同名的
     */
    public void finishActivity(Activity activity) {
        Iterator<Activity> it = mActivityStack.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act == activity) {
                it.remove();
                act.finish();
            }
        }
    }

    /**
     * @param clazz 移除所有这一类的activity
     */
    public void finishAll(Class clazz) {
        Iterator<Activity> it = mActivityStack.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass().getName().equals(clazz.getName())) {
                it.remove();
                act.finish();
            }
        }
    }

    /**
     * 移除所有activity
     */
    public void finishAll() {
        Iterator<Activity> it = mActivityStack.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            it.remove();
            act.finish();
        }
        mActivityStack.clear();
    }
}
