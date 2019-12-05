package com.robotsme.app.location.api;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BaseApi {

    private static BaseApi instance = new BaseApi();
    private static OkHttpClient client;

    private BaseApi() {
        client = new OkHttpClient();
    }

    public static BaseApi getInstance() {
        return instance;
    }

    public void get(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void post(String url, RequestBody body, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
