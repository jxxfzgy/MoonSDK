package com.moon.sdk.imgloader.listerner;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;

/**
 * Created by moon on 2014/10/12 0012.
 * 图片加载监听借口
 */
public interface MILoadingLister {

    /**
     * 开始加载图片
     * @param imageUri
     * @param view
     */
    public void onImageLoadingStart(String imageUri, View view) ;

    /**
     * 取消图片的加载
     * @param imageUri
     * @param view
     */
    public void onImageLoadingCancel(String imageUri, View view) ;

    /**
     * 图片加载完成
     * @param imageUri
     * @param view
     * @param loadedImage
     */
    public void onImageLoadingComplete(String imageUri, View view, Bitmap loadedImage) ;

    /**
     * 图片加载失败
     * @param imageUri
     * @param view
     * @param failReason
     */
    public void onImageLoadingFail(String imageUri, View view, FailReason failReason) ;

    /**
     * 图片加载进度接口
     * @param imageUri
     * @param view
     * @param current
     * @param total
     */
    public void onImageLoadingProgress(String imageUri, View view, int current, int total) ;
}
