package sdk.moon.com.moonsdk.abst;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import sdk.moon.com.moonsdk.core.MFactoryImpl;
import sdk.moon.com.moonsdk.core.intf.MIFactory;

/**
 * Created by moon.zhong on 2014/10/10.
 * 所有Activity的父类，这里可以实现一些重复多余的操作
 */
public abstract class MBaseActivity extends FragmentActivity {
    /*测试图片地址*/
    public String imgUrl = "http://img.my.csdn.net/uploads/201407/26/1406382789_7174.jpg" ;
    public String imgUrl1 = "http://img.my.csdn.net/uploads/201309/01/1378037234_3539.jpg" ;
    public Context gContext ;
    public MIFactory gIFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gContext = this ;
        initFactory() ;
        initData() ;
        initView() ;
    }

    /**
     * 初始化工厂，高德地图的工厂
     */
    private void initFactory(){
        gIFactory = MFactoryImpl.getInstance() ;
    }

    /**
     * 初始化数据
     * 接收其他跳转过来的数据
     */
    public abstract void initData() ;

    /**
     * 初始化控件，几乎所有activity都是有界面的，
     * 所以都需要初始化控件
     */
    public abstract void initView() ;

    /**
     * 封装了一层臭而长的findViewById
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findView(int id){
        return (T)findViewById(id) ;
    }

    /*-------------------------吐司调用，方便其他Activity调用-----------------------------*/
    public void showToast(String showMsg){
        Toast.makeText(gContext,showMsg,Toast.LENGTH_LONG) ;
    }
    public void showToast(int resId){
        Toast.makeText(gContext,resId,Toast.LENGTH_LONG) ;
    }
    /*-------------------------吐司调用，方便其他Activity调用-----------------------------*/
}
