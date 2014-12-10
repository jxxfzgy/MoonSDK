package sdk.moon.com.moonsdk.model.volley;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.moon.volley.api.TestApi;
import com.moon.volley.entity.TestBean;
import com.moon.volley.network.MResponse;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class MPostRActivity extends MBaseActivity{

    private TextView showInfo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_volley);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        showInfo = findView(R.id.showNetData) ;
        TestApi.postListDetail(new MResponse<TestBean>() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(TestBean response) {
                showInfo.setText(response.toString());
                Log.v("zgy", "===========response=============="+response) ;
            }
        });
    }

    @Override
    public void initView() {

    }
}
