package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moon.sdk.loopviewpager.MBaseViewAdapter;
import com.moon.sdk.loopviewpager.anim.MAnimViewPager;
//import com.nineoldandroids.view.ViewHelper;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.model.animviewpager.MAnimViewPagerActivity;

/**
 * Created by moon.zhong on 2014/12/12.
 */
public class MAnimViewPagerAdt extends MBaseViewAdapter<String> {
    private MAnimViewPager mAnimViewPager ;
    public MAnimViewPagerAdt(Context context, List<String> views, MAnimViewPager mAnimViewPager) {
        super(context, views);
        this.mAnimViewPager = mAnimViewPager ;
    }

    @Override
    public View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.viewpager_anim, null);
    }

    @Override
    public void showData(View view, int position) {
//        View.ALPHA.set(view,0.0f);

//        ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
//        ViewHelper.setPivotY(view, view.getMeasuredHeight()*0.5f);
        Log.v("zgy", "===========showData()====" + view) ;
        mAnimViewPager.addViewContainer(position,view);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mAnimViewPager.findContainItem(position));
    }
}
