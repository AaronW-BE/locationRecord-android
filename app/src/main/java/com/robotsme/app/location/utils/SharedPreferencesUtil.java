package com.robotsme.app.location.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static volatile SharedPreferencesUtil instance;
    private Context context;
    private SharedPreferences preferences;

    private SharedPreferencesUtil(Context context) {
        this.context = context;
    }

    public static SharedPreferencesUtil getInstance(Context mContext) {
        SharedPreferencesUtil spu = instance;
        if (spu == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (spu == null) {
                    spu = new SharedPreferencesUtil(mContext);
                    instance = spu;
                }
            }
        }
        return spu;
    }

    public void getSharedPreference(String key) {
        preferences = context.getSharedPreferences(key, 0);
    }
}
