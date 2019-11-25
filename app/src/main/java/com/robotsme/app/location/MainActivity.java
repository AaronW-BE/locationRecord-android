package com.robotsme.app.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.robotsme.app.location.bean.LocationBean;
import com.robotsme.app.location.service.LocationBinder;
import com.robotsme.app.location.service.LocationService;
import com.robotsme.app.location.utils.MLog;
import com.zsd.android.dblib.db.BaseDao;
import com.zsd.android.dblib.db.BaseDaoFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationSource, AMap.OnCameraChangeListener, LocationService.OnLocationChangeListener {

    private MapView mMapView;

    private final int PERMISSION_LOCATION_CODE = 101;
    private AMap mGDMap;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private BaseDao<LocationBean> locationDao;
    private ArrayList<LatLng> latLngs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap(savedInstanceState);
        startLocateService();
        locationDao = BaseDaoFactory.getInstance().getDao(LocationBean.class);
        List<LocationBean> locationInfos = locationDao.query(new LocationBean());
        latLngs = new ArrayList<>();
        if (locationInfos != null && locationInfos.size() > 0) {
            LatLng ll = null;
            for (LocationBean locationInfo : locationInfos) {
                ll = new LatLng(locationInfo.getLat(), locationInfo.getLng());
                latLngs.add(ll);
            }
            mGDMap.addPolyline(new PolylineOptions().addAll(latLngs).width(5).color(ActivityCompat.getColor(this, R.color.colorAccent)));
        }
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView = findViewById(R.id.main_map);
        mMapView.onCreate(savedInstanceState);
        mGDMap = mMapView.getMap();
        mGDMap.setLocationSource(this);
        mGDMap.setOnCameraChangeListener(this);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.showMyLocation(true);
        myLocationStyle.interval(1000 * 60 * 5); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
        mGDMap.setMyLocationStyle(myLocationStyle);
        mGDMap.setMyLocationEnabled(true);
    }

    //开启定位
    public void startLocateService() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (PackageManager.PERMISSION_DENIED == checkSelfPermission) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_CODE);
            return;
        }
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocationBinder binder = (LocationBinder) service;
                LocationService locationService = binder.getService();
                locationService.setOnLocationChangeListener(MainActivity.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION_CODE:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    startLocateService();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mGDMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                locationChangedListener.onLocationChanged(aMapLocation);
                LatLng ll = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                latLngs.add(ll);
                mGDMap.clear();
                mGDMap.addPolyline(new PolylineOptions().addAll(latLngs).width(5).color(ActivityCompat.getColor(this, R.color.colorAccent)));
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.locationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
