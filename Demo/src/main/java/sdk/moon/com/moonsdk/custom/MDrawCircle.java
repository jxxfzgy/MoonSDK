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
public class MDrawCircle extends View {
    private Paint mPaint ;
    public MDrawCircle(Context context) {
        super(context);
        initView();
    }

    public MDrawCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MDrawCircle(Context context, AttributeSet attrs, int defStyleAttr) {
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
        canvas.drawCircle(MComUtil.getScreenSize(getContext())[0]/2,MComUtil.getScreenSize(getContext())[1]/2,200,mPaint);
    }
}
