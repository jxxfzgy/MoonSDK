package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import sdk.moon.com.moonsdk.util.MComUtil;

/**
 * Created by moon.zhong on 2015/1/7.
 */
public class MdynaDrawCircle extends View implements Runnable{
    private Paint mPaint ;
    private int radius = 20 ;

    public MdynaDrawCircle(Context context) {
        super(context);
        initView();
    }

    public MdynaDrawCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MdynaDrawCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG) ;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLUE);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(MComUtil.getScreenSize(getContext())[0]/2,MComUtil.getScreenSize(getContext())[1]/2,radius,mPaint);
    }

    public void setRadius(int radius){
        this.radius = radius ;
    }

    @Override
    public void run() {
        while (true){
            radius = radius + 2 ;
            if(radius > 300)
                radius = 20 ;
            float a = (300 - radius)/360 + 0.5f ;
//            mPaint.setAlpha((int)a);
            postInvalidate();
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
