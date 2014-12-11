package com.moon.volley.entity;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MAvatar {
    private String picUrl ;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "MAvatar{" +
                "picUrl='" + picUrl + '\'' +
                '}';
    }
}
