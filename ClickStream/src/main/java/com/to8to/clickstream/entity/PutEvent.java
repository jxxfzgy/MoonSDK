package com.to8to.clickstream.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class PutEvent {
    /*用户ID*/
    @SerializedName("uid")
    private String uid ;
    /*cook ID*/
    @SerializedName("cid")
    private String cookId ;
    /*session ID*/
    @SerializedName("sid")
    private String sessionId ;
    /*用户的物理位置*/
    @SerializedName("ul")
    private String userLocation ;
    /*ip 地址*/
    @SerializedName("ip")
    private String ipAddress ;
    /*设备类型*/
    @SerializedName("dt")
    private String deviceType ;
    /*操作系统*/
    @SerializedName("ost")
    private String osType ;
    /*操作系统版本号*/
    @SerializedName("osv")
    private String osVersion ;
    /*产品的名称*/
    @SerializedName("pn")
    private String productName ;
    /*产品的版本号*/
    @SerializedName("pv")
    private String productVersion ;
    /*用户所用的代理*/
    @SerializedName("ua")
    private String userAgent ;
    /*浏览器的版本号*/
    @SerializedName("ev")
    private String explorerVersion ;
    /*运营商*/
    @SerializedName("st")
    private String spType ;
    /*网络类型*/
    @SerializedName("nt")
    private String networkType ;
    /*设备ID*/
    @SerializedName("di")
    private String deviceId ;
    /*设备分辨率*/
    @SerializedName("ds")
    private String displaySolution ;

    /*用户事件集合*/
    @SerializedName("e")
    private List<UserEvent> userEvents ;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(String productVersion) {
        this.productVersion = productVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getExplorerVersion() {
        return explorerVersion;
    }

    public void setExplorerVersion(String explorerVersion) {
        this.explorerVersion = explorerVersion;
    }

    public String getSpType() {
        return spType;
    }

    public void setSpType(String spType) {
        this.spType = spType;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDisplaySolution() {
        return displaySolution;
    }

    public void setDisplaySolution(String displaySolution) {
        this.displaySolution = displaySolution;
    }

    public List<UserEvent> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(List<UserEvent> userEvents) {
        this.userEvents = userEvents;
    }

    @Override
    public String toString() {
        return "PutEvent{" +
                "uid='" + uid + '\'' +
                ", cookId='" + cookId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", userLocation='" + userLocation + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", osType='" + osType + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", productName='" + productName + '\'' +
                ", productVersion='" + productVersion + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", explorerVersion='" + explorerVersion + '\'' +
                ", spType='" + spType + '\'' +
                ", networkType='" + networkType + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", displaySolution='" + displaySolution + '\'' +
                ", userEvents=" + userEvents +
                '}';
    }
}
