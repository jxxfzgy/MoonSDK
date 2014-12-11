package sdk.moon.com.moonsdk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.moon.sdk.expandablelistview.AbstractSlideExpandableListAdapter;
import com.moon.sdk.expandablelistview.ActionSlideExpandableListView;
import com.moon.sdk.refresh.MRefreshView;

import java.util.List;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MActivityAdapter;
import sdk.moon.com.moonsdk.entity.MActivityBean;

/**
 * 整个SDK入口
 */
public class MMainActivity extends MBaseActivity implements AbstractSlideExpandableListAdapter.OnItemExpandCollapseListener,
                                            MActivityAdapter.OnItemSelect,MRefreshView.OnRefreshLister {
    /*每个model的信息*/
    private List<MActivityBean> mActivityBeanList ;
    /*可展开的listview*/
    private ActionSlideExpandableListView expandableListView ;
    /*可展开listview的适配器*/
    private MActivityAdapter activityAdapter ;
    /*下拉刷新控件*/
    private MRefreshView mRefreshView ;

    private Handler mHandler = new Handler() ;
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
        mActivityBeanList = new MMainHelper().getData() ;
    }

    @Override
    public void initView() {
        expandableListView = findView(R.id.expandListView) ;
        activityAdapter = new MActivityAdapter(mActivityBeanList) ;
        activityAdapter.setOnItemSelect(this);
        expandableListView.setAdapter(activityAdapter, R.id.expandItem, R.id.expandContext);
        expandableListView.setItemExpandCollapseListener(this);
        mRefreshView = findView(R.id.main_refresh) ;
        mRefreshView.setOnRefreshLister(this);
        mRefreshView.setShowView(expandableListView);

    }


    @Override
    public void onExpand(View itemView, int position) {
        mActivityBeanList.get(position).setExpand(true);
        View view  = (View) itemView.getTag() ;
//        Log.v("zgy", "============view=========" + view);
        TextView textView = (TextView)view.findViewById(R.id.expandItem) ;
        textView.setTextColor(this.getResources().getColor(R.color.green));
    }

    @Override
    public void onCollapse(View itemView, int position) {
        mActivityBeanList.get(position).setExpand(false);
        View view  = (View) itemView.getTag() ;
        TextView textView = (TextView)view.findViewById(R.id.expandItem) ;
        textView.setTextColor(this.getResources().getColor(R.color.dark_black));
    }

    @Override
    public void select(int position, int item) {
        Intent intent = new Intent(this,mActivityBeanList.get(position).getSubBean().get(item).getActivityName()) ;
        startActivity(intent);
    }

    @Override
    public void refresh() {
        mHandler.postDelayed(stopRefresh,1000) ;
    }

    Runnable stopRefresh = new Runnable() {
        @Override
        public void run() {
            mRefreshView.stopRefresh();
        }
    } ;
}
