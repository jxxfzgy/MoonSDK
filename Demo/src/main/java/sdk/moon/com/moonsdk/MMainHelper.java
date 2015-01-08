package sdk.moon.com.moonsdk;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.custom.MDrawColorBitmap;
import sdk.moon.com.moonsdk.custom.MdynaDrawCircle;
import sdk.moon.com.moonsdk.entity.MActivityBean;
import sdk.moon.com.moonsdk.model.amap.MLocationActivity;
import sdk.moon.com.moonsdk.model.animviewpager.MAnimViewPagerActivity;
import sdk.moon.com.moonsdk.model.clickstream.MClickStreamActivity;
import sdk.moon.com.moonsdk.model.clickstream.MPostStreamActivity;
import sdk.moon.com.moonsdk.model.clipimage.MClipImageActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawBitmapActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawCircleActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawColorBitmapActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawColorCircleActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawDyncCircleActivity;
import sdk.moon.com.moonsdk.model.drawview.MDrawPathActivity;
import sdk.moon.com.moonsdk.model.gestureview.MGestureViewActivity;
import sdk.moon.com.moonsdk.model.imageloader.MImageLoadActivity;
import sdk.moon.com.moonsdk.model.litepal.MLitepalActivity;
import sdk.moon.com.moonsdk.model.loopviewpager.MLoopViewActivity;
import sdk.moon.com.moonsdk.model.ormlite.MContactActivity;
import sdk.moon.com.moonsdk.model.ormlite.MOrmliteActivity;
import sdk.moon.com.moonsdk.model.propertyanim.MPropertyActivity;
import sdk.moon.com.moonsdk.model.propertyanim.MTarjectoryActivity;
import sdk.moon.com.moonsdk.model.volley.MPostAvatar;
import sdk.moon.com.moonsdk.model.volley.MPostRActivity;
import sdk.moon.com.moonsdk.model.volley.MPostSActivity;

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
        List<MActivityBean> clickStreamList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> animViewPagerList = new ArrayList<MActivityBean>() ;
        List<MActivityBean> propertyAnim = new ArrayList<MActivityBean>() ;
        List<MActivityBean> drawView = new ArrayList<MActivityBean>() ;
        List<MActivityBean> litePal = new ArrayList<MActivityBean>() ;

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
        ormliteList.add(new MActivityBean().setFunctionName("通讯录").setActivityName(MContactActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("Ormlite数据库API").setSubBean(ormliteList));

        volleyList.add(new MActivityBean().setFunctionName("POST请求").setActivityName(MPostRActivity.class));
        volleyList.add(new MActivityBean().setFunctionName("POST发送").setActivityName(MPostSActivity.class));
        volleyList.add(new MActivityBean().setFunctionName("上传头像").setActivityName(MPostAvatar.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("网络请求API").setSubBean(volleyList));

        clickStreamList.add(new MActivityBean().setFunctionName("统计界面").setActivityName(MClickStreamActivity.class));
        clickStreamList.add(new MActivityBean().setFunctionName("提交界面").setActivityName(MPostStreamActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("点击流统计").setSubBean(clickStreamList));

        animViewPagerList.add(new MActivityBean().setFunctionName("切换动画").setActivityName(MAnimViewPagerActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("ViewPager切换动画").setSubBean(animViewPagerList));

        propertyAnim.add(new MActivityBean().setFunctionName("View 放大").setActivityName(MPropertyActivity.class));
        propertyAnim.add(new MActivityBean().setFunctionName("View 轨迹").setActivityName(MTarjectoryActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("属性动画").setSubBean(propertyAnim));

        drawView.add(new MActivityBean().setFunctionName("绘制三角形").setActivityName(MDrawPathActivity.class));
        drawView.add(new MActivityBean().setFunctionName("绘制圆环").setActivityName(MDrawCircleActivity.class));
        drawView.add(new MActivityBean().setFunctionName("动态圆环").setActivityName(MDrawDyncCircleActivity.class));
        drawView.add(new MActivityBean().setFunctionName("颜色圆球").setActivityName(MDrawColorCircleActivity.class));
        drawView.add(new MActivityBean().setFunctionName("画图片").setActivityName(MDrawBitmapActivity.class));
        drawView.add(new MActivityBean().setFunctionName("画颜色图片").setActivityName(MDrawColorBitmapActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("绘制View").setSubBean(drawView));

        litePal.add(new MActivityBean().setFunctionName("lite数据库").setActivityName(MLitepalActivity.class));
        mActivityBeans.add(new MActivityBean().setFunctionName("LitePal").setSubBean(litePal));

        return mActivityBeans ;
    }
}
