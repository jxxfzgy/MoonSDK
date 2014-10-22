package com.moon.sdk.ormlite.dao;

import android.content.Context;

import com.moon.sdk.ormlite.core.abst.MDaoImpl;
import com.moon.sdk.ormlite.entity.MContactBean;

/**
 * Created by moon.zhong on 2014/10/22.
 */
public class MDaoContactBean extends MDaoImpl<MContactBean> {
    public MDaoContactBean(Context context) {
        super(context);
    }
}
