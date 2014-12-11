package com.moon.sdk.ormlite.dao;

import android.content.Context;

import com.moon.sdk.ormlite.core.abst.MDaoImpl;
import com.moon.sdk.ormlite.entity.UserEvent;


/**
 * Created by moon.zhong on 2014/12/3.
 */
public class UserEventDao extends MDaoImpl<UserEvent> {
    public UserEventDao(Context context) {
        super(context);
    }
}
