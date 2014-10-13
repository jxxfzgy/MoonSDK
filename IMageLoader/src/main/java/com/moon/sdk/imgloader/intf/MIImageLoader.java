package com.moon.sdk.imgloader.intf;

import android.widget.ImageView;

import com.moon.sdk.imgloader.LoadType;
import com.moon.sdk.imgloader.listerner.MILoadingLister;

import java.io.File;

/**
 * Created by moon.zhong on 2014/10/11.
 * 图片核心加载器 接口类
 */
public interface MIImageLoader {

    /*加载普通图片*/
    public static final int NORMAL_IMG = 0;
    /*加载圆形图片参数*/
    public static final int CIRCLE_IMG = 360;


    /**
     * 加载普通图片，没有做任何处理
     *
     * @param imageView
     * @param url
     */
    public void loadNormalImage(String url, ImageView imageView);

    /**
     * 加载普通图片，增加选择从哪个地方加载的参数
     * @param url
     * @param imageView
     * @param loadType
     */
    public void loadNormalImage(String url, ImageView imageView,LoadType loadType);

    /**
     * 加载普通图片，带回调
     *
     * @param imageView
     * @param url
     * @param miLoadingLister
     */
    public void loadNormalImage(String url, ImageView imageView ,MILoadingLister miLoadingLister);

    /**
     * 加载普通图片，带回调,增加选择从哪个地方加载的参数
     *
     * @param imageView
     * @param url
     * @param miLoadingLister
     * @param loadType
     */
    public void loadNormalImage(String url, ImageView imageView ,MILoadingLister miLoadingLister,LoadType loadType);

    /**
     * 加载圆形图片
     * @param url
     * @param imageView
     *
     */
    public void loadCircleImage(String url, ImageView imageView);

    /**
     * 加载圆形图片，带回调
     * @param url
     * @param imageView
     * @param miLoadingLister
     */
    public void loadCircleImage(String url, ImageView imageView ,MILoadingLister miLoadingLister);

    /**
     * 加载圆形图片,增加选择从哪个地方加载的参数
     * @param url
     * @param imageView
     *@param loadType
     */
    public void loadCircleImage(String url, ImageView imageView,LoadType loadType);

    /**
     * 加载圆形图片，带回调,增加选择从哪个地方加载的参数
     * @param url
     * @param imageView
     * @param miLoadingLister
     * @param loadType
     */
    public void loadCircleImage(String url, ImageView imageView ,MILoadingLister miLoadingLister,LoadType loadType);

    /**
     * 加载网络图片，带角度参数
     * @param url
     * @param imageView
     * @param angle
     */
    public void loadImage(String url, ImageView imageView ,int angle );

    /**
     * 加载网络图片，带角度参数，带回调
     * @param url
     * @param imageView
     * @param angle
     * @param miLoadingLister
     */
    public void loadImage(String url, ImageView imageView ,int angle ,MILoadingLister miLoadingLister );

    /**
     * 加载网络图片，带角度参数,增加选择从哪个地方加载的参数
     * @param url
     * @param imageView
     * @param angle
     * @param loadType
     */
    public void loadImage(String url, ImageView imageView ,int angle ,LoadType loadType);

    /**
     * 加载网络图片，带角度参数，带回调,增加选择从哪个地方加载的参数
     * @param url
     * @param imageView
     * @param angle
     * @param miLoadingLister
     * @param loadType
     */
    public void loadImage(String url, ImageView imageView ,int angle ,MILoadingLister miLoadingLister ,LoadType loadType);


    /**
     * 获取缓存在本地的图片文件
     *
     * @param url
     * @return
     */
    public File getImageFile(String url);

}
