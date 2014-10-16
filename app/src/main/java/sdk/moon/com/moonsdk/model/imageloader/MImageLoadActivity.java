package sdk.moon.com.moonsdk.model.imageloader;

import android.os.Bundle;
import android.widget.ImageView;

import com.moon.sdk.imgloader.LoadType;
import com.moon.sdk.imgloader.intf.MIImageLoader;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.R;

/**
 * Created by Administrator on 2014/10/12 0012.
 */
public class MImageLoadActivity extends MBaseActivity {

    //测试地址 http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg
    /*图片加载器引用*/
    private MIImageLoader miImageLoader ;
    /*圆图图片显示组件*/
    private ImageView showCircleImage ;
    /*普通图片显示组件*/
    private ImageView showNormalImage ;



    private String resUrl = R.drawable.test+"" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loadimage);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        miImageLoader = gIFactory.getMIImageLoaderFactory().getImageLoader(gContext) ;
    }

    @Override
    public void initView() {
        showNormalImage = findView(R.id.showNormalImage) ;
        showCircleImage = findView(R.id.showCircleImage) ;
        miImageLoader.loadImage(resUrl ,showCircleImage,MIImageLoader.CIRCLE_IMG, LoadType.LOADING_RES);
        miImageLoader.loadImage(resUrl, showNormalImage, MIImageLoader.NORMAL_IMG, LoadType.LOADING_RES);
    }
}
