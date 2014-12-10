package com.moon.volley.network;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MStringForm implements MFormItem {

    private String name ;
    private String filePath ;
    private String value ;

    public MStringForm(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public MStringForm() {
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getMimeType() {
        return "text/plain";
    }

    @Override
    public String getFilePath() {
        return null;
    }

    @Override
    public byte[] getBody() {
        return value.getBytes();
    }
}
