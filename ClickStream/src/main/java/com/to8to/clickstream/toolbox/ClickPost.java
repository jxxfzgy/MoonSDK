package com.to8to.clickstream.toolbox;

import android.os.Handler;
import android.util.Log;

import com.to8to.clickstream.api.PostApi;
import com.to8to.clickstream.entity.Data;
import com.to8to.clickstream.network.ClickResponse;

import java.util.concurrent.Executor;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class ClickPost implements IPost {

    private Executor executor ;
    public ClickPost(final Handler handler) {
        executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command) ;
            }
        } ;
    }

    @Override
    public void postEvent(String event) {
        postEvent(event, null);
    }

    @Override
    public void postEvent(String event, Runnable runnableError) {
        postEvent(event,runnableError,null) ;
    }

    @Override
    public void postEvent(String event, Runnable runnableError, Runnable runnableSuccess) {
        executor.execute(new PostRunnable(runnableError,runnableSuccess,event));
    }

    private static class PostRunnable implements Runnable {
        final Runnable runnableError,runnableSuccess ;
        final String data ;

        private PostRunnable(Runnable runnableError, Runnable runnableSuccess, String data) {
            this.runnableError = runnableError;
            this.runnableSuccess = runnableSuccess;
            this.data = data;
        }

        @Override
        public void run() {
            PostApi.postData(data, new ClickResponse<Data<String>>() {
                @Override
                public void onResponse(Data data) {
                    Log.v("zgy", "==========Data=============" + data.getData());
                    if (runnableSuccess != null) {
                        runnableSuccess.run();
                    }
                }

                @Override
                public void onErrorResponse() {
                    Log.v("zgy", "==========onErrorResponse=============");
                    if (runnableError != null) {
                        runnableError.run();
                    }
                }
            });
        }
    }
}
