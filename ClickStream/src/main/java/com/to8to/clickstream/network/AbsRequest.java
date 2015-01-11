package com.to8to.clickstream.network;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public abstract class AbsRequest<T> implements Comparable<AbsRequest<T>>{
    private String url;
    private int timeOut ;
    private Map<String, String> header ;
    private int method ;
    private boolean error ;
    private T data ;
    private ClickResponse clickResponse ;
    private Integer mSequence;

    public interface Method {
        int GET = 0;
        int POST = 1;
    }

    public AbsRequest(String url, int timeOut, int method, ClickResponse clickResponse) {
        this.url = url;
        this.timeOut = timeOut;
        this.method = method;
        this.clickResponse = clickResponse;
        this.error = true ;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public Map<String, String> getHeader() {
        return Collections.emptyMap();
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public byte[] getBody(){
        Map<String,String> params = getParams() ;
        if(params != null&& params.size()> 0){
            return encodeParameters(params) ;
        }
        return null ;
    }

    private byte[] encodeParameters(Map<String,String> param){
        StringBuilder stringBuilder = new StringBuilder() ;
        try {
            for(String keySet: param.keySet()){
                stringBuilder.append(URLEncoder.encode(keySet,"UTF-8"))
                             .append("=")
                             .append(URLEncoder.encode(param.get(keySet), "UTF-8"))
                             .append("&");
            }
            return stringBuilder.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            new RuntimeException("Encoding not supported: "+e);
        }
        return null ;
    }

    public String getBodyContentType(){
        return "application/x-www-form-urlencoded; charset=UTF-8" ;
    }

    public void deliveryResponse(T data){
        if(clickResponse != null)
            clickResponse.onResponse(data);
    }

    public void deliveryError(){
        if(clickResponse != null)
            clickResponse.onErrorResponse();
    }
    public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }
    public final void setSequence(int sequence) {
        mSequence = sequence;
    }
    /**
     * Returns the {@link com.android.volley.Request.Priority} of this request; {@link com.android.volley.Request.Priority#NORMAL} by default.
     */
    public Priority getPriority() {
        return Priority.NORMAL;
    }
    @Override
    public int compareTo(AbsRequest<T> other) {
        Priority left = this.getPriority();
        Priority right = other.getPriority();

        // High-priority requests are "lesser" so they are sorted to the front.
        // Equal priorities are sorted by sequence number to provide FIFO ordering.
        return left == right ?
                this.mSequence - other.mSequence :
                right.ordinal() - left.ordinal();
    }

    public abstract Map<String,String> getParams() ;

    public abstract void parseResponse(NetworkBean networkBean) ;
}
