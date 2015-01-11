package sdk.moon.com.moonsdk.model.litepal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.moon.litepalsdk.dao.MBook;
import com.moon.litepalsdk.dao.MPublish;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActionBarActivity;
import sdk.moon.com.moonsdk.custom.MActionBarLayout;
import sdk.moon.com.moonsdk.custom.MParentFocusEditView;

/**
 * Created by moon.zhong on 2015/1/8.
 */
public class MAddPublishActivity extends MBaseActionBarActivity {

    private MActionBarLayout mActionBarLayout;
    private MParentFocusEditView editView1;
    private MParentFocusEditView editView2;

    private MPublish mPublish;
    private ProgressDialog mProgressBar;

    public static void start(Context context, int requestCode) {
        Intent intent = new Intent(context, MAddPublishActivity.class);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lite_add_publish);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mPublish = new MPublish();
    }

    @Override
    public void initView() {
        mProgressBar = new ProgressDialog(this);
        mProgressBar.setMessage("");
        mActionBarLayout = getActionBarView();
        mActionBarLayout.getConfirmBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("保存中...");
                mPublish.setName(editView1.getText().toString());
                mPublish.setAddress(editView2.getText().toString());
                if (mPublish.save()) {
                    showToast("保存成功");
                    setResult(RESULT_OK);
                    finish();
                } else {
                    showToast("保存失败");
                }
                hideProgressDialog();
            }
        });

        editView1 = findView(R.id.listValue1);
        editView2 = findView(R.id.listValue2);
        mActionBarLayout.setConfirmBtnText("保存");


    }

    public void hideProgressDialog() {
        if (mProgressBar.isShowing()) {
            mProgressBar.dismiss();
        }
    }

    public void showDialog(String info) {
        mProgressBar.setMessage(info);
        if (!mProgressBar.isShowing()) {
            mProgressBar.show();
        }
    }
}
