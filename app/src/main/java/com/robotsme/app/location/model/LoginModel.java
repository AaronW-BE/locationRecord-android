package com.robotsme.app.location.model;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginModel extends BaseModel {

    public void login(String username, String password, Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        mApi.post(LOGIN_URL, body, callback);
    }
}
