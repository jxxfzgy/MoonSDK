package com.to8to.clickstream.network;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public interface ClickResponse<T> {

    public void onResponse(T t) ;

    public void onErrorResponse() ;
}
