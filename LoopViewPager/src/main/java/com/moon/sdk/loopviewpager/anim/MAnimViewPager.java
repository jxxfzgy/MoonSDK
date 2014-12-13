package com.moon.sdk.loopviewpager.anim;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


//import com.nineoldandroids.view.ViewHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/12.
 */
public class MAnimViewPager extends ViewPager {
    /*用LinkedHashMap遍历更快*/

    private Map<Integer, Object> viewContainer = new LinkedHashMap<Integer, Object>();

    private State mState = State.IDLE;
    private int mLastPage;
    private static final float SCALE_MAX = 0.5f;
    private static final float ZOOM_MAX = 0.7f;
    private float mScale ;

    private enum State {
        IDLE,
        GOING_LEFT,
        GOING_RIGHT
    }

    public MAnimViewPager(Context context) {
        super(context);
    }

    public MAnimViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        initAnim();
    }

    private void initAnim() {
        Log.v("zgy","===========getCurrentItem()===="+getCurrentItem()) ;
        View view = findContainItem(getCurrentItem()+1) ;
        Log.v("zgy","===========getCurrentItem()===="+view) ;
//        View.ALPHA.set(view,1.0f);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        Object o = viewContainer.get(item);
        View view = (View)o ;
        Log.v("zgy","===========getCurrentItem()===="+view) ;
//        ViewHelper.setPivotX(view, view.getMeasuredWidth()*0.5f);
//        ViewHelper.setPivotY(view, view.getMeasuredHeight()*0.5f);
//        ViewHelper.setScaleX(view, 1);
//        ViewHelper.setScaleY(view, 1);

    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        if (mState == State.IDLE && Math.abs(offset) > 0) {
            mLastPage = getCurrentItem();
            mState = mLastPage == position ? State.GOING_RIGHT : State.GOING_LEFT;
        }
//        Log.v("zgy", "=======mState========" + mState);
        float positionOffSet = isSmall(offset) ? 0 : offset;
        View lastView = findContainItem(position);
        View currentView = findContainItem(position + 1);
        animateZoom(lastView, currentView, positionOffSet, true);
        super.onPageScrolled(position, offset, offsetPixels);
        if(positionOffSet == 0){
            mState = State.IDLE ;
        }
    }

    private boolean isSmall(float positionOffSet) {
        return Math.abs(positionOffSet) <= 0.0001;
    }

    public void addViewContainer(int position, Object o) {
//        ViewHelper.setScaleX((View)o, ZOOM_MAX);
//        ViewHelper.setScaleY((View)o, ZOOM_MAX);
        View.SCALE_X.set((View)o,ZOOM_MAX);
        View.SCALE_Y.set((View)o,ZOOM_MAX);
        viewContainer.put(Integer.valueOf(position), o);
    }

    public View findContainItem(int position) {
        Object o = viewContainer.get(position);
        if (o == null) {
            return null;
        }
        PagerAdapter adapter = getAdapter();
        int N = getChildCount();
        View v;
        for (int i = 0; i < N; i++) {
            v = getChildAt(i);
            if (adapter.isViewFromObject(v, o)) {
                return (View) o;
            }
        }
        return null;
    }

    private void animateZoom(View left, View right, float positionOffset, boolean in) {
//        if (mState != State.IDLE) {
            if (left != null) {
                mScale = in ? ZOOM_MAX + (1-ZOOM_MAX)*(1-positionOffset) :
                        1+ZOOM_MAX - ZOOM_MAX*(1-positionOffset);
//                ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
//                ViewHelper.setPivotY(left, left.getMeasuredHeight()*0.5f);
//                ViewHelper.setScaleX(left, mScale);
//                ViewHelper.setScaleY(left, mScale);
                left.setPivotX(left.getMeasuredWidth()*0.5f) ;
                left.setPivotY(left.getMeasuredHeight()*0.5f) ;
                View.SCALE_X.set(left,mScale);
                View.SCALE_Y.set(left,mScale);
            }
            if (right != null) {
                mScale = in ? ZOOM_MAX + (1-ZOOM_MAX)*positionOffset :
                        1+ZOOM_MAX - ZOOM_MAX*positionOffset;
//                ViewHelper.setPivotX(right, right.getMeasuredWidth()*0.5f);
//                ViewHelper.setPivotY(right, right.getMeasuredHeight()*0.5f);
//                ViewHelper.setScaleX(right, mScale);
//                ViewHelper.setScaleY(right, mScale);
                right.setPivotX(right.getMeasuredWidth()*0.5f);
                right.setPivotY(right.getMeasuredHeight()*0.5f);
                View.SCALE_X.set(right,mScale);
                View.SCALE_Y.set(right,mScale);
            }
//        }
    }
    private void animScan(View lastView, View currentView, float offset) {
        if (mState != State.IDLE) {
            if (lastView != null) {
                View.SCALE_X.set(lastView, offset + 1);
                View.SCALE_Y.set(lastView,offset + 1);
//                View.ALPHA.set(lastView,1-offset);
            }
            if (currentView != null) {
                View.SCALE_X.set(lastView,2-offset);
                View.SCALE_Y.set(lastView,2-offset);
//                View.ALPHA.set(currentView,(offset));
            }
        }
    }
}
