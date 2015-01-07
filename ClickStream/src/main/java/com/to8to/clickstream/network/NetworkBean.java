package com.to8to.clickstream.network;

import org.apache.http.HttpStatus;

import java.util.Collections;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class NetworkBean {
    public NetworkBean(int statusCode, byte[] data, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
    }

    public NetworkBean(byte[] data) {
        this(HttpStatus.SC_OK, data, Collections.<String, String>emptyMap());
    }

    public NetworkBean(byte[] data, Map<String, String> headers) {
        this(HttpStatus.SC_OK, data, headers);
    }

    /** The HTTP status code. */
    public final int statusCode;

    /** Raw data from this response. */
    public final byte[] data;

    /** Response headers. */
    public final Map<String, String> headers;
}
