package com.moon.sdk.ormlite.core;

import android.content.Context;

import com.j256.ormlite.table.TableUtils;
import com.moon.sdk.ormlite.core.abst.MBaseOrmliteHelper;
import com.moon.sdk.ormlite.entity.MApkInfo;
import com.moon.sdk.ormlite.entity.MContact;
import com.moon.sdk.ormlite.entity.MContactBean;
import com.moon.sdk.ormlite.entity.UserEvent;

import java.sql.SQLException;

/**
 * Created by moon.zhong on 2014/10/17.
 * 当需要创建数据表时，需要修改版本号
 * 和在onCreateTable(),onUpdateTable()中
 * 各加入一个方法。
 * 如果需要修改数据库名称，则在父类中修改
 */
public class MOrmliteHelper extends MBaseOrmliteHelper {
    /*数据库版本号*/
    public static final int DATABASE_VERSION = 1;

    public MOrmliteHelper(Context context) {
        super(context,DATABASE_VERSION);
    }

    @Override
    public void onCreateTable() {
        try {
            TableUtils.createTable(connectionSource, MContact.class);
            TableUtils.createTable(connectionSource, MApkInfo.class);
            TableUtils.createTable(connectionSource, MContactBean.class) ;
            TableUtils.createTable(connectionSource, UserEvent.class) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateTable() {
        try {
            TableUtils.dropTable(connectionSource, MContact.class, true);
            TableUtils.dropTable(connectionSource, MApkInfo.class, true);
            TableUtils.dropTable(connectionSource,MContactBean.class,true) ;
            TableUtils.dropTable(connectionSource,UserEvent.class,true) ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
