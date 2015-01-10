package sdk.moon.com.moonsdk.abst;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.custom.MActionBarLayout;

/**
 * Created by moon.zhong on 2014/11/17.
 */
public abstract class MBaseActionBarActivity extends MBaseActivity {
    private LinearLayout mLinearLayout ;
    private LayoutInflater mLayoutInflater ;
    private View actionBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.hide();
    }

    @Override
    public void setContentView(int layoutId){
        mLinearLayout = new LinearLayout(this) ;
        mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLayoutInflater = getLayoutInflater() ;
        actionBarView = mLayoutInflater.inflate(R.layout.actionbar_include,null) ;
        mLinearLayout.addView(actionBarView);
        super.setContentView(mLinearLayout);
        View view = mLayoutInflater.inflate(layoutId,null) ;
        setContentView(view);

    }

    @Override
    public void setContentView(View view) {
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLinearLayout.addView(view);
    }

    @Override
    public void setContentView(View view,ViewGroup.LayoutParams layoutParams){
        view.setLayoutParams(layoutParams);
        mLinearLayout.addView(view);
    }

    public MActionBarLayout getActionBarView(){
        return (MActionBarLayout)actionBarView.findViewById(R.id.actionbar);
    }
}
