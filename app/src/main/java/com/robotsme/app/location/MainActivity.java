package com.robotsme.app.location;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.robotsme.app.location.service.LocationService;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogMain";

    public AMapLocationClient mapLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String url = "https://weapp.heymom.com.cn/api/v1/weapp/common/banner";
//
//        BaseApi.getInstance().get(url, null);
    }

    public void getLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        startService(intent);
    }

    public void stopLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), LocationService.class);
        stopService(intent);
    }

    public void getAllLocation(View view) {
        TextView textView = findViewById(R.id.text_location);
        SharedPreferences sharedPreferences = getSharedPreferences("location", 0);
        Map<String, ?> map =  sharedPreferences.getAll();
        textView.setText(map.toString());
    }

    public void clearLocation(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("location", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
