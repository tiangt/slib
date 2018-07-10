package com.niko.slib.utils;

import android.widget.Toast;

import com.niko.slib.SlibApp;


/**
 * Created by scanor on 15/10/2.
 */
public class ToastUtils {
    private static Toast mToast;
    public static void toast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(SlibApp.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
        }
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void toastLong(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(SlibApp.getInstance(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
        }
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }
}
