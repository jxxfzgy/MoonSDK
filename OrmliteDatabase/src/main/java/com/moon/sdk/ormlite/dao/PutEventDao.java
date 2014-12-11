package com.moon.sdk.ormlite.dao;

import android.content.Context;

import com.moon.sdk.ormlite.core.abst.MDaoImpl;
import com.moon.sdk.ormlite.entity.PutEvent;


/**
 * Created by moon.zhong on 2014/12/3.
 */
public class PutEventDao extends MDaoImpl<PutEvent> {
    public PutEventDao(Context context) {
        super(context);
    }
}
