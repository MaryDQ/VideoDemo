package com.microsys.videodemo.utils;

import android.os.Handler;

public class JniUtils {
    private static volatile JniUtils instance;

    public static JniUtils getInstance() {
        if (null == instance) {
            synchronized (JniUtils.class) {
                if (null == instance) {
                    instance = new JniUtils();
                }
            }

        }
        return instance;
    }

    public Handler timerOverTimeHandler = new Handler();
}
