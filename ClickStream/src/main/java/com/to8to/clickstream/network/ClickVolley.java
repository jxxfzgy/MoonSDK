package com.to8to.clickstream.network;


/**
 * Created by moon.zhong on 2014/12/26.
 */
public class ClickVolley {

    public static ClickQueue newClickQueue(){

        INetwork iNetwork  = new BaseNetwork(new ClickHttp()) ;
        ClickQueue queue = new ClickQueue(iNetwork) ;
        queue.start();
        return queue ;
    }

}
