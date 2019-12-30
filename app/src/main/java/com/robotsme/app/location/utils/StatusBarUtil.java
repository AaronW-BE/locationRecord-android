package com.robotsme.app.location.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarUtil {

    /**
     * 添加StatusBar
     * @param activity
     * @param color
     */
    protected void addStatusBarView(Activity activity, int color) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup contentView = decorView.findViewById(android.R.id.content);
        int statusBarHeight = getStatusBarHeight(activity);
        contentView.setPadding(0, statusBarHeight, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        View statusBarView = new View(activity);
        statusBarView.setBackgroundColor(color);
        decorView.addView(statusBarView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight));
    }

    /**
     * 获取StatusBar的高度
     * @param mContext
     * @return
     */
    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        Resources resources = mContext.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = resources.getDimensionPixelSize(resId);
        }
        return result;
    }
}
