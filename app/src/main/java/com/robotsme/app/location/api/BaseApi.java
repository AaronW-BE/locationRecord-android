package com.robotsme.app.location.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.robotsme.app.location.constract.IRequestCallback;
import com.robotsme.app.location.utils.MLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BaseApi {

    private static BaseApi instance = new BaseApi();
    private static OkHttpClient client;
    private BaseApi() {
        client = new OkHttpClient();
    }

    public static BaseApi getInstance() {
        return instance;
    }

    public void get(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }
            };
        });
    }

    public void post(String url, Map<String, Object> data, final IRequestCallback requestCallback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            builder.add(entry.getKey(), (String) entry.getValue());
        }

        RequestBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        requestCallback.willStart();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                requestCallback.onFailure(e);

                requestCallback.finished();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();


                if (!response.isSuccessful()) {
                    requestCallback.onFailure(new IOException("response is not successful"));
                    requestCallback.finished();
                    return;
                }

                requestCallback.onSuccess(responseBody);
                requestCallback.finished();
            }
        });
    }
}

