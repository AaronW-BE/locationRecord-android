package com.robotsme.app.location.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {
    public static void show(Context context, String string) {
        try {
            Toast.makeText(context, string, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(context, string, Toast.LENGTH_LONG).show();
            Looper.loop();}
    }
}
