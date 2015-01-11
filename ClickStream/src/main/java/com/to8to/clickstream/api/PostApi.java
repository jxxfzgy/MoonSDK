package com.to8to.clickstream.api;

import com.google.gson.reflect.TypeToken;
import com.to8to.clickstream.entity.Data;
import com.to8to.clickstream.entity.IFormData;
import com.to8to.clickstream.entity.StringData;
import com.to8to.clickstream.network.AbsRequest;
import com.to8to.clickstream.network.ClickDataRequest;
import com.to8to.clickstream.network.ClickRequest;
import com.to8to.clickstream.network.ClickResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class PostApi extends AbsApi {

    public static void postParamData(String data, ClickResponse<Data<String>> clickResponse){
        Map<String,String> params = createParams() ;
        params.put("json",data) ;
        params.put("html","1") ;
        ClickRequest<String> request = new ClickRequest<>(params, AbsRequest.Method.POST,new TypeToken<Data<String>>(){}.getType(),clickResponse) ;
        addQueue(request);
    }

    public static void postData(String data,ClickResponse<Data<String>> clickResponse){
        List<IFormData> formDatas = new ArrayList<>() ;
        formDatas.add(new StringData("action", "Click",null)) ;
        formDatas.add(new StringData("model", "Count",null)) ;
        formDatas.add(new StringData("version", "2.0",null)) ;
        formDatas.add(new StringData("json",data,null)) ;
        formDatas.add(new StringData("html","1",null)) ;
        ClickDataRequest<String> request = new ClickDataRequest<>(formDatas, AbsRequest.Method.POST,new TypeToken<Data<String>>(){}.getType(),clickResponse) ;
        addQueue(request);
    }
}
