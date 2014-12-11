package com.to8to.clickstream;

import android.content.Context;

/**
 * Created by moon.zhong on 2014/12/2.
 * 点击流事件接口，需要扩展接口
 * 先要在此处添加，
 */
public interface IEvent {

    /*关闭页面调用*/
    public void onPause(String eventName) ;
    /*启动页面时调用*/
    public void onResume(String eventName);
    /*
    * 点击统计方法
    * 参数说明：eventName 按钮的编码
    *
    * */
    public void onEvent(String eventName);

    /**
     * 向服务器发送数据
     */
    public void onPost() ;

}
