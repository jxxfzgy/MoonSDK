package sdk.moon.com.moonsdk.model.clickstream;

import android.os.Bundle;
import android.view.View;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;

/**
 * Created by moon.zhong on 2014/12/11.
 * 点击流统计页面
 */
public class MClickStreamActivity extends MBaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_clicksteram);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        findView(R.id.click_01).setOnClickListener(this);
        findView(R.id.click_02).setOnClickListener(this);
        findView(R.id.click_03).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_01 :
                iEvent.onEvent("click_01");
                break ;
            case R.id.click_02 :
                iEvent.onEvent("click_02");
                break ;
            case R.id.click_03 :
                iEvent.onEvent("click_03");
                break ;
        }
    }
}
