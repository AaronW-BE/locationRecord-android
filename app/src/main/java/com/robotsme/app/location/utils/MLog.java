package com.robotsme.app.location.utils;

import android.text.TextUtils;
import android.util.Log;

public class MLog {

    public static final boolean IS_DEBUG = true;
    public static final String TAG = "LocationRecord";

    public static void i (String ...msg) {
        if (IS_DEBUG) {
            String log = TextUtils.join(",", msg);
            Log.i(TAG, log);
        }
    }

    public static void e (String ...msg) {
        if (IS_DEBUG) {
            String log = TextUtils.join(",", msg);
            Log.e(TAG, log);
        }
    }
}
