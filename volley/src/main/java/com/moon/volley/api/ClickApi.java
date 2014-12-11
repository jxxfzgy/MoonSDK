package com.moon.volley.api;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.moon.volley.MApi;
import com.moon.volley.entity.MClickBean;
import com.moon.volley.network.MFormItem;
import com.moon.volley.network.MRequest;
import com.moon.volley.network.MRequestFormJson;
import com.moon.volley.network.MRequestJson;
import com.moon.volley.network.MResponse;
import com.moon.volley.network.MStringForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/11.
 * 统计流网络接口
 */
public class ClickApi extends MApi{

    /*通过参数的方式向服务器发送统计数据*/
    public static void postParamClickStream(String clickStream, MResponse response){
        Map<String, String> params = createParam() ;
        params.put("model","Count") ;
        params.put("action","Click") ;
        params.put("json",clickStream) ;
        params.put("html","1") ;
        MRequest<MClickBean> request = new MRequestJson<MClickBean>(params, new TypeToken<MClickBean>(){}.getType(),response) ;
        doRequest(request);
    }

    public static void postDataClickStream(String clickStream, MResponse response){
        List<MFormItem> stringForms = new ArrayList<>() ;
        stringForms.add(new MStringForm("version","2.0"));
        stringForms.add(new MStringForm("model","Count"));
        stringForms.add(new MStringForm("action","Click"));
        stringForms.add(new MStringForm("json",clickStream));
        stringForms.add(new MStringForm("html","1"));
        MRequest<MClickBean> request = new MRequestFormJson<MClickBean>(stringForms,new TypeToken<MClickBean>(){}.getType(),response) ;
        doRequest(request);
    }
}
