package sdk.moon.com.moonsdk.model.drawview;

import android.os.Bundle;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.custom.MdynaDrawCircle;

/**
 * Created by moon.zhong on 2015/1/7.
 */
public class MDrawDyncCircleActivity extends MBaseActivity {
    private MdynaDrawCircle mdynaDrawCircle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_darwdynccircle);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mdynaDrawCircle = findView(R.id.drawCircle) ;
//        mdynaDrawCircle = new MdynaDrawCircle(this) ;
        new Thread(mdynaDrawCircle).start();
    }
}
