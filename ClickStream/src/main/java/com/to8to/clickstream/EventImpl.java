package com.to8to.clickstream;

import android.content.Context;

import com.to8to.clickstream.toolbox.CommonUtil;

/**
 * Created by moon.zhong on 2014/12/2.
 * 点击流事件实现类
 */
public class EventImpl extends AbsEvent implements IEvent {

    private static IEvent instance;

    private EventImpl(Context context) {
        super(context);
    }

    public static synchronized IEvent getInstance(Context context) {
        if (instance == null) {
            instance = new EventImpl(context);
        }
        return instance;
    }


    @Override
    public void onPause(String eventName) {
        String currentTime = CommonUtil.getCurrentTime();
        dataHandler.getUserEvent().setLeaveTime(currentTime);
        if (!findEventRegister(getActivityName(eventName)).equals(UN_STATISTICS + "@"))
            requestEvent();
    }

    @Override
    public void onResume(String eventName) {
        String currentTime = CommonUtil.getCurrentTime();
        String registerValue = findEventRegister(getActivityName(eventName));
        String[] etAndVr = registerValue.split("@");
        dataHandler.getUserEvent().setEventType(1);
        dataHandler.getUserEvent().setLeaveTime(currentTime);
        dataHandler.getUserEvent().setVisitTime(currentTime);
        dataHandler.getUserEvent().setEventId(CommonUtil.getCurrentId());
        dataHandler.getUserEvent().setEventName(etAndVr[0]);
        dataHandler.getUserEvent().setVisitResource(etAndVr[1]);
    }

    @Override
    public void onEvent(String eventName) {
        String currentTime = CommonUtil.getCurrentTime();
        dataHandler.getUserEvent().setEventType(2);
        dataHandler.getUserEvent().setLeaveTime(currentTime);
        dataHandler.getUserEvent().setVisitTime(currentTime);
        dataHandler.getUserEvent().setEventId(CommonUtil.getCurrentId());
        dataHandler.getUserEvent().setEventName(eventName);
        dataHandler.getUserEvent().setVisitResource("");
        requestEvent();
    }

    @Override
    public void onPost() {
        postEvent();
    }
}
