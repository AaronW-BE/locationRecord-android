package com.robotsme.app.location.api;

import com.robotsme.app.location.constract.IRequestCallback;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ALocation {
    private final String LOCATION_URL = "http://47.102.41.243:8000/location";

    public void report(Map<String, Object> data, IRequestCallback callback) {
        BaseApi.getInstance().put(LOCATION_URL, data, callback);
    }

    public void fetch(Map<String, String> data, IRequestCallback callback) throws UnsupportedEncodingException {
        BaseApi.getInstance().get(LOCATION_URL, data, callback);
    }
}
