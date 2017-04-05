package com.yushilei.im.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yushilei.im.BaseApp;
import com.yushilei.im.constant.Constant;

/**
 * @auther by yushilei.
 * @time 2017/4/5-14:15
 * @desc
 */

public class SpUtil {
    private static SharedPreferences getLocalSp() {
        return BaseApp.appContext.getSharedPreferences(Constant.LOCAL_SP, Context.MODE_PRIVATE);
    }

    public static void save(String key, Object value) {
        SharedPreferences.Editor edit = getLocalSp().edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        }
        edit.commit();
    }

    public static <T> T getSP(String key, Class<T> tClass) {
        T value = null;
        SharedPreferences sp = getLocalSp();
        if (tClass.isAssignableFrom(Boolean.class)) {
            value = (T) Boolean.valueOf(sp.getBoolean(key, false));
        } else if (tClass.isAssignableFrom(Integer.class)) {
            value = (T) Integer.valueOf(sp.getInt(key, -1));
        } else if (tClass.isAssignableFrom(String.class)) {
            value = (T) String.valueOf(sp.getString(key, null));
        } else if (tClass.isAssignableFrom(Float.class)) {
            value = (T) Float.valueOf(sp.getFloat(key, -1.0f));
        } else if (tClass.isAssignableFrom(Long.class)) {
            value = (T) Long.valueOf(sp.getLong(key, -1));
        }
        return value;
    }
}
