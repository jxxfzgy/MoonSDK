package sdk.moon.com.moonsdk.model.litepal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.moon.litepalsdk.dao.MBook;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MLitePalAdapter;
import sdk.moon.com.moonsdk.util.MComUtil;
import stickylistheaders.StickyListHeadersListView;

/**
 * Created by moon.zhong on 2015/1/8.
 */
public class MLitepalActivity extends MBaseActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener{

    private List<MBook> list ;
    private StickyListHeadersListView listView ;
    private TextView addView ;
    private MLitePalAdapter adapter ;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_litepal);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        list = new ArrayList<>() ;
        list.addAll(MBook.findAll(MBook.class)) ;
        Log.v("zgy", "=====list============" + list);
    }

    @Override
    public void initView() {
        listView = findView(R.id.listView) ;
        addView = findView(R.id.addData) ;
        addView.setOnClickListener(this);
        listView.setOnItemLongClickListener(this);
        View view = new View(this) ;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,MComUtil.dp2px(this,60)) ;
        view.setLayoutParams(params);
        listView.addFooterView(view);
        adapter = new MLitePalAdapter(this,list) ;
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addData:
                MAddDataActivity.start(this,1000);
                break ;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000&&resultCode == RESULT_OK){
            list.clear();
            list.addAll(MBook.findAll(MBook.class)) ;
            Log.v("zgy", "=====list============" + list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        alertDialog = new AlertDialog.Builder(this).setItems(new String[]{"删除","取消"},new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        deleteItem(position);
                        break ;
                    case 1:
                        break ;
                }
            }
        }).create();
        alertDialog.show();

        return false;
    }

    private void deleteItem(int position) {
        MBook.deleteAll(MBook.class, "name=?", list.get(position).getName()) ;
        list.clear();
        list.addAll(MBook.findAll(MBook.class)) ;
        Log.v("zgy", "=====list============" + list);
        adapter.notifyDataSetChanged();
    }
}
