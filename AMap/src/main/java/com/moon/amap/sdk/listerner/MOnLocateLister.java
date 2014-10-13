package com.moon.amap.sdk.listerner;

import com.moon.amap.sdk.entity.MLocation;

/**
 * Created by moon.zhong on 2014/10/10.
 * 定位回调接口 ,定位失败，
 * 定位成功返回定位信息
 *
 */
public interface MOnLocateLister {

    /**
     * 定位成功返回，定位信息
     * @param mMLocation
     */
    public void onLocateSuccess(MLocation mMLocation) ;

    /**
     * 定位失败
     */
    public void onLocateFail() ;
}
