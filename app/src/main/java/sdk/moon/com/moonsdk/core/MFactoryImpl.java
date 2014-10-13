package sdk.moon.com.moonsdk.core;

import com.moon.amap.sdk.MAMapFactoryImpl;
import com.moon.amap.sdk.intf.MIAMapFactory;
import com.moon.sdk.imgloader.MImageLoaderFactoryImpl;
import com.moon.sdk.imgloader.intf.MIImageLoaderFactory;

import sdk.moon.com.moonsdk.core.intf.MIFactory;

/**
 * Created by moon.zhong on 2014/10/11.
 * 获取SDK工厂实现类
 */
public class MFactoryImpl implements MIFactory {
    /*核心工厂实现类*/
    private static MIFactory miFactory = new MFactoryImpl() ;

    /**
     * 获取核心工厂的实现
     * @return m1Factory
     */
    public static MIFactory getInstance(){
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
}
