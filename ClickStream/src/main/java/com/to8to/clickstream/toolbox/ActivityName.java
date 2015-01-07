package com.to8to.clickstream.toolbox;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class ActivityName implements IFindRegister {

    protected static Map<String, String> pageEvent;

    @Override
    public String findEventRegister(String key) {
        if (pageEvent == null) {
            pageEvent = new HashMap<>();
        }
        String value = pageEvent.get(key);
        if (TextUtils.isEmpty(value)) {
            value = UN_STATISTICS;
        }
        return value;
    }

    @Override
    public void addMap(String key, String value) {
        if (pageEvent == null) {
            pageEvent = new HashMap<>();
        }
        pageEvent.clear();
        pageEvent.put(key, value);
    }

    @Override
    public void unRegister() {
        if (pageEvent != null)
            pageEvent.clear();
    }
}
