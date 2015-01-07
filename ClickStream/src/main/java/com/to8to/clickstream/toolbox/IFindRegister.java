package com.to8to.clickstream.toolbox;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public interface IFindRegister {

    public final String UN_STATISTICS  = "unStatistics@ ";

    public String findEventRegister(String key);

    public void addMap(String key, String value) ;

    public void unRegister() ;
}
