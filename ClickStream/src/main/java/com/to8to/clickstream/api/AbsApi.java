package com.to8to.clickstream.api;

import com.to8to.clickstream.IClickStream;
import com.to8to.clickstream.network.AbsRequest;
import com.to8to.clickstream.toolbox.ClickUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public abstract class AbsApi {

    public static void addQueue(AbsRequest request) {
        IClickStream iClickStream = ClickUtil.getClickStream();
        iClickStream.initClickQueue().add(request);
    }

    public static Map<String,String> createParams(){
        Map<String,String> params = new HashMap<>() ;
        params.put("action", "Click");
        params.put("model", "Count");
        params.put("version", "2.0");
        return params ;
    }
}
