package com.robotsme.app.location;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;

public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static final LinkedList<Activity> activities = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    public static void killAll() {
        LinkedList<Activity> copy;
        synchronized (activities) {
            copy = new LinkedList<>(activities);
        }
        for (Activity activity : copy) {
            activity.finish();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        synchronized (this) {
            activities.add(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        synchronized (this) {
            activities.remove(activity);
        }
    }
}
