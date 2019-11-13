package com.robotsme.app.location.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.robotsme.app.location.R;

import java.util.Date;

public class LocationService extends Service {

    private static final String TAG = "LogLocationService";

    public AMapLocationClient mapLocationClient = null;

    private Thread thread;

    public LocationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "初始化定位");
        mapLocationClient = new AMapLocationClient(getApplicationContext());
        mapLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                Log.e(TAG, "location changed");
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        Log.e(TAG, "定位成功");
                        Log.e(TAG, aMapLocation.toStr());
                        SharedPreferences sharedPreferences = getSharedPreferences("location", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String nowTimeStamp = String.valueOf(System.currentTimeMillis());
                        editor.putString(nowTimeStamp, aMapLocation.toStr());
                        editor.apply();
                        int size = sharedPreferences.getAll().size();
                        Log.e(TAG, "size : " + size);
                        Toast.makeText(getApplicationContext(), "address:" + aMapLocation.getStreet(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "errcode: " + aMapLocation.getErrorCode());
                        Log.e(TAG, "errinfo: " + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Log.e(TAG, "wait");
                        if (thread.isAlive()) {
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!mapLocationClient.isStarted()) {
                        mapLocationClient.startLocation();
                    }
                }
            }
        });
        thread.start();
        mapLocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mapLocationClient.stopLocation();
        mapLocationClient = null;
        thread.interrupt();
        super.onDestroy();
    }
}
