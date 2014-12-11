package sdk.moon.com.moonsdk.core.intf;

import android.content.Context;

import com.moon.amap.sdk.intf.MIAMapFactory;
import com.moon.sdk.imgloader.intf.MIImageLoaderFactory;
import com.to8to.clickstream.IClickStream;
import com.to8to.clickstream.IEvent;

/**
 * Created by moon.zhong on 2014/10/11.
 * 调用SDK工厂接口
 */
public interface MIFactory {
    /*获取高德地图工厂类接口*/
    public MIAMapFactory getMIAMapFactory() ;

    /*获取图片加载类工厂接口*/
    public MIImageLoaderFactory getMIImageLoaderFactory() ;

    /*获取点击流工厂接口*/
    public IClickStream getIClickStreamFactory() ;
}
