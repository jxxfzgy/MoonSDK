package sdk.moon.com.moonsdk.model.propertyanim;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;

/**
 * Created by moon.zhong on 2014/12/30.
 */
public class MPropertyActivity extends MBaseActivity {

    private ImageView imageView ;
    private MPropertyAnim mPropertyAnim ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_property);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mPropertyAnim = new MPropertyAnim() ;
        imageView = findView(R.id.imageView) ;
//        imageView.setTranslationX();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect rect = new Rect();
                imageView.getDrawingRect(rect);

                float pivotX = rect.right/2 ;
                float pivotY = rect.bottom/2 ;
                rect.bottom = 600 ;
                rect.right = 720 ;
                Rect rect1 = imageView.getDrawable().getBounds() ;
                rect.bottom = rect1.bottom*rect.right/rect1.right ;
//                Log.v("zgy","=======getHeight======"+pivotX) ;
//                Log.v("zgy","=======getHeight======"+pivotY) ;
                pivotY = pivotY - rect.bottom/2 ;
//                mPropertyAnim.animHolder(new MViewWrapper(imageView),rect,0.0f,pivotY);
//                mPropertyAnim.valueAnim(imageView);
                mPropertyAnim.customType();
            }
        });


    }
}
