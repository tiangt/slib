package com.niko.slib.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by niko on 15/10/16.
 */
public class TimeUtils {
    public static SimpleDateFormat HMS = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
    public static SimpleDateFormat YMD = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    public static SimpleDateFormat YMD_HMS  =  new  SimpleDateFormat( "yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    public static Calendar parseDate(String dateString) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = YMD.parse(dateString);
            calendar.setTime(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar parseTime(String timeString) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = HMS.parse(timeString);
            calendar.setTime(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar parseDateAndTime(String dateString, String timeString) {
        if (TextUtils.isEmpty(dateString)) {
            return parseTime(timeString);
        } else if (TextUtils.isEmpty(timeString)) {
            return parseDate(dateString);
        }
        String dateAndTimeString = dateString + " " + timeString;
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = YMD_HMS.parse(dateAndTimeString);
            calendar.setTime(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar parseDateAndTime(String dateAndTimeString) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = YMD_HMS.parse(dateAndTimeString);
            calendar.setTime(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar parseFuzzy(String dateAndTimeString) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = null;
            if (dateAndTimeString.length() <= 10) {
                date = YMD.parse(dateAndTimeString);
            } else if (dateAndTimeString.length() >= 19){
                date = YMD_HMS.parse(dateAndTimeString);
            }
            calendar.setTime(date);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar getCalendarFromDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    public static Calendar getCalendarFromTime(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    public static String getFormatTime(Calendar calendar) {
        return HMS.format(calendar.getTime());
    }

    public static String getFormatDateAndTime(Calendar calendar) {
        String dateAndTime = getFormatDate(calendar) + " " + getFormatTime(calendar);
        return dateAndTime;
    }

    public static String getFormatDate(Calendar calendar) {
        return YMD.format(calendar.getTime());
    }

    public static String getFormatDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return YMD.format(calendar.getTime());
    }

    public static String getFormatTime(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return HMS.format(calendar.getTime());
    }
}
