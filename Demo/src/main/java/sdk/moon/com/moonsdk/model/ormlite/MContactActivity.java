package sdk.moon.com.moonsdk.model.ormlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.moon.sdk.ormlite.dao.MDaoContactBean;
import com.moon.sdk.ormlite.entity.MContactBean;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MContactAdapter;
import sdk.moon.com.moonsdk.util.MDialogUtil;

/**
 * Created by moon on 2014/10/22 0022.
 */
public class MContactActivity extends MBaseActivity implements DialogInterface.OnClickListener
                                        ,AdapterView.OnItemClickListener{
    /*对话框自定义View*/
    private View view  ;
    /*联系人数据库操作*/
    private MDaoContactBean mDaoContactBean ;
    /*联系人集合*/
    private List<MContactBean> list ;
    /*联系人适配器*/
    private MContactAdapter mContactAdapter ;
    /*联系人列表*/
    private ListView mListView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ormlite_contact);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mDaoContactBean = new MDaoContactBean(gContext) ;
        list = new ArrayList<MContactBean>() ;
        list.addAll(mDaoContactBean.queryAll()) ;
    }

    @Override
    public void initView() {
        view = getLayoutInflater().inflate(R.layout.dialog_addcontact_view, null) ;
        mContactAdapter = new MContactAdapter(list) ;
        mListView = findView(R.id.contactListView) ;
        mListView.setAdapter(mContactAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_ment,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addContact:
                view = getLayoutInflater().inflate(R.layout.dialog_addcontact_view, null) ;
                MDialogUtil.showViewDialog(gContext,"增加联系人",view,this);
                break ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        TextView textName = (TextView) view.findViewById(R.id.dialogName) ;
        TextView textTel = (TextView) view.findViewById(R.id.dialogTel) ;
        String name = textName.getText().toString() ;
        String tel = textTel.getText().toString() ;
        if("".equals(name)||"".equals(tel)){
            showToast("请输入正确的姓名或电话号码！");
            return;
        }
        MContactBean mContactBean = new MContactBean() ;
        mContactBean.setName(name);
        mContactBean.setTel(tel);
        mDaoContactBean.insert(mContactBean) ;
        showToast("添加成功！");
        list.clear();
        list.addAll(mDaoContactBean.queryAll());
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MContactBean mContactBean = list.get(position) ;
        showToast("姓名:"+mContactBean.getName()+"、电话:"+mContactBean.getTel());
    }
}
