package com.moon.volley.network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;

import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public abstract class MRequest<T> extends Request<T> {
    private MResponse<T> mResponse ;
    public static final int TIMEOUT_MS = 20000 ;
    private Map<String,String> params ;
    public MRequest(int method, String url, Map<String, String> params, MResponse<T> listener) {
        super(method, url, listener);
        this.params = params;
        mResponse = listener ;
        setRetryPolicy(new DefaultRetryPolicy(MRequest.TIMEOUT_MS,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)) ;
        setShouldCache(false) ;
    }

    @Override
    protected void deliverResponse(T response) {
        mResponse.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
