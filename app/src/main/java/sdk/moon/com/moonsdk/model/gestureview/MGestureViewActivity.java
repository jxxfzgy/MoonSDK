package sdk.moon.com.moonsdk.model.gestureview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.moon.sdk.imgloader.intf.MIImageLoader;
import com.moon.sdk.imgloader.listerner.MSimpleLister;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.polites.android.GestureImageView;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.R;

/**
 * Created by Administrator on 2014/10/12 0012.
 */
public class MGestureViewActivity extends MBaseActivity {
    /*手势放缩图片组件*/
    private GestureImageView gestureImageView ;
    /*图片加载器*/
    private MIImageLoader miImageLoader ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gesture_imageview);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        miImageLoader = gIFactory.getMIImageLoaderFactory().getImageLoader(gContext) ;

    }

    @Override
    public void initView() {
        gestureImageView = findView(R.id.showGestureImage) ;
        miImageLoader.loadNormalImage(imgUrl,gestureImageView,new MSimpleLister(){
            @Override
            public void onImageLoadingStart(String imageUri, View view) {
                super.onImageLoadingStart(imageUri, view);
            }

            @Override
            public void onImageLoadingProgress(String imageUri, View view, int current, int total) {
                super.onImageLoadingProgress(imageUri, view, current, total);
            }

            @Override
            public void onImageLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onImageLoadingComplete(imageUri, view, loadedImage);
            }

            @Override
            public void onImageLoadingFail(String imageUri, View view, FailReason failReason) {
                super.onImageLoadingFail(imageUri, view, failReason);
            }
        });
    }
}
