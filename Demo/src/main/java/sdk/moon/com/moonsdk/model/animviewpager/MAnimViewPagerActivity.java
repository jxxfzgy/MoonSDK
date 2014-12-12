package sdk.moon.com.moonsdk.model.animviewpager;

import android.os.Bundle;

import com.moon.sdk.loopviewpager.anim.MAnimViewPager;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MAnimViewPagerAdt;

/**
 * Created by moon.zhong on 2014/12/12.
 */
public class MAnimViewPagerActivity extends MBaseActivity {
    private MAnimViewPager mAnimViewPager ;
    private MAnimViewPagerAdt mAnimViewPagerAdt  ;
    private List<String> list ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_anim_viewpager);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        list = new ArrayList<>() ;
        list.add("test");
        list.add("test");
        list.add("test");
        list.add("test");
        list.add("test");
        list.add("testd");
        list.add("testd");
    }

    @Override
    public void initView() {
        mAnimViewPager = findView(R.id.animViewPager) ;
        mAnimViewPager.setOffscreenPageLimit(3);
        mAnimViewPager.setPageMargin(-250);
        mAnimViewPagerAdt = new MAnimViewPagerAdt(gContext,list,mAnimViewPager) ;
        mAnimViewPager.setAdapter(mAnimViewPagerAdt);
    }
}
