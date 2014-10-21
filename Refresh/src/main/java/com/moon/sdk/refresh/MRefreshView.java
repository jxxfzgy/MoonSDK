package com.moon.sdk.refresh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 下拉刷新，有很多开源的下拉刷新机制，但都不能通用
 * 此款下拉刷新，能适合所有控件的刷新，即使是TextView也能刷新
 * create by moon
 */
public class MRefreshView extends ViewGroup implements OnGestureListener {
    /*刷新的头部*/
    private View refreshHead;
    /*当数据为空时，显示空界面*/
    private View emptyView;
    /*下拉刷新控件的内部控件*/
    private View childView;
    private View showView;
    /*加载过程中，显示的动画效果*/
    private ImageView animationView;
    /*字体提示信息，下来可以刷新，松开可以刷新，正在刷新。。*/
    private TextView textTips;
    /*view滚动，需要用到*/
    private Scroller mScroller;
    /*手势识别需要用到*/
    private GestureDetector mGesture;
    /*第一次布局次控件，让刷新view先隐藏起来，
    * 滚动到上面
    * */
    private boolean isFirst = true;
    private float downPointY;
    private int data_5 = 0;
    /*刷新view当前的状态*/
    private MRefreshStatue refreshStatue;
    /*刷新监听操作，当达到下拉刷新的条件时，触发*/
    private OnRefreshLister mOnRefreshLister;
    /*上下文*/
    private Context mContext;
    /*listView控件的父类*/
    private AdapterView<?> mAdapterView;
    /*垂直滚动控件*/
    private ScrollView mScrollView;

    private boolean showEmpty = false;

    public MRefreshView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
        initData();
        initContentAdapterView();
    }

    /**
     * 实例化对象
     */
    private void initData() {
        this.mScroller = new Scroller(mContext);
        this.mGesture = new GestureDetector(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        this.refreshHead = ((Activity) mContext).getLayoutInflater().inflate(R.layout.refresh_head, null);
        this.emptyView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.refresh_content, null);
        this.animationView = ((ImageView) refreshHead.findViewById(R.id.progress));
        this.textTips = ((TextView) refreshHead.findViewById(R.id.text));
        addView(this.refreshHead);
    }

    /**
     * 设置动画开关
     *
     * @param enable
     */
    private void setAnimation(boolean enable) {
        if (enable) {
            this.animationView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) this.animationView.getDrawable()).start();
        } else {
            ((AnimationDrawable) this.animationView.getDrawable()).stop();
            this.animationView.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 滚动刷新控件
     */
    private void scrollInvalidate() {
        int scrollY = getScrollY();
        switch (refreshStatue) {
            case REFRESH_PREPARE:
                refreshStatue = MRefreshStatue.REFRESH_ING;
                int refreshHeight = this.animationView.getTop() - scrollY;
                int duration = (1000 * Math.abs(refreshHeight) / this.refreshHead.getHeight());
                this.mScroller.startScroll(0, scrollY, 0, refreshHeight, duration);
                invalidate();
                setAnimation(true);
                if (this.mOnRefreshLister != null)
                    this.mOnRefreshLister.refresh();
                this.textTips.setText("");
                break;
            case REFRESH_NORMAL:
            case REFRESH_END:
            case REFRESH_ING:
                int stopHeight = this.refreshHead.getHeight() - scrollY;
                this.mScroller.startScroll(0, scrollY, 0, stopHeight, 500);
                invalidate();
                refreshStatue = MRefreshStatue.REFRESH_NORMAL;
                setAnimation(false);
                break;
        }
    }

    //根据自己的控件来定义高度
    public final void setPaddingTop(int data) {
        this.data_5 = data;
    }

    public final void stopRefresh() {

        /*放在外面是为了第一次执行stop时可以显示空数据界面*/
        if (showView instanceof AdapterView<?>) {
            mAdapterView = (AdapterView<?>) showView;
            if (mAdapterView.getAdapter() == null || mAdapterView.getAdapter().getCount() == 0) {
                showEmptyView();
            } else {
                hideEmptyView();
            }
        } else if (showView instanceof ScrollView) {
            // finish later
            mScrollView = (ScrollView) showView;
            if (mScrollView.getChildCount() == 0) {
                showEmptyView();
            } else {
                hideEmptyView();
            }
        }
        scrollInvalidate();
    }

    public final void setShowView(View showView) {
        this.showView = showView;
        if (showView instanceof AdapterView<?>) {
            mAdapterView = (AdapterView<?>) showView;
        } else if (showView instanceof ScrollView) {
            // finish later
            mScrollView = (ScrollView) showView;
        }
    }

    public final void showEmptyView() {
        if (!showEmpty) {
            showEmpty = true;
            removeView(showView);
            removeView(emptyView);
            addView(this.emptyView);
        }
    }

    public final void hideEmptyView() {
        /* 瀑布流筛选时，无数据，再清除条件，不显示。所以要去掉,，去掉暂时没发现什么bug,错了，发现了以下bug*/
        /*加载下一页的时候，向上拖动再向下滑动会出现下拉刷新*/
        /*加入showEmpty 就是为了反之上面出现的那种情况*/
//        == EXIT_PULL_REFRESH) {
        if (showEmpty) {
            showEmpty = false;
            removeView(emptyView);
            removeView(showView);
            addView(showView);
        }
//        }
    }

    public final void setOnRefreshLister(OnRefreshLister onRefreshLister) {
        this.mOnRefreshLister = onRefreshLister;
    }

    public void addView(View view, int index,
                        LayoutParams layoutParams) {
        this.childView = view;
        super.addView(view, index, layoutParams);
    }

    /**
     * 手动刷新，由外部调用
     */
    public final void startRefresh() {
        if (mAdapterView != null) {
            mAdapterView.setSelection(0);
        }
        scrollTo(0, this.animationView.getTop() - (int) (0.5F + 15.0F * getResources().getDisplayMetrics().density));
        this.refreshStatue = MRefreshStatue.REFRESH_PREPARE;
        scrollInvalidate();
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(0, this.mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 获取需要刷新的内部控件
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        View view;
        for (int i = 0; i < count; i++) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
//            TLog.e("must contain a AdapterView or ScrollView in this layout!");
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
        return super.getChildDrawingOrder(paramInt1, paramInt2);
    }

    public boolean onDown(MotionEvent paramMotionEvent) {
        return true;
    }

    public boolean onFling(MotionEvent event1,
                           MotionEvent event2, float velocityX, float velocityY) {
        return false;
    }

    private boolean isRefreshViewScroll(int deltaY) {
        //对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return true;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) == data_5) {//这里之前用3可以判断,但现在不行,还没找到原因
                    if (refreshStatue == MRefreshStatue.REFRESH_ING) {
                        int loadingTop = getScrollY();
                        int loadingHeight = this.animationView.getHeight();
                        int refreshHeight = this.refreshHead.getHeight();
                        if (loadingTop > refreshHeight - loadingHeight)
                            return true;
                        else
                            return false;
                    }
                    return true;
                }

            }
        }else if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                return true;
            }
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        float rawY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPointY = rawY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRefreshViewScroll((int) (rawY - downPointY))) {
                    event.setAction(MotionEvent.ACTION_DOWN);
                    onTouchEvent(event);
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return true;
                }
                break;
        }
        return false;
    }

    protected void onLayout(boolean changed, int l, int t,
                            int r, int b) {
        View mView = this.refreshHead;
        int viewWidth = this.refreshHead.getMeasuredWidth();
        int viewHeight = this.refreshHead.getMeasuredHeight();
        mView.layout(0, 0, viewWidth, viewHeight);
        this.childView.layout(0, viewHeight, this.childView.getMeasuredWidth(), viewHeight
                + this.childView.getMeasuredHeight());
        if (this.isFirst) {
            refreshStatue = MRefreshStatue.REFRESH_NORMAL;
            scrollTo(0, this.refreshHead.getHeight());
            this.isFirst = false;
        }
    }

    public void onLongPress(MotionEvent paramMotionEvent) {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        this.refreshHead.measure(widthMeasureSpec, heightMeasureSpec);
        this.emptyView.measure(widthMeasureSpec, heightMeasureSpec);
        this.childView.measure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2,
                            float distanceX, float distanceY) {
        float y_dis;
        y_dis = distanceY * (float) (0.7D * (getScrollY() * 1.0f / this.refreshHead.getHeight()));
        if (y_dis + getScrollY() >= this.refreshHead.getHeight())
            y_dis = this.refreshHead.getHeight() - getScrollY();
        scrollBy(0, (int) y_dis);
        int loadingTop = this.animationView.getTop();
        if ((getScrollY() > loadingTop) && (this.refreshStatue == MRefreshStatue.REFRESH_PREPARE)) {
            this.refreshStatue = MRefreshStatue.REFRESH_NORMAL;
            this.textTips.setText("下拉可以刷新");
        }
        if ((getScrollY() < loadingTop) && (this.refreshStatue == MRefreshStatue.REFRESH_NORMAL)) {
            this.refreshStatue = MRefreshStatue.REFRESH_PREPARE;
            this.textTips.setText("松开可以刷新");
        }
        if (this.refreshStatue == MRefreshStatue.REFRESH_NORMAL) {
            this.textTips.setText("下拉可以刷新");
        }

        return true;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if ((motionEvent.getAction() == MotionEvent.ACTION_UP)
                || (motionEvent.getAction() == MotionEvent.ACTION_CANCEL)) {
            scrollInvalidate();
            return true;
        }
        return this.mGesture.onTouchEvent(motionEvent);
    }

    /**
     * 刷新触发接口
     */
    public static abstract interface OnRefreshLister {
        public abstract void refresh();
    }
}