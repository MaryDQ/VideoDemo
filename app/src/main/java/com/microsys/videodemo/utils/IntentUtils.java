package com.microsys.videodemo.utils;

import android.content.Context;
import android.content.Intent;

public class IntentUtils {
    public static void jumpToTargetClass(Context context,Class tagetClass){
        Intent intent=new Intent(context,tagetClass);
        context.startActivity(intent);
    }
}
