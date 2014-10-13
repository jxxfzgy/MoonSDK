package com.moon.sdk.imgloader;

import android.content.Context;

import com.moon.sdk.imgloader.intf.MIImageLoader;
import com.moon.sdk.imgloader.intf.MIImageLoaderFactory;

/**
 * Created by moon.zhong on 2014/10/11.
 * 真正获取核心加载类的实现
 */
public class MImageLoaderFactoryImpl implements MIImageLoaderFactory {
    /*核心加载类的实例化*/
    private MIImageLoader miImageLoader;
    /*实例化自己，让其他模块调用*/
    private static MIImageLoaderFactory miImageLoaderFactory = new MImageLoaderFactoryImpl() ;

    public static MIImageLoaderFactory getMiImageLoaderFactory(){
        return  miImageLoaderFactory ;
    }
    @Override
    public synchronized MIImageLoader getImageLoader(Context context) {
        if(miImageLoader == null){
            miImageLoader = new MImageLoaderImpl(context) ;
        }
        return miImageLoader;
    }
}
