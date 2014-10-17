package com.moon.sdk.ormlite.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by moon.zhong on 2014/10/17.
 * 数据库操作对象 联系人
 * 用于测试
 */
@DatabaseTable(tableName = "contact")
public class MContact {
    /*表中自动生成的id*/
    @DatabaseField(generatedId = true)/*自动增加*/
    private int _id ;
    /*联系人的名字*/
    @DatabaseField(canBeNull = false)/*顾名思义*/
    private String name ;
    /*联系人的电话*/
    @DatabaseField
    private String telNum ;
    /*联系人的公司*/
    @DatabaseField
    private String company ;
    /*联系人的住址*/
    @DatabaseField(columnName = "location")
    private String address ;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "MContact{" +
                ", name='" + name + '\'' +
                ", telNum='" + telNum + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
