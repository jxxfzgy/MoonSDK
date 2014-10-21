package sdk.moon.com.moonsdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.moon.sdk.expandablelistview.AbstractSlideExpandableListAdapter;
import com.moon.sdk.expandablelistview.ActionSlideExpandableListView;

import java.util.List;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MActivityAdapter;
import sdk.moon.com.moonsdk.entity.MActivityBean;


public class MMainActivity extends MBaseActivity implements AbstractSlideExpandableListAdapter.OnItemExpandCollapseListener,MActivityAdapter.OnItemSelect {

    //显示消息，提示
    private TextView showMsg ;
    private List<MActivityBean> mActivityBeanList ;
    private ActionSlideExpandableListView expandableListView ;
    private MActivityAdapter activityAdapter ;
    private Handler handler = new Handler() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        mActivityBeanList = MMainHelper.mActivityBeans ;
    }

    @Override
    public void initView() {
        expandableListView = findView(R.id.expandListView) ;
        activityAdapter = new MActivityAdapter(mActivityBeanList) ;
        activityAdapter.setOnItemSelect(this);
        expandableListView.setAdapter(activityAdapter, R.id.expandItem, R.id.expandContext);
        expandableListView.setItemExpandCollapseListener(this);

    }


    @Override
    public void onExpand(View itemView, int position) {
        mActivityBeanList.get(position).setExpand(true);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCollapse(View itemView, int position) {
        mActivityBeanList.get(position).setExpand(false);
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void select(int position, int item) {
        Intent intent = new Intent(this,mActivityBeanList.get(position).getSubBean().get(item).getActivityName()) ;
        startActivity(intent);
    }
}
