package com.moon.sdk.imgloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.moon.sdk.imgloader.abst.MAImageLoader;
import com.moon.sdk.imgloader.intf.MIImageLoader;
import com.moon.sdk.imgloader.listerner.MILoadingLister;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;

/**
 * Created by moon.zhong on 2014/10/11.
 * 图片核心加载器 实现类
 * String imageUri = "http://site.com/image.png"; // from Web
 * String imageUri = "file:///mnt/sdCard/image.png"; // from SD card
 * String imageUri = "content://media/external/audio/albumart/1"; // from content provider
 * String imageUri = "assets://image.png"; // from assets
 * String imageUri = "drawable://" + R.drawable.img; // from drawables (non-9patch images)
 * NOTE: Use drawable:// only if you really need it! Always consider the native way to load drawables - ImageView.setImageResource(...) instead of using of ImageLoader.
 */
public class MImageLoaderImpl extends MAImageLoader implements MIImageLoader {


    public MImageLoaderImpl(Context context) {
        super(context);
    }

    @Override
    public void loadNormalImage( String url,ImageView imageView) {
        mImageLoader.displayImage(url,imageView,normalDisplayImageOptions);
    }

    @Override
    public void loadNormalImage(String url, ImageView imageView, MLoadType MLoadType) {
        loadNormalImage(getLoadType(url, MLoadType),imageView) ;
    }

    @Override
    public void loadNormalImage(String url, ImageView imageView, final MILoadingLister miLoadingLister) {
        mImageLoader.displayImage(url,imageView,normalDisplayImageOptions,new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                miLoadingLister.onImageLoadingStart(imageUri,view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                miLoadingLister.onImageLoadingFail(imageUri,view,failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                miLoadingLister.onImageLoadingComplete(imageUri,view,loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                miLoadingLister.onImageLoadingCancel(imageUri,view);
            }
        },new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                miLoadingLister.onImageLoadingProgress(imageUri,view,current,total);
            }
        });
    }

    @Override
    public void loadNormalImage(String url, ImageView imageView, MILoadingLister miLoadingLister, MLoadType MLoadType) {
        loadNormalImage(getLoadType(url, MLoadType),imageView,miLoadingLister);
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView) {
        mImageLoader.displayImage(url,imageView,circleDisplayImageOptions);
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView, final MILoadingLister miLoadingLister) {
        mImageLoader.displayImage(url,imageView,circleDisplayImageOptions,new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                miLoadingLister.onImageLoadingStart(imageUri,view);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                miLoadingLister.onImageLoadingFail(imageUri,view,failReason);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                miLoadingLister.onImageLoadingComplete(imageUri,view,loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                miLoadingLister.onImageLoadingCancel(imageUri,view);
            }
        },new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                miLoadingLister.onImageLoadingProgress(imageUri,view,current,total);
            }
        });
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView, MLoadType MLoadType) {
        loadCircleImage(getLoadType(url, MLoadType),imageView);
    }

    @Override
    public void loadCircleImage(String url, ImageView imageView, MILoadingLister miLoadingLister, MLoadType MLoadType) {
        loadCircleImage(getLoadType(url, MLoadType),imageView,miLoadingLister);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int angle) {
        if(angle == MIImageLoader.NORMAL_IMG){
            loadNormalImage(url,imageView);
        }else if(angle == MIImageLoader.CIRCLE_IMG){
            loadCircleImage(url, imageView);
        }else{
            loadAngleImage(url,imageView,angle);
        }
    }

    @Override
    public void loadImage(String url, ImageView imageView, int angle, MILoadingLister miLoadingLister) {
        if(angle == MIImageLoader.NORMAL_IMG){
            loadNormalImage(url,imageView,miLoadingLister);
        }else if(angle == MIImageLoader.CIRCLE_IMG){
            loadCircleImage(url, imageView,miLoadingLister);
        }else{
            loadAngleImage(url,imageView,angle,miLoadingLister);
        }
    }

    @Override
    public void loadImage(String url, ImageView imageView, int angle, MLoadType MLoadType) {
        loadImage(getLoadType(url, MLoadType),imageView,angle);
    }

    @Override
    public void loadImage(String url, ImageView imageView, int angle, MILoadingLister miLoadingLister, MLoadType MLoadType) {
        loadImage(getLoadType(url, MLoadType),imageView,angle,miLoadingLister);
    }

    @Override
    public File getImageFile(String url) {
        return mImageLoader.getDiskCache().get(url);
    }

    /**
     * 加载弧形图片，此方法被私有化，
     * 只能是本类调用。
     * @param url
     * @param imageView
     * @param angle
     */
    private void loadAngleImage(String url, ImageView imageView, int angle){

    }
    /**
     * 加载弧形图片，此方法被私有化，
     * 只能是本类调用。
     * @param url
     * @param imageView
     * @param angle
     * @param miLoadingLister
     */
    private void loadAngleImage(String url, ImageView imageView, int angle,MILoadingLister miLoadingLister){

    }
}
