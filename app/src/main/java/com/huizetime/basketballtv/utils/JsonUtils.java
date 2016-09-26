package com.huizetime.basketballtv.utils;

import com.google.gson.Gson;

/**
 * Created by water_fairy on 2016/9/22.
 */
public class JsonUtils {
    public static String getJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T jsonToObject(String json, Class<T> type) {
        return new Gson().fromJson(json, type);
    }
}
