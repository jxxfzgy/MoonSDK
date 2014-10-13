package sdk.moon.com.moonsdk.clipimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.moon.sdk.clipimage.ClipImageLayout;
import com.moon.sdk.imgloader.intf.MIImageLoader;

import java.io.ByteArrayOutputStream;

import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.R;

/**
 * Created by moon on 2014/10/12 0012.
 */
public class MClipImageActivity extends MBaseActivity {
    /*裁剪容器*/
    private ClipImageLayout clipImageLayout ;
    private MIImageLoader miImageLoader ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_clip_image);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clip_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.clipImage:
                Bitmap bitmap = clipImageLayout.clip() ;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                byte[] mBytes = bos.toByteArray();
                Intent intent = new Intent(this,MShowClipActivity.class) ;
                intent.putExtra("bitmap",mBytes) ;
                startActivity(intent);
                finish();
                break ;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initData() {
        miImageLoader = gIFactory.getMIImageLoaderFactory().getImageLoader(gContext) ;
    }

    @Override
    public void initView() {
        clipImageLayout = findView(R.id.clipImage) ;
        miImageLoader.loadNormalImage(imgUrl,clipImageLayout.getZoomImageView());
    }
}
