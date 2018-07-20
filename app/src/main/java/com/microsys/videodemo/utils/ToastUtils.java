package com.microsys.videodemo.utils;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.microsys.videodemo.app.MyApplication;

/**
 * ============================
 * 作    者：mlx
 * 创建日期：2018/1/3.
 * 描    述：Toast工具类
 * 修改历史：
 * ===========================
 */

public class ToastUtils {
    private static Toast mToast;

    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(int resId) { //R.string.**
        getSingleToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
    }

    public static void showSingleLongToast(int resId) {
        getSingleToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showSingleLongToast(String text) {
        getSingleToast(text, Toast.LENGTH_LONG).show();
    }

    /*********************** 连续弹出的Toast ************************/
    public static void showToast(int resId) {
        getToast(resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String text) {
        getToast(text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(int resId) {
        getToast(resId, Toast.LENGTH_LONG).show();
    }

    public static void showLongToast(String text) {
        getToast(text, Toast.LENGTH_LONG).show();
    }

    private static Toast getSingleToast(int resId, int duration) { // 连续调用不会连续弹出，只是替换文本
        return getSingleToast(MyApplication.getInstance().getResources().getText(resId).toString(), duration);
    }

    @SuppressLint("ShowToast")
    private static Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getInstance(), text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    private static Toast getToast(int resId, int duration) { // 连续调用会连续弹出
        return getToast(MyApplication.getInstance().getResources().getText(resId).toString(), duration);
    }

    private static Toast getToast(String text, int duration) {
        return Toast.makeText(MyApplication.getInstance(), text, duration);
    }
}
