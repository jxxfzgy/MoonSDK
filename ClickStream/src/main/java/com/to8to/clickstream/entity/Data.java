package com.to8to.clickstream.entity;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class Data<T> {
    private String status ;
    private String allRows ;
    private T data ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRawCount() {
        return allRows;
    }

    public void setRawCount(String rawCount) {
        this.allRows = rawCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
