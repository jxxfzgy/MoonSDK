package com.moon.amap.sdk.intf;

/**
 * Created by moon.zhong on 2014/10/11.
 * 工厂的核心接口，
 * 生产高德地图所有服务
 */
public interface MIAMapFactory {

    /**
     *  生产定位服务
     * @return
     */
    public MILocateService getLocationService() ;
}
