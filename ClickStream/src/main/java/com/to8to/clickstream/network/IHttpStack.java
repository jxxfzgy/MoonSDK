package com.to8to.clickstream.network;

import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public interface IHttpStack {

    public HttpResponse performRequest(AbsRequest absRequest) throws IOException;
}
