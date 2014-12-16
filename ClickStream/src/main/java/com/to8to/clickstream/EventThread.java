package com.to8to.clickstream;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moon.sdk.ormlite.entity.PutEvent;
import com.moon.sdk.ormlite.entity.UserEvent;
import com.to8to.clickstream.toolbox.CommonUtil;
import com.to8to.clickstream.toolbox.FileUtil;
import com.to8to.clickstream.toolbox.IPost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class EventThread extends HandlerThread implements Handler.Callback {

    private final int REQUEST_EVENT = 0 ;
    private final int INIT_EVENT = 1 ;
    private final int POST_EVENT = 2 ;
    private final int WRITE_EVENT = 3 ;
    private Handler dispatcherThread;
    private DataHandler dataHandler;
    private IPost mPost;
    private List<UserEvent> userEvents;
    private Gson gson;
    private Context context;

    public EventThread(Context context, DataHandler dataHandler, IPost post) {
        super("HandlerEventThread");
        this.dataHandler = dataHandler;
        mPost = post;
        this.context = context;
        userEvents = new ArrayList<>();
        gson = new Gson();

    }

    public void requestEventThread() {
        if (dispatcherThread == null) {
            dispatcherThread = new Handler(getLooper(), this);
        }
        dispatcherThread.sendEmptyMessage(REQUEST_EVENT);
    }

    public void postEventThread() {
        if (dispatcherThread == null) {
            dispatcherThread = new Handler(getLooper(), this);
        }
        if (CommonUtil.isNetworkAvailable(context))
            dispatcherThread.sendEmptyMessage(POST_EVENT);
    }

    public void initEventThread() {
        if (dispatcherThread == null) {
            dispatcherThread = new Handler(getLooper(), this);
        }

        dispatcherThread.sendEmptyMessage(INIT_EVENT);
    }

    private void insertEventThread() {
        if (dispatcherThread == null) {
            dispatcherThread = new Handler(getLooper(), this);
        }
        dispatcherThread.sendEmptyMessage(WRITE_EVENT);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            /*请求事件处理，把事件写入文件中*/
            case REQUEST_EVENT:
                dataHandler.getUserEventDao().insert(dataHandler.getUserEvent());
                long N = dataHandler.getUserEventDao().queryCount();
                if (N % IClickStream.defaultNum == 0) {
                    postEventThread();
                }
                break;
            /*请求网络处理，把事件发送到服务器*/
            case POST_EVENT:
                String context = FileUtil.readFile(ClickStream.filePath);
                PutEvent putEvent = gson.fromJson(context, PutEvent.class);
                if (putEvent == null) {
                    File file = new File(ClickStream.filePath);
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    /*重新发送*/
                    initEventThread();
                    postEventThread();
                    return false;
                }
                String userString = FileUtil.readFile(ClickStream.EVENT_Path);
                List<UserEvent> events = gson.fromJson(userString, new TypeToken<List<UserEvent>>() {
                }.getType());
                if (events == null) {
                    File file = new File(ClickStream.EVENT_Path);
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    events = new ArrayList<>();
                }
                events.addAll(dataHandler.getUserEventDao().queryAll());
                putEvent.setUserEvents(events);
                String json = gson.toJson(putEvent);
                mPost.postEvent(json, new ErrorRunnable(events), new SuccessRunnable());
                dataHandler.getUserEventDao().deleteAll();
                break;
            /*初始化数据*/
            case INIT_EVENT:
                initEvent() ;
                FileUtil.writeFile(gson.toJson(dataHandler.getPutEvent()), ClickStream.filePath);
                EventThread.this.userEvents.clear();
                break;
            case WRITE_EVENT:
                FileUtil.writeFile(gson.toJson(EventThread.this.userEvents), ClickStream.EVENT_Path);
                EventThread.this.userEvents.clear();
                break;
        }

        return false;
    }

    private void initEvent(){
        PutEvent putEvent = new PutEvent() ;
        putEvent.setUid(dataHandler.getUid());
        putEvent.setCookId("");
        putEvent.setSessionId("");
        putEvent.setUserLocation(CommonUtil.getLocation(context));
        putEvent.setIpAddress(CommonUtil.getIpAddress(context));
        putEvent.setDeviceType("手机");
        putEvent.setOsType("Android");
        putEvent.setOsVersion(CommonUtil.getSDKVersion());
        putEvent.setProductName(CommonUtil.getAppName(context));
        putEvent.setProductVersion(CommonUtil.getVersionName(context));
        putEvent.setUserAgent("");
        putEvent.setExplorerVersion("");
        putEvent.setSpType(CommonUtil.getServiceProvider(context) + "");
        putEvent.setNetworkType(CommonUtil.getNetworkType(context) + "");
        putEvent.setDeviceId(CommonUtil.getDeviceId(context));
        putEvent.setDisplaySolution(CommonUtil.getDisplaySolution(context));
        putEvent.setUserEvents(new ArrayList<UserEvent>());
        dataHandler.setPutEvent(putEvent);
    }

    private class ErrorRunnable implements Runnable {


        private ErrorRunnable(List<UserEvent> userEvents) {
            EventThread.this.userEvents.addAll(userEvents);
        }

        @Override
        public void run() {
            insertEventThread();
        }
    }

    private class SuccessRunnable implements Runnable {
        @Override
        public void run() {

            initEventThread();
        }
    }
}
