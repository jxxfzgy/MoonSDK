package com.to8to.clickstream.entity;

import java.io.UnsupportedEncodingException;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class StringData implements IFormData {
    public String stringData ;
    private String name ;
    private String fileName ;

    public StringData(String name, String value, String fileName) {
        this.stringData = value;
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public byte[] getData() {
        try {
            return  stringData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return stringData.getBytes() ;
        }
    }

    @Override
    public String getMimeType() {
        return "text/plain";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFileName() {
        return fileName;
    }
}
