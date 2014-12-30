package sdk.moon.com.moonsdk.model.propertyanim;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.autonavi.amap.mapcore2d.FPoint;

/**
 * Created by moon.zhong on 2014/12/30.
 */
public class MPropertyAnim {
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void scaleBig(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotationX", 0.0f, 180f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();

    }

    public void value(final Object o, final Rect rect) {
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat()
    }

    public void animHolder(final Object o, final Rect rect, float x, float y) {
        float startX = ((MViewWrapper) o).getX();
        float startY = ((MViewWrapper) o).getY();

        PropertyValuesHolder propertyValuesHolderScaleX = PropertyValuesHolder.ofInt("height", rect.bottom, 200);
        PropertyValuesHolder propertyValuesHolderScaleY = PropertyValuesHolder.ofInt("width", rect.right, 200);
        PropertyValuesHolder propertyValuesHolderX = PropertyValuesHolder.ofFloat("translationY", 450.0f);
        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("translationX", -200.0f);
//        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("y", y, 10.0f) ;
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(o, propertyValuesHolderX, propertyValuesHolderY, propertyValuesHolderScaleX, propertyValuesHolderScaleY);
        objectAnimator.setDuration(1500);
        ((MViewWrapper) o).setScaleType(ImageView.ScaleType.CENTER_CROP);
        objectAnimator.start();

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animHolder1(o, rect);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void animHolder1(Object o, Rect rect) {

        float tranX = (rect.right - 200);
        float tranY = (rect.bottom - 200);
        PropertyValuesHolder propertyValuesHolderScaleX = PropertyValuesHolder.ofInt("height", 200, rect.bottom);
        PropertyValuesHolder propertyValuesHolderScaleY = PropertyValuesHolder.ofInt("width", 200, rect.right);
        PropertyValuesHolder propertyValuesHolderX = PropertyValuesHolder.ofFloat("translationY", 0);
        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("translationX", 0);
//        PropertyValuesHolder propertyValuesHolderY = PropertyValuesHolder.ofFloat("y", y, 10.0f) ;
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(o, propertyValuesHolderX, propertyValuesHolderY, propertyValuesHolderScaleX, propertyValuesHolderScaleY);
        objectAnimator.setDuration(1500);
        objectAnimator.start();

    }

    public void valueAnim(final Object object) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 500);
        valueAnimator.setTarget(object);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ((View) object).getLayoutParams().height = height;
                ((View) object).requestLayout();

            }
        });
    }

    public void test(Button btn2) {
        ObjectAnimator oa = ObjectAnimator.ofInt(btn2, "width", 400, 200, 400, 100, 500);
        oa.setDuration(2000);
        oa.start();
    }

    public void customType() {
        int temp = 0;
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new FPoint(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<FPoint>() {
            @Override
            public FPoint evaluate(float fraction, FPoint startValue, FPoint endValue) {
                Log.v("zgy", "=========fraction=========" + fraction);
                FPoint fPoint = new FPoint(fraction * 2, fraction * 3);
                return fPoint;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                FPoint fPoint = (FPoint) animation.getAnimatedValue();
//                Log.v("zgy","=========fPoint======"+fPoint.x) ;
//                Log.v("zgy","=========fPoint======"+fPoint.y) ;
            }
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    public void trajectoryAnim(final Object object) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, 0.5f, 1.0f);
        valueAnimator.setTarget(object);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((View) object).setAlpha((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();

    }

    public void trajectoryAnim2(final Object object) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 300, 300);
        valueAnimator.setTarget(object);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ((View) object).setX((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();

    }

    public void trajectoryAnim1(final Object object) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setTarget(object);
        valueAnimator.setDuration(2000);
        valueAnimator.setObjectValues(new PointF(0.0F, 0.0F), new PointF(600.0F, 800F));
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
                float x = startValue.x + (fraction * (endValue.x - startValue.x));
                float y = startValue.y + (fraction * (endValue.y - startValue.y));
                return new PointF(x, y);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                ((View) object).setX(pointF.x);
                ((View) object).setY(pointF.y);
            }
        });
        valueAnimator.start();

    }
}
