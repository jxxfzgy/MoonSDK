package com.to8to.clickstream.toolbox;

import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.moon.volley.api.ClickApi;
import com.moon.volley.entity.MClickBean;
import com.moon.volley.network.MResponse;

import java.util.concurrent.Executor;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class PostImpl implements IPost{

    private Executor executor ;

    public PostImpl(final Handler handler) {
        executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command) ;
            }
        } ;
    }

    @Override
    public void postEvent(String event) {
        executor.execute(new ExecRunnable(null,event));
        postEvent(event,null) ;
    }

    @Override
    public void postEvent(String event, Runnable runnable) {
        executor.execute(new ExecRunnable(runnable,event));
        postEvent(event,runnable,null) ;
    }

    @Override
    public void postEvent(String event, final Runnable runnableError, final Runnable runnableSuccess) {
        executor.execute(new ExecRunnable(runnableError,runnableSuccess,event));
//        ClickApi.postParamClickStream(event,new MResponse<MClickBean>() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if(runnableError != null)
//                    runnableError.run();
//            }
//
//            @Override
//            public void onResponse(MClickBean response) {
//                if(runnableSuccess != null)
//                    runnableSuccess.run();
//            }
//        });
    }

    private static class ExecRunnable implements Runnable{

        private Runnable runnableError ;
        private Runnable runnableSuccess ;
        private String event ;

        private ExecRunnable(Runnable runnableError, String event) {
            this.runnableError = runnableError;
            this.event = event;
        }
        private ExecRunnable(Runnable runnableError,Runnable runnableSuccess, String event) {
            this.runnableError = runnableError;
            this.runnableSuccess = runnableSuccess;
            this.event = event;
        }

        @Override
        public void run() {

            ClickApi.postDataClickStream(event,new MResponse<MClickBean>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(runnableError != null)
                        runnableError.run();
                }

                @Override
                public void onResponse(MClickBean response) {
                    if(runnableSuccess != null)
                        runnableSuccess.run();
                }
            });

        }
    }
}
