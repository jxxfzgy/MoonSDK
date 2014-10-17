package com.moon.sdk.ormlite.dao;

import android.content.Context;

import com.moon.sdk.ormlite.core.abst.MDaoImpl;
import com.moon.sdk.ormlite.entity.MApkInfo;

/**
 * Created by moon.zhong on 2014/10/17.
 * 数据库直接操作类，
 * 只需要实例化此类即可，实现数据库操作的所有功能
 */
public class MDaoApkInfo extends MDaoImpl<MApkInfo> {
    public MDaoApkInfo(Context context) {
        super(context);
    }
}
