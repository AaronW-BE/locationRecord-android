package com.robotsme.app.location;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.robotsme.app.location.api.BaseApi;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "https://weapp.heymom.com.cn/api/v1/weapp/common/banner";

        BaseApi.getInstance().get(url, null);
    }
}
