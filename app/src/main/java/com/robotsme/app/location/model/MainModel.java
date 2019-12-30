package com.robotsme.app.location.model;

import com.robotsme.app.location.api.IHttpBack;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainModel extends BaseModel {

    public void getFootPoints(IHttpBack httpBack) {
        RequestBody body = new FormBody.Builder()
                .build();

        mApi.post("", body, httpBack);
    }
}
