package com.to8to.clickstream.network;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class ClickQueue {
    private final static int DEFAULT_NETWORK_THREAD_SIZE = 1 ;
    private NetworkDispatch[] networkDispatches;
    private IDelivery mDelivery ;
    private INetwork mNetwork ;
    private PriorityBlockingQueue<AbsRequest> networkRequest = new PriorityBlockingQueue<>() ;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();

    public ClickQueue(INetwork mNetwork) {
        this(mNetwork,DEFAULT_NETWORK_THREAD_SIZE) ;
    }

    public ClickQueue(INetwork mNetwork,int poolSize){
        this.mNetwork = mNetwork;
        mDelivery = new ExeDelivery(new Handler(Looper.getMainLooper())) ;
        networkDispatches = new NetworkDispatch[poolSize] ;
    }

    public void start(){
        stop();
        for(int i = 0 ; i< networkDispatches.length; i++){
            NetworkDispatch networkDispatch = new NetworkDispatch(mDelivery,mNetwork,networkRequest) ;
            networkDispatches[i] = networkDispatch ;
            networkDispatch.start();
        }
    }
    public void stop() {
        for (int i = 0; i < networkDispatches.length; i++) {
            if (networkDispatches[i] != null) {
                networkDispatches[i].quit();
            }
        }
    }


    public void add(AbsRequest request){
        request.setSequence(getSequenceNumber());

        networkRequest.add(request) ;
    }

    public int getSequenceNumber() {
        return mSequenceGenerator.incrementAndGet();
    }
}
