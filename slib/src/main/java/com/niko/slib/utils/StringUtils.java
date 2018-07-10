package com.niko.slib.utils;

import android.text.TextUtils;

/**
 * Created by scanor on 2016/6/16.
 */
public class StringUtils {
    /**
     * @param strings 待选的字符串
     * @return 返回第一个不为空的字符串的下标
     */
    public static int getUnEmptyIndex(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            if (!TextUtils.isEmpty(strings[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param strings 待选的字符串
     * @return 返回第一个不为空的字符串
     */
    public static String getUnEmptyString(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            if (!TextUtils.isEmpty(strings[i])) {
                return strings[i];
            }
        }
        return "";
    }

    /**
     * @param strings 待选的字符串
     * @return 返回第一个为空的字符串的下标
     */
    public static int getEmptyIndex(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            if (TextUtils.isEmpty(strings[i])) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isMobile(String mobile) {
        return mobile.matches("(^(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[^346,\\D]\\d{7})$");
    }

    public static boolean isInteger(String str) {
        return TextUtils.isDigitsOnly(str);
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }
}
