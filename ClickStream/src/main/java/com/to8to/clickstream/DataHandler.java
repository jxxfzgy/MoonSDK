package com.to8to.clickstream;


import com.moon.sdk.ormlite.dao.PutEventDao;
import com.moon.sdk.ormlite.dao.UserEventDao;
import com.moon.sdk.ormlite.entity.PutEvent;
import com.moon.sdk.ormlite.entity.UserEvent;

/**
 * Created by moon.zhong on 2014/12/3.
 */
public class DataHandler {
    private UserEvent userEvent ;
    private PutEvent putEvent ;
    private UserEventDao userEventDao ;
    private PutEventDao putEventDao ;
    private String uid ;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserEvent getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(UserEvent userEvent) {
        this.userEvent = userEvent;
    }

    public PutEvent getPutEvent() {
        return putEvent;
    }

    public void setPutEvent(PutEvent putEvent) {
        this.putEvent = putEvent;
    }

    public UserEventDao getUserEventDao() {
        return userEventDao;
    }

    public void setUserEventDao(UserEventDao userEventDao) {
        this.userEventDao = userEventDao;
    }

    public PutEventDao getPutEventDao() {
        return putEventDao;
    }

    public void setPutEventDao(PutEventDao putEventDao) {
        this.putEventDao = putEventDao;
    }
}
