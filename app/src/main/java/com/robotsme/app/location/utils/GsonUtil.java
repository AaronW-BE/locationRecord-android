package com.robotsme.app.location.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.robotsme.app.location.api.ParameterizedTypeImpl;
import com.robotsme.app.location.bean.BaseEntity;

import java.lang.reflect.Type;

public class GsonUtil {

    public static <T> BaseEntity<T> parseBaseEntity(String jsonStr, Class<T> clazz) {
        if (TextUtils.isEmpty(jsonStr)) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new ParameterizedTypeImpl(BaseEntity.class, new Class[]{clazz});
        return gson.fromJson(jsonStr, type);
    }
}
