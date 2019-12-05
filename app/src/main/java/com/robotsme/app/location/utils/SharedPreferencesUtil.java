package com.robotsme.app.location.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private static volatile SharedPreferencesUtil instance;
    private Context context;
    private SharedPreferences sp;

    private SharedPreferencesUtil(Context context) {
        this.context = context;
        sp = context.getSharedPreferences("location_record_config", Context.MODE_PRIVATE);
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


    public void putToken(String token) {
        sp.edit().putString("token", token).apply();
    }
    public String getToken() {
        return sp.getString("token", "");
    }
}
