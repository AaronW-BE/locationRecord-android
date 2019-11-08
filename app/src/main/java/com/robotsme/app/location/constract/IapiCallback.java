package com.robotsme.app.location.constract;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

interface IapiCallback {
    /**\
     *
     */
    void onFailure(IOException e);

    /**
     *
     */
    void onSuccess(ResponseBody body, Response response);
}
