package com.robotsme.app.location.constract;

import java.io.IOException;

import okhttp3.ResponseBody;


public interface IRequestCallback {
    void onSuccess(ResponseBody responseBody);

    void onFailure(IOException e);

    void willStart();

    void finished();
}
