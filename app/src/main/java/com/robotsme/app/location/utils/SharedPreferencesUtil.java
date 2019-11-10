package com.robotsme.app.location.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    private Context context;
    private SharedPreferences preferences;

    SharedPreferencesUtil(Context context) {
        this.context = context;
    }
    public void getSharedPreference(String key) {
        preferences = context.getSharedPreferences(key, 0);
    }
}
