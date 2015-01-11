package sdk.moon.com.moonsdk.application;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.moon.volley.network.MRequestQueue;

import org.litepal.LitePalApplication;

/**
 * Created by moon.zhong on 2014/10/15.
 */
public class MApplication extends LitePalApplication {
    /*全局上下文*/
    private static Context context ;
    /*全局调试模式*/
    private static boolean DEBUG = true ;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext() ;
        initGlobal() ;
    }

    public static Context getContext(){
        return context ;
    }

    public boolean getDebug(){
        return DEBUG ;
    }

    private void initGlobal(){
        RequestQueue requestQueue = Volley.newRequestQueue(context) ;
        MRequestQueue.setRequestQueue(requestQueue);

    }
}
