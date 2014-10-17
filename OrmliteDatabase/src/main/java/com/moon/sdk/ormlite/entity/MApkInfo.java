package com.moon.sdk.ormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by moon.zhong on 2014/10/17.
 * 第二个测试
 * apk详情
 */
@DatabaseTable(tableName = "apk_info")
public class MApkInfo {
    @DatabaseField(id = true)/*设为主键*/
    private String packageName ;
    @DatabaseField
    private String apkName ;
    @DatabaseField
    private String apkVersion ;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    @Override
    public String toString() {
        return "ApkInfo{" +
                "packageName='" + packageName + '\'' +
                ", apkName='" + apkName + '\'' +
                ", apkVersion='" + apkVersion + '\'' +
                '}';
    }
}
