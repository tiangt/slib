package com.niko.slib.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import com.niko.slib.SlibApp;


/**
 * Created by niko on 15/8/19.
 */
public class DimenUtils {

    public static int px2Dp(int px) {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        Log.d("DimenUtils-----", metric.density + "");
        return (int)(px / metric.density);
    }

    /**
     * @param dp dpi
     * @return 将dp转成px，一般用于view的大小的绘制的计算
     */
    public static int dp2Px(int dp) {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        Log.d("DimenUtils-----", metric.density + "");
        return (int)(dp * metric.density);
    }


    /**
     * @param context 传入context一般用于绘制view的时候调用，这样可以预览
     * @param dp dip
     * @return 将dp转成px，一般用于view的大小的绘制的计算
     */
    public static int dp2Px(Context context, int dp) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        Log.d("DimenUtils-----", metric.density + "");
        return (int)(dp * metric.density);
    }

    /**
     * @return 每英寸的像素点dots per inch，160就使用mdpi，240使用hdpi，320使用xhdpi，以此类推
     */
    public static int getDensityDpi() {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        return metric.densityDpi;
    }

    /**
     * @return 返回当前设备densityDpi 与 标准的densityDpi即DisplayMetrics.DENSITY_DEFAULT(160)的比值
     * 其中1表示使用mdpi里面的图，同时，1px = 1dp
     */
    public static float getDensity() {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        return metric.density;
    }

    /**
     * @return 返回屏幕宽度的物理尺寸，单位是英寸inch
     * 其中metric.widthPixels 为横向的像素总数，
     * xdpi 为The exact physical pixels per inch of the screen in the X dimension.
     */
    public static float getPhysicalWidth() {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        return metric.widthPixels / metric.xdpi;
    }

    /**
     * @return 返回屏幕高度的物理尺寸，单位是英寸inch
     * 其中metric.heightPixels 为纵向的像素总数，
     * ydpi 为The exact physical pixels per inch of the screen in the Y dimension.
     */
    public static float getPhysicalHeight() {
        DisplayMetrics metric = SlibApp.getInstance().getResources().getDisplayMetrics();
        return metric.heightPixels / metric.ydpi;
    }

    /**
     * @return 返回屏幕对角线的物理尺寸，单位是英寸inch
     */
    public static double getPhysicalSize() {
        float pw = getPhysicalWidth();
        float ph = getPhysicalHeight();
        return Math.sqrt(pw * pw + ph * ph);
    }
}
