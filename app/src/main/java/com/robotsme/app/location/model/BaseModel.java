package com.robotsme.app.location.model;

import com.robotsme.app.location.api.BaseApi;

public class BaseModel {

    public final String BASE_URL = "http://47.102.41.243:8000/";
    public final String LOGIN_URL = BASE_URL + "auth";
    protected BaseApi mApi;

    public BaseModel() {
        mApi = BaseApi.getInstance();
    }
}
