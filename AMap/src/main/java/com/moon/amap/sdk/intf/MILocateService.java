package com.moon.amap.sdk.intf;

import android.content.Context;

import com.moon.amap.sdk.entity.MLocation;
import com.moon.amap.sdk.listerner.MOnLocateLister;

/**
 * Created by moon.zhong on 2014/10/10.
 * 定位接口，主要操作：开始定位、结束定位
 * 获取定位信息
 * 注册定位监听事件，
 * 取消定位监听事件
 */
public interface MILocateService {
    /**
     * 开始定位
     */
    public void startLocation(Context context) ;

    /**
     * 结束定位
     */
    public void stopLocation() ;

    /**
     * 获取定位信息
     * @return
     */
    public MLocation getLocation() ;

    /**
     * 注册定位监听事件
     * @param mOnLocateLister
     */
    public void registerLocateLister(MOnLocateLister mOnLocateLister) ;

    /**
     * 取消定位监听事件
     * @param mOnLocateLister
     */
    public void unRegisterLocateLister(MOnLocateLister mOnLocateLister) ;

    /**
     * 调用者结束时或者销毁时调用此方法
     * 如果未调用，以下特殊情况会出现
     * 奔溃效果
     * 当定位失败，然后没有等到调用注销
     * 监听事件，再次进来调用定位功能
     * 就会报错
     */
//    public void destroyInvoke() ;

}
