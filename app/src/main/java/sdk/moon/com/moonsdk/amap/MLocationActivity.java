package sdk.moon.com.moonsdk.amap;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.moon.amap.sdk.entity.MLocation;
import com.moon.amap.sdk.intf.MILocateService;
import com.moon.amap.sdk.listerner.MOnLocateLister;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.R;

/**
 * Created by moon.zhong on 2014/10/10.
 */
public class MLocationActivity extends MBaseActivity implements MOnLocateLister{
    /*定位信息*/
    private TextView location ;
    /*定位服务*/
    private MILocateService miLocateService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_location_service);
        super.onCreate(savedInstanceState);
        location() ;

    }

    @Override
    public void initData() {
        miLocateService = gIFactory.getMIAMapFactory().getLocationService() ;
    }

    @Override
    public void initView() {
        location = findView(R.id.showMsg1) ;
        location.setText(miLocateService.getLocation().getAddress()==null?"正在定位中...":miLocateService.getLocation().getAddress());
    }

    public void test(View view){
        location() ;
    }

    /**
     * 执行定位操作
     */
    private void location(){
        miLocateService.registerLocateLister(this);
        miLocateService.startLocation(gContext);
    }

    @Override
    public void onLocateSuccess(MLocation mMLocation) {
        miLocateService.unRegisterLocateLister(this);
        location.setText(mMLocation.getAddress()) ;
    }

    @Override
    public void onLocateFail() {
        miLocateService.unRegisterLocateLister(this);
        location.setText("定位失败") ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onLocateFail() ;
    }
}
