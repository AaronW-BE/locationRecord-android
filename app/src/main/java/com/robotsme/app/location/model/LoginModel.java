package com.robotsme.app.location.model;

import com.robotsme.app.location.api.IHttpBack;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel {

    public void login(String username, String password, IHttpBack callback) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        mApi.post(LOGIN_URL, body, callback);
    }
}
