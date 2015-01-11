package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import sdk.moon.com.moonsdk.R;


/**
 * Created by moon.zhong on 2014/11/27.
 * viewPager 下标切换
 */
public class MPageMark extends View {
    /*需要更换的图标*/
    private Drawable mDrawable;
    /*每个小图标的间距*/
    private int mMarkMargin;
    /*小圆点的个数*/
    private int mItemCount;
    /*当前选中的小圆点*/
    private int mCurrItem;
    /*小圆点的大小*/
    private int mIconSize;
    /*画小圆点的画笔*/
    private Paint mPaint;
    /*画未选中的小圆点颜色*/
    private int mNormalColor;
    /*画已经选中的小圆点的颜色*/
    private int mSelectColor;
    /*小圆点的半径*/
    private float mRadius;
    /*小圆点的圆心*/
    private float mCx, mCy;
    /*是否线性滑动*/
    private boolean mSmooth;
    /*偏移量*/
    private float mOffset  ;
    /*viewpager滑动事件*/
    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public MPageMark(Context context) {
        super(context);
    }

    public MPageMark(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageMark);
        int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.PageMark_mark_icon:
                    mDrawable = a.getDrawable(attr);
                    break;
                case R.styleable.PageMark_icon_margin:
                    mMarkMargin = (int) a.getDimension(attr, 5.0f);
                    break;
                case R.styleable.PageMark_icon_size:
                    mIconSize = (int) a.getDimension(attr, 0);
                    break;
                case R.styleable.PageMark_doc_normal:
                    mNormalColor = a.getColor(attr, 0);
                    break;
                case R.styleable.PageMark_doc_select:
                    mSelectColor = a.getColor(attr, 0);
                    break;
            }
        }
        a.recycle();
        initDoc();
        initIcon();
//        getResources().getString(R.string.abc_action_bar_home_description);
    }

    private void initIcon() {
        if (mDrawable != null) {
            mIconSize = mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(0, 0, mIconSize, mIconSize);
        }
        mRadius = mIconSize * 0.5f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthSpec) {
        int mode = MeasureSpec.getMode(widthSpec);
        int size = MeasureSpec.getSize(widthSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                return Math.min(size, (getPaddingLeft() + mIconSize * mItemCount + mMarkMargin * (mItemCount - 1)) + getPaddingRight());
        }
        return size;
    }

    private int measureHeight(int heightSpec) {
        int mode = MeasureSpec.getMode(heightSpec);
        int size = MeasureSpec.getSize(heightSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                size = Math.min(size, mIconSize + getPaddingBottom() + getPaddingTop());
        }
        mCy = size * 0.5f;
        return size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        int step = mIconSize + mMarkMargin;
        for (int i = 0; i < mItemCount; i++) {
            if (mDrawable != null) {
                mDrawable.setState(EMPTY_STATE_SET);
                mDrawable.draw(canvas);
            } else {
                mPaint.setColor(mNormalColor);
                drawDoc(canvas);
            }
            canvas.translate(step, 0);
        }
        canvas.restore();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        for (int i = 0; i < mItemCount; i++) {
            if (mCurrItem == i) {
                canvas.translate(step*(mCurrItem+mOffset), 0);
                if (mDrawable != null) {
                    mDrawable.setState(SELECTED_STATE_SET);
                    mDrawable.draw(canvas);
                } else {
                    mPaint.setColor(mSelectColor);
                    drawDoc(canvas);
                }
            }
        }
    }
        private ViewPager.OnPageChangeListener mPagerChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int i2) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(position, offset, i2);
                }
                if(mSmooth){
                    mCurrItem = position;
                    mOffset = offset ;
                    invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(position);
                }
                if(!mSmooth){
                    mCurrItem = position;
                    invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(i);
                }
            }
        };

    private void initDoc() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mNormalColor);
    }

    private void drawDoc(Canvas canvas) {
        canvas.drawCircle(mRadius, mCy, mRadius, mPaint);
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        PagerAdapter adapter = viewPager.getAdapter();
        mItemCount = adapter.getCount();
        viewPager.setOnPageChangeListener(mPagerChangeListener);
        mCurrItem = viewPager.getCurrentItem();
        invalidate();
        requestLayout();
    }

    /**
     * 此方法打算实现线性滑动效果，就是
     * 小圆点随着viewpager滑动的距离而滑动，
     *
     * @param linearSmooth
     */
    public void setLinearSmooth(boolean linearSmooth) {
        mSmooth = linearSmooth;
    }

}
