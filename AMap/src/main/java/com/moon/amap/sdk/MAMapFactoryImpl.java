package com.moon.amap.sdk;

import com.moon.amap.sdk.intf.MIAMapFactory;
import com.moon.amap.sdk.intf.MILocateService;

/**
 * Created by moon.zhong on 2014/10/10.
 * 高德地图工厂类，生产各种服务，
 * miLocation： 定位服务
 *
 */
public class MAMapFactoryImpl implements MIAMapFactory{
    //定位服务接口
    private MILocateService miLocation ;
    //实例化自己
    private static MIAMapFactory maMapFactory = new MAMapFactoryImpl() ;

    /**
     * 在加载此类的时候就实例化了本身，所以不需要使用
     * 同步代码块
     * @return maMapFactory
     */
    public static MIAMapFactory getMaMapFactoryImpl(){
        return maMapFactory;
    }

    /**
     *  从工厂中获取定位服务
     * @return MILocation
     */
    @Override
    public synchronized MILocateService getLocationService(){
        if(miLocation == null){
            miLocation = new MLocateServiceImpl() ;
        }
        return miLocation ;
    }
}
