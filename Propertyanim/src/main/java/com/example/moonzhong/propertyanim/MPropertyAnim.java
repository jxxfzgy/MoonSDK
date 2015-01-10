package com.example.moonzhong.propertyanim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by moon.zhong on 2014/12/30.
 */
public class MPropertyAnim {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void scaleBig(View view ){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "rotationX", 0, 360) ;
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }
}
