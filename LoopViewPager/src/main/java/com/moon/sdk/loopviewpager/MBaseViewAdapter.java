package com.moon.sdk.loopviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2014/10/15 0015.
 */
public abstract class MBaseViewAdapter<T> extends PagerAdapter {

    private LayoutInflater inflater ;

    private List<T> tList ;

    protected MBaseViewAdapter(Context context, List<T> tList) {
        inflater = LayoutInflater.from(context) ;
        this.tList = tList;
    }

    @Override
    public int getCount() {
        return tList.size();
    }

    public T getItem(int position){
        return  tList.get(position) ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = createView(inflater) ;
        showData(view,position);
        container.addView(view);
        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    public abstract View createView(LayoutInflater inflater) ;

    public abstract void showData(View view ,int position) ;

    /*此方法没用，*/
    public <K extends View> K findView(View view ,int id) {
        return (K) view.findViewById(id) ;
    }
}
