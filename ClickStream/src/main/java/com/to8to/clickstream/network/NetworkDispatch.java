package com.to8to.clickstream.network;

import android.os.Process;

import java.util.concurrent.BlockingQueue;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class NetworkDispatch extends Thread{

    private IDelivery mDelivery ;
    private INetwork mNetwork ;
    private BlockingQueue<AbsRequest> queue ;
    private boolean quit = false ;

    public NetworkDispatch(IDelivery mDelivery, INetwork mNetwork, BlockingQueue<AbsRequest> queue) {
        this.mDelivery = mDelivery;
        this.mNetwork = mNetwork;
        this.queue = queue;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        while (true){
            AbsRequest request ;
            try {
                request = queue.take() ;
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            try {
                NetworkBean networkBean = mNetwork.performRequest(request) ;
                request.parseResponse(networkBean);
                mDelivery.onDelivery(request);
            } catch (Exception e) {
                e.printStackTrace();
                mDelivery.onDeliveryError(request);
            }
        }
    }

    public void quit(){
        interrupt();
    }
}
