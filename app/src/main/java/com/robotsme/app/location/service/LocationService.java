package com.robotsme.app.location.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.robotsme.app.location.utils.MLog;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service {

    public AMapLocationClient locationClient;
    private LocationBinder locationBinder;

    public LocationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        locationBinder = new LocationBinder(this);
        return locationBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startLocation();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        locationClient.stopLocation();
        locationClient = null;
        super.onDestroy();
    }

    /**
     * 初始化定位参数配置
     */

    private void startLocation() {
        //定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getApplicationContext());
        }
        //声明LocationClient类实例并配置定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //注册监听函数
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(5000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //缓存机制
        mLocationOption.setLocationCacheEnable(false);
        if (null != locationClient) {
            locationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            locationClient.stopLocation();
            locationClient.startLocation();
        }
    }

    public void setLocationListener(AMapLocationListener listener) {
        if (locationClient == null) {
            locationClient = new AMapLocationClient(getApplicationContext());
        }
        locationClient.setLocationListener(listener);
    }
}
