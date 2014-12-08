package com.moon.volley.entity;

import java.io.Serializable;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class TestBean implements Serializable {

    private String status ;
    private String info ;
    private Result data ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "status='" + status + '\'' +
                ", info='" + info + '\'' +
                ", data=" + data +
                '}';
    }
}
