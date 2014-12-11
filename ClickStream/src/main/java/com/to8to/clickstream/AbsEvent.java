package com.to8to.clickstream;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.moon.sdk.ormlite.dao.PutEventDao;
import com.moon.sdk.ormlite.dao.UserEventDao;
import com.moon.sdk.ormlite.entity.PutEvent;
import com.moon.sdk.ormlite.entity.UserEvent;
import com.to8to.clickstream.toolbox.CommonUtil;
import com.to8to.clickstream.toolbox.PostImpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/2.
 * 点击流事件的父类，
 * 这里可以实现，获取工具类中的
 * 公共信息，把这类分离开来，
 * 防止实现类太臃肿，其实分离开
 * 意义不是很大.
 */
public abstract class AbsEvent {

    public Context context ;
    public PutEventDao putEventDao ;
    public UserEventDao userEventDao ;
    public EventThread eventThread ;
    public DataHandler dataHandler ;
    public final String UN_STATISTICS  = "unStatistics";

    private static final Map<String,String> pageEvent = new HashMap<String, String>() ;

    public AbsEvent(Context context){
        this.context = context ;
        userEventDao = new UserEventDao(context) ;
        dataHandler = new DataHandler() ;
        dataHandler.setUserEventDao(userEventDao);
        dataHandler.setPutEvent(new PutEvent());
        dataHandler.setUserEvent(new UserEvent());
        if(eventThread == null){
            eventThread = new EventThread(context,dataHandler, new PostImpl(new Handler(Looper.myLooper()))) ;
            eventThread.start();
        }

    }



    public void requestEvent(){
        eventThread.requestEventThread();
    }

    public void postEvent(){
        eventThread.postEventThread();
//        initEvent() ;
    }

    protected void register(String activityName, String encodeType, String resource){
        if(TextUtils.isEmpty(resource)){
            resource = encodeType ;
        }
        pageEvent.put(activityName,encodeType+"@"+resource) ;
    }

    protected String findEventRegister(String key){
        String value = pageEvent.get(key) ;
        if(TextUtils.isEmpty(value)){
            Log.e("zgy", "======你还没有注册此页面====默认不统计========") ;
            value = UN_STATISTICS+"@ " ;
        }
        return value ;
    }

    protected String getActivityName(String simpleName){
        simpleName = simpleName.substring(simpleName.lastIndexOf(".")+1,simpleName.length()) ;

        return simpleName ;
    }

    protected void setUid(String uid){
        dataHandler.setUid(uid);
    }
}
