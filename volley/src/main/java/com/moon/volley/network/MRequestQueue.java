package com.moon.volley.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class MRequestQueue {
    private static RequestQueue requestQueue;

    public static void setRequestQueue(RequestQueue requestQueue) {
        stop();
        MRequestQueue.requestQueue = requestQueue;
        MRequestQueue.requestQueue.start();
    }

    public static void stop() {
        if (MRequestQueue.requestQueue != null) {
            requestQueue.stop();
            requestQueue = null;
        }
    }

    public static void add(Request request){
        MRequestQueue.requestQueue.add(request) ;
    }
}
