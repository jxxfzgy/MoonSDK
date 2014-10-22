package sdk.moon.com.moonsdk.model.loopviewpager;

import android.os.Bundle;
import android.util.Log;

import com.moon.sdk.loopviewpager.loop1.LoopViewPager;
import com.moon.sdk.loopviewpager.loop1.ViewPager;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MBaseLoopMaxAdapter;
import sdk.moon.com.moonsdk.entity.MLoopViewBean;

/**
 * Created by Administrator on 2014/10/14 0014.
 * view pager 实现循环滚动
 */
public class MLoopViewActivity extends MBaseActivity {
    private LoopViewPager mLoopViewPager ;
    private MBaseLoopMaxAdapter mLoopMaxAdapter ;
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
        mLoopMaxAdapter = new MBaseLoopMaxAdapter(gContext,loopViewBeans) ;
        mLoopViewPager = findView(R.id.loopViewPager) ;

        mLoopViewPager.setAdapter(mLoopMaxAdapter);
        mLoopViewPager.setOffscreenPageLimit(3);
        mLoopViewPager.setPageMargin(-250);
        mLoopViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.v("zgy", "=============position================" + i % loopViewBeans.size()) ;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
