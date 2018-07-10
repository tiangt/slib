package com.niko.slib.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by niko on 15/10/10.
 */
public class LogUtils {
    /**
     * @param text 要打印的字符串
     */
    public static void d(String text) {
        if (!TextUtils.isEmpty(text)) {
            int logcatMaxLength = 1000;
            int lineCount = text.length() / logcatMaxLength;
            if (text.length() % logcatMaxLength > 0) {
                lineCount += 1;
            }
            if (lineCount > 1) {
                Log.d("<<<<<<<<", "总共" + lineCount + "行开始打印");
            }
            for (int i = 0; i < lineCount; i++) {
                String subString = text.substring(i * logcatMaxLength, Math.min((i + 1) * logcatMaxLength, text.length()));
//                Log.d("<<<<<<<<", "当前第" + (i+1) + "行：" + subString);
                Log.d("<<<<<<<<", subString);
            }
            if (lineCount > 1) {
                Log.d("<<<<<<<<", "总共" + lineCount + "行结束打印");
            }
        }
    }

    /**
     * @param text 要打印的字符串
     */
    public static void e(String text) {
        if (!TextUtils.isEmpty(text)) {
            int logcatMaxLength = 1000;
            int lineCount = text.length() / logcatMaxLength;
            if (text.length() % logcatMaxLength > 0) {
                lineCount += 1;
            }
            if (lineCount > 1) {
                Log.e("<<<<<<<<", "总共" + lineCount + "行开始打印");
            }
            for (int i = 0; i < lineCount; i++) {
                String subString = text.substring(i * logcatMaxLength, Math.min((i + 1) * logcatMaxLength, text.length()));
//                Log.d("<<<<<<<<", "当前第" + (i+1) + "行：" + subString);
                Log.e("<<<<<<<<", subString);
            }
            if (lineCount > 1) {
                Log.e("<<<<<<<<", "总共" + lineCount + "行结束打印");
            }
        }
    }
}
