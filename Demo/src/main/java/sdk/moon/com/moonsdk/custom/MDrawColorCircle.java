package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Array;

import sdk.moon.com.moonsdk.util.MComUtil;

/**
 * Created by moon.zhong on 2015/1/7.
 */
public class MDrawColorCircle extends View {
    private Paint mPaint ;
    public MDrawColorCircle(Context context) {
        super(context);
        initView();
    }

    public MDrawColorCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MDrawColorCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
//        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{
//                /*R*/
//                0.5f,0,0,0,0,
//                /*G*/
//                0,0.5f,0,0,0,
//                /*B*/
//                0,0,0.5f,0,0,
//                /*A*/
//                0,0,0,0.5f,0,
//        })) ;
        mPaint.setColorFilter(new ColorMatrixColorFilter(new float[]{
                /*R*/
                1, 0, 0, 0, 0,
                /*G*/
                0, 1, 0, 0, 0,
                /*B*/
                0, 0, 1, 0, 0,
                /*A*/
                0, 0, 0, 1, 0,
        })) ;
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(MComUtil.getScreenSize(getContext())[0]/2,MComUtil.getScreenSize(getContext())[1]/2,200,mPaint);
    }
}
