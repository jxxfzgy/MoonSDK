package com.moon.volley.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.moon.volley.MConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class MRequestXml extends MRequest<String>{

    public MRequestXml(int method, String url, Map<String, String> params, MResponse<String> response) {
        super(method, url, params,response);
    }

    public MRequestXml(String url, Map<String, String> param, MResponse<String> response){
        this(Method.POST,url,param, response) ;
    }

    public MRequestXml(Map<String, String> param, MResponse<String> response){
        this(Method.POST, MConstant.URL,param,response) ;

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
            if(saveXmlFile(response) ){
                return Response.success("success",HttpHeaderParser.parseCacheHeaders(response)) ;
            }else{
                return Response.error(new ParseError()) ;
            }
    }

    private boolean saveXmlFile(NetworkResponse response){
        try {
            String json = new String(response.data,HttpHeaderParser.parseCharset(response.headers)) ;
            try {
                JSONObject jsonObject = new JSONObject(json);
                if(jsonObject.getInt("errorCode") == 0){
                    writeByteInFile(response.data,"");
                    return true ;
                }else {
                    return false ;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false ;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false ;
        }
    }

    private void writeByteInFile(byte[] buffer, String filePath){
//        ByteArrayOutputStream
    }
}
