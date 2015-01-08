package com.to8to.clickstream;

import com.to8to.clickstream.network.ClickQueue;

/**
 * Created by moon.zhong on 2014/12/2.
 * 点击流模块工厂接口
 */
public interface IClickStream {

    /*确定发送数据量，默认为20条每次*/
    public final static int defaultNum = 10 ;

    /*这个不解释*/
    public IEvent newEventInStance() ;

    /**
     *    注册      页面统计时长需要用到
     * @param activityName activity名称
     * @param encodeType 页面编码
     */
    public void registerEvent(String activityName, String encodeType) ;

    /**
     *
     * @param activityName
     * @param encodeType
     * @param resource 资源 ，etc url...
     */
    public void registerEvent(String activityName, String encodeType, String resource) ;

    /*统计用户ID，当用户更换了用户名，需要重新设置*/
    public void setUid(String uid) ;
    public ClickQueue initClickQueue() ;

}
