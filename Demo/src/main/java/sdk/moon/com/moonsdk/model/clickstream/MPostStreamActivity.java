package sdk.moon.com.moonsdk.model.clickstream;

import android.os.Bundle;
import android.view.View;

import sdk.moon.com.moonsdk.abst.MBaseActionBarActivity;
import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.custom.MActionBarLayout;

/**
 * Created by moon.zhong on 2014/12/11.
 * 向服务器发送点击流统计数据
 */
public class MPostStreamActivity extends MBaseActionBarActivity {
    private MActionBarLayout mActionBarLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_postclickstream);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mActionBarLayout = getActionBarView() ;
        mActionBarLayout.setConfirmOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*点击发送数据*/
                iEvent.onPost();
            }
        });
    }
}
