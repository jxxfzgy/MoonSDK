package sdk.moon.com.moonsdk.model.ormlite;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.moon.sdk.ormlite.dao.MDaoApkInfo;
import com.moon.sdk.ormlite.dao.MDaoContact;
import com.moon.sdk.ormlite.entity.MApkInfo;
import com.moon.sdk.ormlite.entity.MContact;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.application.MApplication;

/**
 * Created by moon.zhong on 2014/10/17.
 */
public class MOrmliteActivity extends MBaseActivity {

    /*联系人数据表*/
    private MDaoContact mDaoContact ;
    /*apk详情数据表*/
    private MDaoApkInfo mDaoApkInfo ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ormlite);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        mDaoContact = new MDaoContact(MApplication.getContext()) ;
        mDaoApkInfo = new MDaoApkInfo(MApplication.getContext()) ;
    }

    @Override
    public void initView() {
        Log.v("zgy", "========mDaoContact===========" + mDaoContact.queryAll().size()) ;
    }

    /**
     * 保存数据
     * @param view
     */
    public void insert(View view){
//        MContact mContact = new MContact() ;
//        mContact.setAddress("腾讯大厦");
//        mContact.setCompany("腾讯云技术有限公司");
//        mContact.setName("马云");
//        mContact.setTelNum("13888888888");
//        mDaoContact.insert(mContact) ;
        MApkInfo mApkInfo = new MApkInfo() ;
        mApkInfo.setApkName("abcdefdfas");
        mApkInfo.setPackageName("com.zhong.guang");
        mApkInfo.setApkVersion("1.2.1.2");
        mDaoApkInfo.insert(mApkInfo) ;
        List<MApkInfo> list = mDaoApkInfo.queryAll() ;
        Iterator<MApkInfo> iterator = list.iterator() ;
        while (iterator.hasNext()){
            Log.v("zgy","======MApkInfo===="+iterator.next());

        }


    }
    /**
     * 删除数据
     * @param view
     */
    public void delete(View view){

    }
    /**
     * 更新数据
     * @param view
     */
    public void update(View view){
        MContact mContact = new MContact() ;
        mContact.setAddress("腾讯大厦");
        mContact.setCompany("tencent");
        mContact.setName("马化腾");
        mContact.setTelNum("130 6688 0503");
//        int data = mDaoContact.updateData(mContact) ;
//        Log.v("zgy","======MContact===="+data);
        List<MContact> list = mDaoContact.queryAll() ;
        Iterator<MContact> iterator = list.iterator() ;
        while (iterator.hasNext()){
            Log.v("zgy","======MContact===="+iterator.next());

        }
    }
    /**
     * 查询数据
     * @param view
     */
    public void query(View view){
        Map<String,Object> args = new HashMap<String, Object>() ;
        args.put("name","马云") ;
        args.put("telNum","13") ;
        List<MContact> list = mDaoContact.query(args) ;
        Iterator<MContact> iterator = list.iterator() ;
        while (iterator.hasNext()){
            Log.v("zgy","======MContact===="+iterator.next());
        }

    }
}
