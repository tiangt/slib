package com.niko.slib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.niko.slib.SlibApp;


/**
 * Created by scanor on 15/10/2.
 */
public class NetUtils {

    public static boolean isNetworkAvailable() {
        Context context = SlibApp.getInstance().getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo aNetworkInfo : networkInfo)
                    if (aNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;  // 存在可用的网络连接
            }
        }
        return false;
    }

    public static boolean isNetworkAvailableNoBroadcast() {
        Context context = SlibApp.getInstance().getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo aNetworkInfo : networkInfo)
                    if (aNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
                        return true;  // 存在可用的网络连接
            }
        }
        return false;
    }

    public static boolean isWifiNetwork() {
        ConnectivityManager manager = (ConnectivityManager)
                SlibApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFastMobileNetwork() {
        TelephonyManager telephonyManager = (TelephonyManager)
                SlibApp.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }
}
