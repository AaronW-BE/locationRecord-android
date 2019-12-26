package com.robotsme.app.location.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class QueryString {

    public static String build(Map<String, String> data) throws UnsupportedEncodingException {
        StringBuilder stringBuffer = new StringBuilder();
        for (String key : data.keySet()) {
            String value = data.get(key);
            value = URLEncoder.encode(value, "UTF-8");
            stringBuffer.append(key).append("=").append(value).append("&");
        }
        return stringBuffer.toString();
    }
}
