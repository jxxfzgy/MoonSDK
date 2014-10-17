package com.moon.sdk.ormlite.core.abst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by moon.zhong on 2014/10/17.
 * <p/>
 * 数据库创建帮助类，入口；
 */
public abstract class MBaseOrmliteHelper extends OrmLiteSqliteOpenHelper {

    /*数据库名称*/
    private static final String DATABASE_NAME = "moon_table.db";

    /*上下文，目测是为了保存数据库的位置，所以需要传入*/


    public MBaseOrmliteHelper(Context context,int version) {
        super(context, DATABASE_NAME, null, version);
    }

    /**
     * 创建数据表
     *
     * @param database         Database being created.
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
            onCreateTable() ;
    }

    /**
     * 更新数据表
     *
     * @param database         Database being upgraded.
     * @param connectionSource To use get connections to the database to be updated.
     * @param oldVersion       The version of the current database so we can know what to do to the database.
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
            onUpdateTable() ;
            onCreate(database, connectionSource);
    }

    /**
     * 创建数据表
     */
    public abstract void onCreateTable() ;

    /**
     * 更新数据表
     */
    public abstract void onUpdateTable() ;

}
