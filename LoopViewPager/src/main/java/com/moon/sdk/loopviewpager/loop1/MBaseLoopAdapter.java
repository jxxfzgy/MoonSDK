package com.moon.sdk.loopviewpager.loop1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by moon.zhong on 2014/10/15.
 */
public abstract class MBaseLoopAdapter<T> extends android.support.v4.view.PagerAdapter {
    private List<T> tList ;
    private static final int MAX_SIZE = 20;
    private LayoutInflater inflater ;

    protected MBaseLoopAdapter(Context context, List<T> tList) {
        inflater = LayoutInflater.from(context) ;
        this.tList = tList;
    }

    @Override
    public int getCount() {
        return MAX_SIZE*tList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = createView(inflater) ;
        showData(view,position%this.tList.size());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public T getItem(int position){
        return  tList.get(position) ;
    }

    public abstract View createView(LayoutInflater inflater) ;

    public abstract void showData(View view ,int position) ;

    public <K extends View > K findView(View view, int id){
        return  (K) view.findViewById(id) ;
    }
}
