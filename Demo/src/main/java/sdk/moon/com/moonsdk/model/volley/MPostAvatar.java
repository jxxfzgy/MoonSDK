package sdk.moon.com.moonsdk.model.volley;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.moon.volley.api.InfoApi;
import com.moon.volley.entity.MAvatar;
import com.moon.volley.network.MResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MPostAvatar extends MBaseActivity implements View.OnClickListener {
    private Button postBut;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_post_avatar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        filePath = gContext.getFilesDir().getPath() + File.separator + "upload.jpg";

        File file = new File(filePath);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) gContext.getResources().getDrawable(R.drawable.test);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            try {
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        filePath = "/storage/sdcard0/to8to/camera/1416797888446.jpg";
    }

    @Override
    public void initView() {
        postBut = findView(R.id.upload);
        postBut.setOnClickListener(this);
        BitmapFactory.Options options = new BitmapFactory.Options() ;
        options.inJustDecodeBounds = true ;
        BitmapFactory.decodeFile(filePath,options) ;
        options.inSampleSize = 50 ;
        options.inJustDecodeBounds = false ;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath,options) ;
        ImageView imageView = findView(R.id.avatar) ;
        imageView.setImageBitmap(bitmap);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:
                InfoApi.postAvatar(filePath, new MResponse<MAvatar>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                    @Override
                    public void onResponse(MAvatar response) {

                    }
                });
                break;
        }
    }
}
