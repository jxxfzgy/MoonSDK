package sdk.moon.com.moonsdk.model.volley;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.moon.volley.api.ListApi;
import com.moon.volley.entity.MList;
import com.moon.volley.network.MResponse;

import sdk.moon.com.moonsdk.abst.MBaseActionBarActivity;
import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.custom.MActionBarLayout;
import sdk.moon.com.moonsdk.custom.MParentFocusEditView;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MPostSActivity extends MBaseActionBarActivity {

    private MActionBarLayout mActionBarLayout ;
    private MParentFocusEditView editView1 ;
    private MParentFocusEditView editView2 ;
    private MParentFocusEditView editView3 ;
    private MParentFocusEditView editView4 ;
    private MParentFocusEditView editView5 ;
    private MParentFocusEditView editView6 ;
    private MParentFocusEditView editView7 ;

    private MList mList ;
    private ProgressDialog mProgressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_add);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mList = new MList() ;
    }

    @Override
    public void initView() {
        mProgressBar = new ProgressDialog(this) ;
        mProgressBar.setMessage("发送中...");
        mActionBarLayout = getActionBarView() ;
        mActionBarLayout.getConfirmBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("发送中...");
                Log.v("zgy", "=============confirm=====onClick============") ;
                mList.setName(editView1.getText().toString());
                mList.setPrice(editView2.getText().toString());
                mList.setBrand(editView3.getText().toString());
                mList.setSpec(editView4.getText().toString());
                mList.setBuyFrom(editView5.getText().toString());
                mList.setProductName(editView6.getText().toString());
                mList.setRemark(editView7.getText().toString());
                Log.v("zgy", "=============confirm=====onClick============"+mList) ;
                ListApi.addListPost(mList,new MResponse<MList>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Log.v("zgy", "================error==============") ;
                        showToast("添加失败");
                    }

                    @Override
                    public void onResponse(MList response) {
                        hideProgressDialog();
                        showToast("添加成功");
                        Log.v("zgy", "==================response============"+response) ;
                    }
                });
            }
        });

        editView1 = findView(R.id.listValue1);
        editView2 = findView(R.id.listValue2);
        editView3 = findView(R.id.listValue3);
        editView4 = findView(R.id.listValue4);
        editView5 = findView(R.id.listValue6);
        editView6 = findView(R.id.listValue5);
        editView7 = findView(R.id.listValue7);
    }
    public void hideProgressDialog(){
        if(mProgressBar.isShowing()){
            mProgressBar.dismiss();
        }
    }

    public void showDialog(String info){
        mProgressBar.setMessage(info);
        if(!mProgressBar.isShowing()){
            mProgressBar.show();
        }
    }
}
