package com.niko.slib;

import android.app.Application;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;


/**
 * Created by niko on 15/10/1.
 */
public class SlibApp extends Application {

    public static final String TAG = SlibApp.class.getSimpleName();
    private LocalBroadcastManager mBroadcastManager;

    private static SlibApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    public static synchronized SlibApp getInstance() {
        if (mInstance == null) {
            throw new RuntimeException("请先定义自己的Application类让其继承com.scanor.slib.SlibApp,并在AndroidManifest文件中注册");
        }
        return mInstance;
    }


    public LocalBroadcastManager getBroadcastManager() {
        return mBroadcastManager;
    }


}
