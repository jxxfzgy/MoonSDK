package com.to8to.clickstream.network;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class ExeDelivery implements IDelivery {

    private final Executor executor ;

    public ExeDelivery(final Handler handler) {
        executor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command) ;
            }
        } ;
    }

    @Override
    public void onDelivery(AbsRequest<?> absRequest) {
        executor.execute(new ExeRunnable(absRequest,null));
    }

    @Override
    public void onDelivery(AbsRequest<?> absRequest, Runnable runnable) {
        executor.execute(new ExeRunnable(absRequest,runnable));
    }

    @Override
    public void onDeliveryError(AbsRequest<?> absRequest) {
        absRequest.setError(true);
        executor.execute(new ExeRunnable(absRequest,null));
    }

    private static class ExeRunnable implements Runnable{
        final Runnable runnable ;
        final AbsRequest absRequest ;
        private ExeRunnable( AbsRequest<?> absRequest,Runnable runnable) {
            this.runnable = runnable;
            this.absRequest = absRequest;
        }

        @Override
        public void run() {
            if(!absRequest.isError()){
                absRequest.deliveryResponse(absRequest.getData());
            }else {
                absRequest.deliveryError();
            }
            if(runnable != null)
                runnable.run();
        }
    }
}
