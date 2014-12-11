package com.to8to.clickstream.toolbox;

/**
 * Created by moon.zhong on 2014/12/11.
 */
public interface IPost {

    public void postEvent(String event) ;

    public void postEvent(String event, Runnable runnableError) ;

    public void postEvent(String event, Runnable runnableError, Runnable runnableSuccess) ;
}
