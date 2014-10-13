package com.moon.sdk.imgloader.intf;

import android.content.Context;

/**
 * Created by moon.zhong on 2014/10/11.
 * 获取核心图片加载器
 */
public interface MIImageLoaderFactory {

    /**
     *  获取图片核心加载器
     * @return MIImageLoader
     */
    public abstract  MIImageLoader getImageLoader(Context context) ;
}
