package com.to8to.clickstream.network;


/**
 * Created by moon.zhong on 2014/12/25.
 */
public interface IDelivery {

    public void onDelivery(AbsRequest<?> absRequest);

    public void onDelivery(AbsRequest<?> absRequest, Runnable runnable);

    public void onDeliveryError(AbsRequest<?> absRequest) ;

}
