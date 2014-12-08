package com.moon.volley.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.moon.volley.MConstant;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class MRequestJson<T> extends MRequest<T>{

    private Type type ;
    public MRequestJson(int method, String url, Map<String,String> params, Type type ,MResponse<T> response) {
        super(method, url, params,response);
        this.type = type ;
    }

    public MRequestJson(String url, Map<String, String> param ,Type type , MResponse<T> response){
        this(Method.POST,url,param,type, response) ;
    }

    public MRequestJson(Map<String, String> param , Type type ,MResponse<T> response){
        this(Method.POST, MConstant.URL,param, type,response) ;

    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers)) ;
            Log.v("zgy", "===========jsonString==============" + jsonString) ;
            Gson gson = new Gson() ;
            T result = gson.fromJson(jsonString,type) ;
            return Response.success(result,HttpHeaderParser.parseCacheHeaders(response)) ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e)) ;
        }
    }
}
