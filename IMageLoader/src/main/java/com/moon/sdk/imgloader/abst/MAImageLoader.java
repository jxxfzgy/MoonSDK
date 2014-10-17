package com.moon.sdk.imgloader.abst;

import android.content.Context;
import android.graphics.Bitmap;

import com.moon.sdk.imgloader.MLoadType;
import com.moon.sdk.imgloader.R;
import com.moon.sdk.imgloader.displayer.MCircleDisplay;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by moon.zhong on 2014/10/11.
 * 图片加载配置信息
 */
public abstract class MAImageLoader {
    /*加普通图片*/
    public DisplayImageOptions normalDisplayImageOptions ;
    /*加载圆形图片*/
    public DisplayImageOptions circleDisplayImageOptions ;
    /*加载图片配置信息*/
    public ImageLoaderConfiguration imageLoaderConfiguration ;
    /*图片加载器*/
    public ImageLoader mImageLoader ;

    protected MAImageLoader(Context context) {
        normalDisplayImageOptions = getNormalDisplayImageOptions() ;
        circleDisplayImageOptions = getCircleDisplayImageOptions() ;
        imageLoaderConfiguration = getImageLoaderConfig(context);
        mImageLoader = ImageLoader.getInstance() ;
        mImageLoader.init(imageLoaderConfiguration);
    }

    /**
     * 从本地加载
     * @param url
     * @return
     */
    public String getLocalPath(String url){
        return "file://"+ url ;
    }

    /**
     *从资源文件中加载
     * @param url
     * @return
     */
    public String getResPath(String url){
        return "drawable://"+ url ;
    }

    /**
     *从数据库中加载
     * @param url
     * @return
     */
    public String getProviderPath(String url){
        return "content://"+ url ;
    }

    /**
     *从assets中加载
     * @param url
     * @return
     */
    public String getAssetsPath(String url){
        return "assets://"+ url ;
    }


    /**
     *  转换加载地址
     * @param url
     * @param MLoadType
     * @return
     */
    public String getLoadType(String url, MLoadType MLoadType) {
        if(MLoadType == MLoadType.LOADING_NET){

        }else if(MLoadType == MLoadType.LOADING_LOCAL){
            url = getLocalPath(url) ;
        }else if(MLoadType == MLoadType.LOADING_RES){
            url = getResPath(url) ;
        }else if(MLoadType == MLoadType.LOADING_ASSETS){
            url = getAssetsPath(url) ;
        }else if(MLoadType == MLoadType.LOADING_PROVIDER){
            url = getProviderPath(url) ;
        }
        return url;
    }

    /**
     *  设置图片加载器配置信息
     * @param context
     * @return
     */
    private ImageLoaderConfiguration getImageLoaderConfig(Context context) {
        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(context)) // default
                .imageDecoder(new BaseImageDecoder(true)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        return config;
    }

    /**
     * 普通加载图片的显示配置
     * @return
     */
    public DisplayImageOptions getNormalDisplayImageOptions(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否启用内存缓存？
                .cacheOnDisk(true) //是否启用内存卡缓存？
                .bitmapConfig(Bitmap.Config.ARGB_8888) //设置图像像素
//                .showImageOnLoading(R.drawable.ic_launcher)
//                .showImageOnFail(R.drawable.ic_launcher)
//                .showImageForEmptyUri(R.drawable.ic_launcher)
//                .displayer() //加载不同类型的图片，传入不同类 ，不填，表示正常图片。
                .build() ;
        return displayImageOptions ;
    }

    /**
     *  圆形图片的显示配置
     * @return
     */
    public DisplayImageOptions getCircleDisplayImageOptions(){
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否启用内存缓存？
                .cacheOnDisk(true) //是否启用内存卡缓存？
                .bitmapConfig(Bitmap.Config.ARGB_8888) //设置图像像素
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .displayer(new MCircleDisplay()) //加载不同类型的图片，传入不同类 ，MCircleDisplay，表示圆形图片。
                .build() ;
        return displayImageOptions ;
    }
}
