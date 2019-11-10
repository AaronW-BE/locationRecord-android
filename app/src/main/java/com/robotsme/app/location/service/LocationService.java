package com.robotsme.app.location.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.robotsme.app.location.R;

import java.util.Date;

public class LocationService extends Service {

    private static final String TAG = "LogLocationService";

    public AMapLocationClient mapLocationClient = null;

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
                    }
                }
            }
        });
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mapLocationClient.startLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapLocationClient.stopLocation();
        mapLocationClient = null;
    }
}
