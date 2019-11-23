package com.robotsme.app.location.utils;

import android.util.Log;

public class MLog {

    public static final boolean IS_DEBUG = true;
    public static final String TAG = "LocationRecord";

    public static void i (String msg) {
        if (IS_DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void e (String msg) {
        if (IS_DEBUG) {
            Log.e(TAG, msg);
        }
    }
}
