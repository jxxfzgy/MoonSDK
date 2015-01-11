package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by moon.zhong on 2014/12/31.
 */
public class MDrawPath extends View {

    private Paint mPaint ;
    public MDrawPath(Context context) {
        super(context);
        init();
    }

    public MDrawPath(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MDrawPath(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint() ;
        mPaint.setColor(0xffFF0000);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0,0,500,500,mPaint);
        Path path = new Path() ;
        path.moveTo(10,0);
        path.lineTo(10, 300);
        path.lineTo(150, 450);
        path.lineTo(300,0);
        path.lineTo(200,500);
        path.lineTo(500,210);
        path.lineTo(700,800);
        path.lineTo(500,420);
        path.close();
        mPaint.setColor(0xff0080ff);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,mPaint);
        Log.v("zgy", "=========onDraw=======") ;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
