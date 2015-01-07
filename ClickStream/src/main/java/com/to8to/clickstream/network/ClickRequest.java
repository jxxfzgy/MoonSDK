package com.to8to.clickstream.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.to8to.clickstream.api.ComApi;
import com.to8to.clickstream.entity.Data;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class ClickRequest<T> extends AbsRequest<Data<T>> {

    private Map<String,String> params ;
    private Type type ;
    private Gson gson ;

    public ClickRequest(Map<String,String> params,int method,Type type, ClickResponse clickResponse) {
        this(params, ComApi.HOST_URL, method, type,clickResponse);
    }

    public ClickRequest(Map<String,String> params,String url, int method, Type type,ClickResponse clickResponse) {
        this(params,url, 20000, method, type,clickResponse);
    }

    public ClickRequest(Map<String,String> params,String url, int timeOut, int method, Type type,ClickResponse clickResponse) {
        super(url, timeOut, method, clickResponse);
        this.params = params ;
        this.type = type ;
        gson = new Gson() ;
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public void parseResponse(NetworkBean networkBean) {

        String parsed;
        try {
            parsed = new String(networkBean.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(networkBean.data);
        }
        Log.v("zgy", "===========parsed=======" +parsed) ;
        try {
            Data<T> data = gson.fromJson(parsed,type) ;
            setError(false);
            setData(data);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            setError(true);
        }
    }

}
