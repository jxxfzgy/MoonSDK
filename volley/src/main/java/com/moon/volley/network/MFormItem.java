package com.moon.volley.network;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public interface MFormItem {
    /*获取参数的名称*/
    public String getName() ;
    /*获取mime类型*/
    public String getMimeType() ;
    /*获取文件的路径*/
    public String getFilePath() ;
    /*获取内容*/
    public byte[] getBody() ;
}
