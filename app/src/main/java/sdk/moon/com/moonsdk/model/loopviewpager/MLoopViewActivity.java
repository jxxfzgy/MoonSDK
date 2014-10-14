package sdk.moon.com.moonsdk.model.loopviewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.moon.sdk.loopviewpager.LoopPagerAdapterWrapper;
import com.moon.sdk.loopviewpager.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MLoopAdapter;
import sdk.moon.com.moonsdk.entity.MLoopViewBean;

/**
 * Created by Administrator on 2014/10/14 0014.
 * view pager 实现循环滚动
 */
public class MLoopViewActivity extends MBaseActivity {
    private LoopViewPager mLoopViewPager ;
    private MLoopAdapter mLoopAdapter ;
    private List<MLoopViewBean> loopViewBeans ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loop_viewpager);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        loopViewBeans = new ArrayList<MLoopViewBean>() ;
        loopViewBeans.add(new MLoopViewBean("love1",R.drawable.test));
        loopViewBeans.add(new MLoopViewBean("love2",R.drawable.ic_launcher));
        loopViewBeans.add(new MLoopViewBean("love3",R.drawable.test));
        loopViewBeans.add(new MLoopViewBean("love4",R.drawable.ic_launcher));
        loopViewBeans.add(new MLoopViewBean("love5",R.drawable.test));
        loopViewBeans.add(new MLoopViewBean("love6",R.drawable.ic_launcher));
        loopViewBeans.add(new MLoopViewBean("love7",R.drawable.test));
    }

    @Override
    public void initView() {
        mLoopAdapter = new MLoopAdapter(gContext ,loopViewBeans) ;
        mLoopViewPager = findView(R.id.loopViewPager) ;
        mLoopViewPager.setAdapter(mLoopAdapter);
        mLoopViewPager.setOffscreenPageLimit(3);
        mLoopViewPager.setPageMargin(-300);
        mLoopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.v("zgy", "=============position================" + i) ;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
