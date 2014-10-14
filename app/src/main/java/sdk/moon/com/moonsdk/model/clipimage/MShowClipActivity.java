package sdk.moon.com.moonsdk.model.clipimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.R;

/**
 * Created by Administrator on 2014/10/12 0012.
 */
public class MShowClipActivity extends MBaseActivity {
    /*显示裁剪图片*/
    private ImageView clipShowImage ;
    /*裁剪图片*/
    private Bitmap mBitmap ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_clip_show);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        byte mByte[] = getIntent().getByteArrayExtra("bitmap") ;
        mBitmap = BitmapFactory.decodeByteArray(mByte ,0 ,mByte.length) ;
    }

    @Override
    public void initView() {
        clipShowImage = findView(R.id.clipShowImage) ;
        clipShowImage.setImageBitmap(mBitmap);
    }
}
