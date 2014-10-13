package com.moon.amap.sdk;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.moon.amap.sdk.entity.MLocation;
import com.moon.amap.sdk.intf.MILocateService;
import com.moon.amap.sdk.listerner.MOnLocateLister;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by moon.zhong on 2014/10/10.
 * 定位核心接口实现类
 */
public class MLocateServiceImpl implements MILocateService,AMapLocationListener,Runnable{
    /*高德地图定位服务入口类*/
    private LocationManagerProxy mLocationManagerProxy ;
    /*定位信息保存*/
    private MLocation mLocation ;
    /*延时操作，当定位超过10s，还没反应，自动停止定位*/
    private Handler mHandler =  new Handler();
    /*定位超时，超时时间为10s*/
    private static final int LOCATE_TIME_OUT = 10*1000;
    /*有多个地方同时调用的时候需要注册
    * 多个监听，可以用观察这模式吗？
    * 好像可以唉。
    * */
    private List<MOnLocateLister> mOnLocateListerList ;

    public MLocateServiceImpl() {
        if(mLocation == null){
            mLocation = new MLocation() ;
        }
    }

    @Override
    public void startLocation(Context context) {
       if (mLocationManagerProxy == null){
           mLocationManagerProxy = LocationManagerProxy.getInstance(context) ;
           mLocationManagerProxy.requestLocationUpdates(LocationProviderProxy.AMapNetwork, 5000, 10, this);
           mHandler.postDelayed(this ,LOCATE_TIME_OUT) ;
       }
    }

    @Override
    public void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destory();
        }
        mLocationManagerProxy = null;
    }

    @Override
    public MLocation getLocation() {
        return mLocation;
    }

    @Override
    public void registerLocateLister(MOnLocateLister mOnLocateLister) {
        if(this.mOnLocateListerList == null){
            this.mOnLocateListerList = new ArrayList<MOnLocateLister>() ;
        }
        if(!mOnLocateListerList.contains(mOnLocateLister)){
            mOnLocateListerList.add(mOnLocateLister) ;
        }
    }

    @Override
    public void unRegisterLocateLister(MOnLocateLister mOnLocateLister) {
        if(mOnLocateListerList.contains(mOnLocateLister)){
            mOnLocateListerList.remove(mOnLocateLister) ;
        }
    }


    @Override
    public void run() {
        if(mOnLocateListerList != null){
            /* java.util.ConcurrentModificationException*/
            for(MOnLocateLister mOnLocateLister:mOnLocateListerList){
                mOnLocateLister.onLocateFail();
            }
        }
        stopLocation() ;
    }

    /**
     * 向观察者们发送更新提示
     * @param aMapLocation
     */
    private void invoke(AMapLocation aMapLocation){
        if (aMapLocation != null) {
            mLocation.setProvider(aMapLocation.getProvider());
            mLocation.setAddress(aMapLocation.getAddress());
            mLocation.setCity(aMapLocation.getCity());
            mLocation.setCityCode(aMapLocation.getCityCode());
            mLocation.setLocateTime(aMapLocation.getTime());
            mLocation.setLatitude(aMapLocation.getLatitude());
            mLocation.setLongitude(aMapLocation.getLongitude());
            mLocation.setProvince(aMapLocation.getProvince());
            mLocation.setStreet(aMapLocation.getStreet());
            if(mOnLocateListerList != null){
                for(MOnLocateLister mOnLocateLister:mOnLocateListerList){
                    mOnLocateLister.onLocateSuccess(mLocation);
                }
            }
        }else{
            /*定位失败*/
            if(mOnLocateListerList != null){
                for(MOnLocateLister mOnLocateLister:mOnLocateListerList){
                    mOnLocateLister.onLocateFail();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        mHandler.removeCallbacks(this);
        invoke(aMapLocation) ;
        stopLocation() ;
    }

    /*----------------------------------------下面这个方法已经废弃了----------------------------------------------------*/
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
