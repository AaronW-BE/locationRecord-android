package com.robotsme.app.location.model;

import com.robotsme.app.location.api.BaseApi;

public class BaseModel {

    public final String BASE_URL = "";
    public final String LOGIN_URL = "";
    protected BaseApi mApi;

    public BaseModel() {
        mApi = BaseApi.getInstance();
    }
}
