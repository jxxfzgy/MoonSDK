package sdk.moon.com.moonsdk.model.litepal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.moon.litepalsdk.dao.MBook;
import com.moon.litepalsdk.dao.MPublish;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MPublishAdapter;
import sdk.moon.com.moonsdk.util.MComUtil;

/**
 * Created by moon.zhong on 2015/1/9.
 */
public class MOne2ManyActivity extends MBaseActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private ListView listView;
    private MPublishAdapter mPublishAdapter ;
    private List<MPublish> publishs ;
    private TextView txtAdd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_one_many);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        publishs = new ArrayList<>() ;
        publishs.addAll(MPublish.findAll(MPublish.class)) ;
        Log.v("zgy","=====publishs=========="+publishs) ;
    }

    @Override
    public void initView() {
        listView = findView(R.id.listView) ;
        txtAdd = findView(R.id.addData);
        txtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MAddPublishActivity.start(MOne2ManyActivity.this,1000);
            }
        });
        View view = new View(this) ;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MComUtil.dp2px(this, 60)) ;
        view.setLayoutParams(params);
        listView.addFooterView(view);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        mPublishAdapter = new MPublishAdapter(this,publishs) ;
        listView.setAdapter(mPublishAdapter);
        mPublishAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setItems(new String[]{"出版","删除","取消"}
                ,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        MAddDataActivity.start(MOne2ManyActivity.this, 1000, publishs.get(position).getId());
                        break ;
                    case 1:
                        deleteItem(position);
                        break ;
                    case 2:
                        break;
                }
            }
        }).create() ;
        alertDialog.show();
        return false;
    }
    private void deleteItem(int position) {
        MBook.deleteAll(MPublish.class, "name=?", publishs.get(position).getName()) ;
        publishs.clear();
        publishs.addAll(MPublish.findAll(MPublish.class)) ;
        Log.v("zgy", "=====list============" + publishs);
        mPublishAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000&&resultCode == RESULT_OK){
            publishs.clear();
            publishs.addAll(MPublish.findAll(MPublish.class)) ;
            Log.v("zgy", "=====list============" + publishs);
            mPublishAdapter.notifyDataSetChanged();
        }
    }
}
