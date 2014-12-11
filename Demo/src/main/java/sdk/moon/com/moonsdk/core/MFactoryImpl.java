package sdk.moon.com.moonsdk.core;

import android.content.Context;

import com.moon.amap.sdk.MAMapFactoryImpl;
import com.moon.amap.sdk.intf.MIAMapFactory;
import com.moon.sdk.imgloader.MImageLoaderFactoryImpl;
import com.moon.sdk.imgloader.intf.MIImageLoaderFactory;
import com.to8to.clickstream.ClickStream;
import com.to8to.clickstream.IClickStream;
import com.to8to.clickstream.IEvent;

import sdk.moon.com.moonsdk.core.intf.MIFactory;

/**
 * Created by moon.zhong on 2014/10/11.
 * 获取SDK工厂实现类
 */
public class MFactoryImpl implements MIFactory {
    /*核心工厂实现类*/
    private static MIFactory miFactory  ;
    private IClickStream iClickStream ;

    private MFactoryImpl(Context context) {
        iClickStream = ClickStream.newInstance(context) ;
        iClickStream.setUid("1111111");
        registerClickStream() ;
    }

    /**
     * 获取核心工厂的实现
     * @return m1Factory
     */
    public static MIFactory getInstance(Context context){
        if(miFactory == null){
            miFactory = new MFactoryImpl(context) ;
        }
        return miFactory ;
    }
    /*获取高德地图工厂实现方法*/
    @Override
    public MIAMapFactory getMIAMapFactory() {
        return MAMapFactoryImpl.getMaMapFactoryImpl();
    }

    /*获取图片加载器工厂实现方法*/
    @Override
    public MIImageLoaderFactory getMIImageLoaderFactory() {
        return MImageLoaderFactoryImpl.getMiImageLoaderFactory();
    }

    @Override
    public IClickStream getIClickStreamFactory() {
        return iClickStream;
    }

    private void registerClickStream(){
        iClickStream.registerEvent("MMainActivity","MMainActivity","主界面");
        iClickStream.registerEvent("MClickStreamActivity","MClickStreamActivity","统计界面");
        iClickStream.registerEvent("MPostStreamActivity","MPostStreamActivity","发送数据界面");
    }
}
