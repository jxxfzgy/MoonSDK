package sdk.moon.com.moonsdk;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.entity.MActivityBean;
import sdk.moon.com.moonsdk.model.amap.MLocationActivity;
import sdk.moon.com.moonsdk.model.clipimage.MClipImageActivity;
import sdk.moon.com.moonsdk.model.gestureview.MGestureViewActivity;
import sdk.moon.com.moonsdk.model.imageloader.MImageLoadActivity;
import sdk.moon.com.moonsdk.model.loopviewpager.MLoopViewActivity;
import sdk.moon.com.moonsdk.model.ormlite.MContactActivity;
import sdk.moon.com.moonsdk.model.ormlite.MOrmliteActivity;
import sdk.moon.com.moonsdk.model.volley.MVolleyActivity;

/**
 * Created by moon.zhong on 2014/10/21.
 * 主activity的帮助类，
 * 数据加载
 */
public class MMainHelper {

    public List<MActivityBean> getData(){
        List<MActivityBean> mActivityBeans = new ArrayList<MActivityBean>() ;
        List<MActivityBean> gaodeList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> tupianJiazaiList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> tupianShoushiList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> tupianCaijianList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> loopViewpagerlist = new ArrayList<MActivityBean>() ;
        List<MActivityBean> ormliteList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> volleyList = new ArrayList<MActivityBean>() ;

        gaodeList.add(new MActivityBean().setFunctionName("定位服务").setActivityName(MLocationActivity.class)) ;
        mActivityBeans.add(new MActivityBean().setFunctionName("高德地图API").setSubBean(gaodeList));

        tupianJiazaiList.add(new MActivityBean().setFunctionName("普通图片").setActivityName(MImageLoadActivity.class));
        tupianJiazaiList.add(new MActivityBean().setFunctionName("圆形图片").setActivityName(MImageLoadActivity.class));
        tupianJiazaiList.add(new MActivityBean().setFunctionName("弧形图片").setActivityName(MImageLoadActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("图片加载API").setSubBean(tupianJiazaiList));

        tupianShoushiList.add(new MActivityBean().setFunctionName("手势放缩").setActivityName(MGestureViewActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("图片手势放缩API").setSubBean(tupianShoushiList));

        tupianCaijianList.add(new MActivityBean().setFunctionName("图片裁剪").setActivityName(MClipImageActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("图片裁剪API").setSubBean(tupianCaijianList));

        loopViewpagerlist.add(new MActivityBean().setFunctionName("无限循环").setActivityName(MLoopViewActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("循环ViewPagerAPI").setSubBean(loopViewpagerlist));

        ormliteList.add(new MActivityBean().setFunctionName("增加数据").setActivityName(MOrmliteActivity.class));
        ormliteList.add(new MActivityBean().setFunctionName("删除数据").setActivityName(MOrmliteActivity.class));
        ormliteList.add(new MActivityBean().setFunctionName("修改数据").setActivityName(MOrmliteActivity.class));
        ormliteList.add(new MActivityBean().setFunctionName("查询数据").setActivityName(MOrmliteActivity.class));
        ormliteList.add(new MActivityBean().setFunctionName("通讯录").setActivityName(MContactActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("Ormlite数据库API").setSubBean(ormliteList));

        volleyList.add(new MActivityBean().setFunctionName("POST请求").setActivityName(MVolleyActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("网络请求API").setSubBean(volleyList));



        return mActivityBeans ;
    }
}
