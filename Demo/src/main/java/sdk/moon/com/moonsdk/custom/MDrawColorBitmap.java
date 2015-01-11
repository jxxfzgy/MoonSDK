package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.util.MComUtil;

/**
 * Created by moon.zhong on 2015/1/7.
 */
public class MDrawColorBitmap extends View {
    private Paint mPaint ;
    private Bitmap mBitmap ;
    private int x, y ;
    public MDrawColorBitmap(Context context) {
        super(context);
        initView();
    }

    public MDrawColorBitmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MDrawColorBitmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN)) ;
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                /*R*/
                1.5f, 0, 0, 0, 0,
                /*G*/
                0, 1, 0, 0, 0,
                /*B*/
                0, 0, 1, 0, 0,
                /*A*/
                0, 0, 0, 1, 0,
        })) ;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.desk_meitu) ;
        x = MComUtil.getScreenSize(getContext())[0] / 2 - mBitmap.getWidth()/2 ;
        y = MComUtil.getScreenSize(getContext())[1] / 2 - mBitmap.getHeight()/2 ;
        invalidate();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap,x,10,mPaint);
    }
}
