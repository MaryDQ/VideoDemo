package com.microsys.videodemo.widget;

import android.app.ProgressDialog;
import android.content.Context;

import com.microsys.videodemo.eventbus_events.DismissDialogEvent;

import org.greenrobot.eventbus.EventBus;

public class MyProgressDialog extends ProgressDialog {
    public MyProgressDialog(Context context) {
        super(context);
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new DismissDialogEvent(true,"取消加载框"));
        super.onBackPressed();
    }
}
