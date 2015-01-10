package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gyzhong on 15/1/1.
 * 画三角形
 */
public class MPathView extends View {
    private Paint mPaint ;

    public MPathView(Context context) {
        super(context);
        initView();
    }

    public MPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        mPaint = new Paint() ;
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path() ;
        path.moveTo(200,350);
        path.lineTo(450,500);
        path.lineTo(250,600);
        path.close();
        canvas.drawPath(path,mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
