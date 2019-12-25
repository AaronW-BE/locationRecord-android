package com.robotsme.app.location.api;

import com.robotsme.app.location.constract.IRequestCallback;

import java.util.Map;

public class AUser{
    private final String LOGIN_URL = "http://47.102.41.243:8000/auth";

    public void login(Map<String, Object> data, IRequestCallback requestCallback) {
        BaseApi.getInstance().post(LOGIN_URL, data, requestCallback);
    }

    public void register(Map<String, Object> data, IRequestCallback requestCallback) {
        BaseApi.getInstance().post(LOGIN_URL, data, requestCallback);
    }
}
