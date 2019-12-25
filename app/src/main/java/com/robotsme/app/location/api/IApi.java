package com.robotsme.app.location.api;

import com.robotsme.app.location.constract.IRequestCallback;

import java.util.Map;

public interface IApi {
    void run(Map<String, Object> data, IRequestCallback requestCallback);
}
