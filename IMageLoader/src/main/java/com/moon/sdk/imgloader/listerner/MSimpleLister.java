package com.moon.sdk.imgloader.listerner;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;

/**
 * Created by moon on 2014/10/12 0012.
 */
public class MSimpleLister implements MILoadingLister {

    @Override
    public void onImageLoadingStart(String imageUri, View view) {

    }

    @Override
    public void onImageLoadingCancel(String imageUri, View view) {

    }

    @Override
    public void onImageLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

    }

    @Override
    public void onImageLoadingFail(String imageUri, View view, FailReason failReason) {

    }

    @Override
    public void onImageLoadingProgress(String imageUri, View view, int current, int total) {

    }
}
