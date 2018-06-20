package org.samtech.earthquaketest.utils;

import android.content.Context;
import android.widget.Toast;

public class Messages {

    public static void showMsg(Context context, String msg, boolean isLong){
        Toast.makeText(context, msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }
}
