package com.yushilei.im.util;

import com.google.gson.Gson;

/**
 * @auther by yushilei.
 * @time 2017/4/5-14:10
 * @desc
 */

public class Json {
    public static String toJson(Object t) {
        return new Gson().toJson(t);
    }

    public static <T> T jsonToObj(String json, Class<T> classOfT) {
        return new Gson().fromJson(json, classOfT);
    }
}
