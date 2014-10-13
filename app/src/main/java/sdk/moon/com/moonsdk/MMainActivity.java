package sdk.moon.com.moonsdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.amap.MLocationActivity;
import sdk.moon.com.moonsdk.clipimage.MClipImageActivity;
import sdk.moon.com.moonsdk.gestureview.MGestureViewActivity;
import sdk.moon.com.moonsdk.imageloader.MImageLoadActivity;


public class MMainActivity extends MBaseActivity {

    //显示消息，提示
    private TextView showMsg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        showMsg = findView(R.id.showMsg) ;
    }

    public void test(View view){
        Intent intent = new Intent(this,MLocationActivity.class) ;
        startActivity(intent);
    }
    public void testImage(View view){
        Intent intent = new Intent(this,MImageLoadActivity.class) ;
        startActivity(intent);
    }
    public void testGesture(View view){
        Intent intent = new Intent(this,MGestureViewActivity.class) ;
        startActivity(intent);
    }
    public void testClip(View view){
        Intent intent = new Intent(this,MClipImageActivity.class) ;
        startActivity(intent);
    }

}
