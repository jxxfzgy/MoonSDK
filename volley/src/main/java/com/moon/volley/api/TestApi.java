package com.moon.volley.api;

import com.google.gson.reflect.TypeToken;
import com.moon.volley.MApi;
import com.moon.volley.entity.TestBean;
import com.moon.volley.network.MRequest;
import com.moon.volley.network.MRequestJson;
import com.moon.volley.network.MResponse;

import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class TestApi extends MApi{

    public static void postListDetail(MResponse response){
        Map<String,String> params = createParam() ;
        MRequest<TestBean> request = new MRequestJson<TestBean>(params,new TypeToken<TestBean>(){}.getType(),response) ;
        doRequest(request) ;
    }
}
