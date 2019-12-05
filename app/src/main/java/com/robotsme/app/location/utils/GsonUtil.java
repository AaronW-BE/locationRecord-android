package com.robotsme.app.location.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.robotsme.app.location.bean.BaseEntity;

public class GsonUtil {

    public static <T> BaseEntity<T> parseBaseEntity(String jsonStr, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        BaseEntity<T> baseEntity = new BaseEntity<>();
        Gson gson = new Gson();
        T data = gson.fromJson(jsonStr, clazz);
        baseEntity.setData(data);
        return baseEntity;
    }
}
