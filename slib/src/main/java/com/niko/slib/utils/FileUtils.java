package com.niko.slib.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by niko on 16/1/13.
 */
public class FileUtils {
    public static String getAppImageDirectory(String appName) {
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+ File.separator +appName;
        File file = new File(directory);
        if(!file.exists()) {
            file.mkdir();
        }
        if (TextUtils.isEmpty(directory)) {
            throw new RuntimeException("Image directory create fail, please make sure you have enough space for allocation");
        }
        return directory;
    }

    public static String getDefaultImageName(String appName, String fileName){
        return getAppImageDirectory(appName) + File.separator + fileName;
    }
}
