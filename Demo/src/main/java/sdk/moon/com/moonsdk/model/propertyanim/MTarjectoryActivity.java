package sdk.moon.com.moonsdk.model.propertyanim;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;

/**
 * Created by moon.zhong on 2014/12/30.
 */
public class MTarjectoryActivity extends MBaseActivity implements View.OnClickListener{

    private ImageView imageView ;
    private Button startBtn ;
    private MPropertyAnim mPropertyAnim ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_tarjectory);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        imageView = findView(R.id.ball) ;
        startBtn = findView(R.id.startAnim) ;
        startBtn.setOnClickListener(this);
        mPropertyAnim = new MPropertyAnim() ;
    }

    @Override
    public void onClick(View v) {
        mPropertyAnim.trajectoryAnim1(imageView);
    }
}
