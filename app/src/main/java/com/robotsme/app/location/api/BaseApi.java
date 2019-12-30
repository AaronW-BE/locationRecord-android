package com.robotsme.app.location.api;

import android.os.Handler;
import android.os.Looper;

import com.robotsme.app.location.utils.MLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseApi {

    private static BaseApi instance = new BaseApi();
    private static OkHttpClient client;
    private Handler mHandler;

    private BaseApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(s -> {
            MLog.mLog("okhttp", "http---> " + s);
        });
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static BaseApi getInstance() {
        return instance;
    }

    public void get(String url, IHttpBack httpBack) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(() -> {
                    httpBack.onFailure(call, e);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(() -> {
                    try {
                        httpBack.onResponse(call, response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void post(String url, RequestBody body, IHttpBack httpBack) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mHandler.post(() -> {
                    httpBack.onFailure(call, e);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                mHandler.post(() -> {
                    try {
                        httpBack.onResponse(call, response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
