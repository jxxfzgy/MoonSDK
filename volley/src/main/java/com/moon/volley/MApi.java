package com.moon.volley;

import com.moon.volley.network.MRequest;
import com.moon.volley.network.MRequestQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public abstract class MApi {
    public static void doRequest(MRequest request){
        MRequestQueue.add(request);
    }

    public static Map<String ,String> createParam(){
        Map<String ,String> params = new HashMap<String, String>() ;
        return params ;
    }
}
