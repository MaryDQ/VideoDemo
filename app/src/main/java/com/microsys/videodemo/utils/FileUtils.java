package com.microsys.videodemo.utils;

import android.os.Environment;

import java.io.File;

public class FileUtils {
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String SD_SHOT_PATH = SD_PATH + File.separator + "shot" + File.separator;

    public static String getShotPath() {
        String path = SD_SHOT_PATH;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
