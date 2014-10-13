package com.moon.amap.sdk.entity;

/**
 * Created by moon.zhong on 2014/10/10.
 * 保存定位信息
 */
public class MLocation {
    /*定位方式*/
    private String mProvider  ;
    /*定位时间*/
    private long mLocateTime ;
    /*经度*/
    private double mLongitude ;
    /*纬度*/
    private double mLatitude ;
    /*详细位置*/
    private String mAddress ;
    /*省*/
    private String mProvince ;
    /*市*/
    private String mCity ;
    /*街道或门牌号*/
    private String mStreet ;
    /*城市编码*/
    private String mCityCode ;

    public String getProvider() {
        return mProvider;
    }

    public void setProvider(String mProvider) {
        this.mProvider = mProvider;
    }

    public long getLocateTime() {
        return mLocateTime;
    }

    public void setLocateTime(long mLocateTime) {
        this.mLocateTime = mLocateTime;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getProvince() {
        return mProvince;
    }

    public void setProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String mStreet) {
        this.mStreet = mStreet;
    }

    public String getCityCode() {
        return mCityCode;
    }

    public void setCityCode(String mCityCode) {
        this.mCityCode = mCityCode;
    }

    @Override
    public String toString() {
        return "MLocation{" +
                "mProvider='" + mProvider + '\'' +
                ", mLocateTime=" + mLocateTime +
                ", mLongitude=" + mLongitude +
                ", mLatitude=" + mLatitude +
                ", mAddress='" + mAddress + '\'' +
                ", mProvince='" + mProvince + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mStreet='" + mStreet + '\'' +
                ", mCityCode='" + mCityCode + '\'' +
                '}';
    }
}
