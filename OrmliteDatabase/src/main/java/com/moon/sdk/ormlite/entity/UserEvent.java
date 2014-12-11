package com.moon.sdk.ormlite.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class UserEvent {

    /*事件类型*/
    @SerializedName("et")
    @DatabaseField
    private int eventType;
    /*事件开始时间*/
    @SerializedName("vt")
    @DatabaseField
    private String visitTime;
    /*事件结束时间*/
    @SerializedName("lt")
    @DatabaseField
    private String leaveTime;
    /*事件访问资源*/
    @SerializedName("vr")
    @DatabaseField
    private String visitResource;
    /*事件的名称*/
    @SerializedName("en")
    @DatabaseField
    private String eventName;
    /*事件ID*/
    @SerializedName("ci")
    @DatabaseField (id = true)
    private String eventId;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getVisitResource() {
        return visitResource;
    }

    public void setVisitResource(String visitResource) {
        this.visitResource = visitResource;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "eventType=" + eventType +
                ", visitTime='" + visitTime + '\'' +
                ", leaveTime='" + leaveTime + '\'' +
                ", visitResource='" + visitResource + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventId='" + eventId + '\'' +
                '}';
    }
}
