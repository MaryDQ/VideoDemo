package com.microsys.videodemo.common;

import android.util.SparseArray;

import com.microsys.videodemo.api.Api;
import com.microsys.videodemo.ui.bean.TestBean;

import java.util.ArrayList;

public class Constants {
    public static final String VIDEO_BACKGROUND_URL = Api.BASE_URL_WITHOUT_PORT + "8080/MediaServer/pic/";
    public static SparseArray<ArrayList<TestBean>> categoryList = new SparseArray<>();
    public static volatile boolean isUserCancelDialog = false;
    public static volatile int CUR_FRAGMENT_ITEM=0;
}
