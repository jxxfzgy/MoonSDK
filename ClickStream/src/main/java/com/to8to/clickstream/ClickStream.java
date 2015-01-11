package com.to8to.clickstream;

import android.content.Context;

import com.to8to.clickstream.network.ClickQueue;
import com.to8to.clickstream.network.ClickVolley;

import java.io.File;
import java.io.IOException;

/**
 * Created by moon.zhong on 2014/12/2.
 * 点击流工厂实现类
 */
public class ClickStream implements IClickStream ,IRegister{
    private EventImpl event ;
    private Context context ;
    public static String filePath  ;
    public static String EVENT_Path  ;
    private ClickQueue clickQueue ;
    private static IClickStream iClickStream ;

    public static synchronized IClickStream newInstance(Context context){
        if(iClickStream == null){
            iClickStream = new ClickStream(context) ;
        }
        return iClickStream ;
    }
    private ClickStream(Context context) {
        this.context = context;
        clickQueue = ClickVolley.newClickQueue() ;
        filePath = context.getFilesDir().getAbsolutePath()+ File.separator+"event.log" ;
        EVENT_Path = context.getFilesDir().getAbsolutePath()+ File.separator+"user.log" ;
        File file = new File(filePath) ;
        File fileEvent = new File(EVENT_Path) ;
        if(!file.exists()){
            try {
                fileEvent.createNewFile() ;
                file.createNewFile() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IEvent newEventInStance() {
        return EventImpl.getInstance(context);
    }

    @Override
    public void registerEvent(String activityName, String encodeType) {
        event = (EventImpl)EventImpl.getInstance(context) ;
        event.register(activityName,encodeType,null);
    }

    @Override
    public void registerEvent(String activityName, String encodeType, String resource) {
        event = (EventImpl)EventImpl.getInstance(context) ;
        event.register(activityName,encodeType,resource);
    }

    @Override
    public void setUid(String uid) {
        event = (EventImpl)EventImpl.getInstance(context) ;
        event.setUid(uid);
    }
    @Override
    public ClickQueue initClickQueue() {
        return clickQueue;
    }
}
