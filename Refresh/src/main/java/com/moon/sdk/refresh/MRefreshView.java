package com.moon.sdk.refresh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
    private View refreshView;
    private View refreshContent;
    private View childView;
    private View showView;
    private ImageView loadingView;
    private TextView textView;
    private Scroller scroller;
    private GestureDetector mGesture;
    private int data_1000 = 1000;
    private int data_500 = 500;
    private float yDown;
    private int data_5 = 0;
    private boolean isShow = true;
    private int pull_refresh_statue = NONE_PULL_REFRESH;
    private final static int NONE_PULL_REFRESH = 0;   //正常状态
    private final static int ENTER_PULL_REFRESH = 1;  //进入下拉刷新状态
    private final static int OVER_PULL_REFRESH = 2;   //进入松手刷新状态
    private final static int EXIT_PULL_REFRESH = 3;     //松手后反弹后加载状态
    private OnRefreshLister mOnRefreshLister;
    private Context mContext;
    private AdapterView<?> mAdapterView;
    private ScrollView mScrollView;

    private int upDown = 0;
    private final static int UP_TYPE = 0;   //正常状态
    private final static int DOWN_TYPE = 1;  //进入下拉刷新状态
    private boolean showEmpty = false;

    //当正在刷新时，可以上滑隐藏，下滑再次出现。
    private boolean canHide = true;

    public MRefreshView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.refreshView = ((Activity) context).getLayoutInflater().inflate(
                R.layout.refresh_head, null);
        this.refreshContent = ((Activity) context).getLayoutInflater().inflate(
                R.layout.refresh_content, null);
        addView(this.refreshView);
        this.loadingView = ((ImageView) findViewById(R.id.progress));
        this.textView = ((TextView) findViewById(R.id.text));
        this.scroller = new Scroller(context);
        this.mGesture = new GestureDetector(this);
        this.mContext = context;
        DisplayMetrics dm = getResources().getDisplayMetrics();
        data_5 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data_5, dm);
        initContentAdapterView();
    }

    private void setAnimation(boolean enable) {
        if (enable) {
            this.loadingView.setVisibility(View.VISIBLE);
            ((AnimationDrawable) this.loadingView.getDrawable()).start();
        } else {
            ((AnimationDrawable) this.loadingView.getDrawable()).stop();
            this.loadingView.setVisibility(View.INVISIBLE);
        }

    }

    public final void setCanHide(boolean canHide) {
        this.canHide = canHide;
    }

    private void scrollInvalidate() {
        int scrollY = getScrollY();
        if (this.pull_refresh_statue == OVER_PULL_REFRESH) {
            this.pull_refresh_statue = EXIT_PULL_REFRESH;
            int refreshHeight = this.loadingView.getTop() - scrollY;
            int duration = (int) (this.data_1000 * Math.abs(refreshHeight) / this.refreshView
                    .getHeight());
            this.scroller.startScroll(0, scrollY, 0, refreshHeight, duration);
            invalidate();
            setAnimation(true);
            if (this.mOnRefreshLister != null)
                this.mOnRefreshLister.refresh();
            this.textView.setText("");
        } else if (pull_refresh_statue == NONE_PULL_REFRESH || this.pull_refresh_statue == ENTER_PULL_REFRESH) {
            int stopHeight = this.refreshView.getHeight() - scrollY;
            this.scroller.startScroll(0, scrollY, 0, stopHeight, this.data_500);
            invalidate();
            pull_refresh_statue = NONE_PULL_REFRESH;
            setAnimation(false);
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

        if (this.pull_refresh_statue == EXIT_PULL_REFRESH) {
            this.pull_refresh_statue = NONE_PULL_REFRESH;
            scrollInvalidate();
        }
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
            removeView(refreshContent);
            addView(this.refreshContent);
        }
    }

    public final void hideEmptyView() {
        /* 瀑布流筛选时，无数据，再清除条件，不显示。所以要去掉,if (this.pull_refresh_statue == EXIT_PULL_REFRESH)，去掉暂时没发现什么bug,错了，发现了以下bug*/
        /*加载下一页的时候，向上拖动再向下滑动会出现下拉刷新*/
        /*加入showEmpty 就是为了反之上面出现的那种情况*/
//        if (this.pull_refresh_statue == EXIT_PULL_REFRESH) {
        if (showEmpty) {
            showEmpty = false;
            removeView(refreshContent);
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

    public final void startRefresh() {
        if (mAdapterView != null) {
            mAdapterView.setSelection(0);
        }
        scrollTo(0, this.loadingView.getTop() - (int) (0.5F + 15.0F * getResources().getDisplayMetrics().density));
        this.pull_refresh_statue = OVER_PULL_REFRESH;
        scrollInvalidate();
    }

    public void computeScroll() {
        if (this.scroller.computeScrollOffset()) {
            scrollTo(0, this.scroller.getCurrY());
            invalidate();
        }
    }

    private void initContentAdapterView() {
        int count = getChildCount();
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
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
        if (pull_refresh_statue == EXIT_PULL_REFRESH) {
            if (!this.canHide)
                return false;
            else {
                return true;
            }
        }
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
//                TLog.d("==========top===============" + top);
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) == data_5) {//这里之前用3可以判断,但现在不行,还没找到原因
                    if (pull_refresh_statue == EXIT_PULL_REFRESH) {
                        int loadingTop = getScrollY();
                        int loadingHeight = this.loadingView.getHeight();
                        int refreshHeight = this.refreshView.getHeight();
                        if (loadingTop > refreshHeight - loadingHeight)
                            return true;
                        else
                            return false;
                    }
                    return true;
                }

            } else if (deltaY < 0) {
                if (pull_refresh_statue == EXIT_PULL_REFRESH) {
                    int loadingTop = getScrollY();
                    int refreshHeight = this.refreshView.getHeight();
                    if (loadingTop < refreshHeight)
                        return true;
                    else
                        return false;
                }
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    return false;
                }
                return false;
            }
        }
        // 对于ScrollView
        else if (mScrollView != null) {
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
        float y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) (y - yDown);
                if (deltaY > 0) {
                    upDown = UP_TYPE;
                }
                if (deltaY < 0) {
                    upDown = DOWN_TYPE;
                }
                if (isRefreshViewScroll(deltaY)) {
                    event.setAction(0);
                    onTouchEvent(event);
                    ((ViewGroup) getParent()).requestDisallowInterceptTouchEvent(true);
                    return true;
                }

                break;
        }

        return false;
    }

    protected void onLayout(boolean changed, int l, int t,
                            int r, int b) {
        View mView = this.refreshView;
        int view_width = this.refreshView.getMeasuredWidth();
        int view_height = this.refreshView.getMeasuredHeight();
        mView.layout(0, 0, view_width, view_height);

        this.childView.layout(0, view_height, this.childView.getMeasuredWidth(), view_height
                + this.childView.getMeasuredHeight());
        if (this.isShow) {
            scrollTo(0, this.refreshView.getHeight());
            this.isShow = false;
        }
    }

    public void onLongPress(MotionEvent paramMotionEvent) {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i1 = MeasureSpec.getSize(widthMeasureSpec);
        int i2 = MeasureSpec.getSize(heightMeasureSpec);
        this.refreshView.measure(widthMeasureSpec, heightMeasureSpec);
        this.refreshContent.measure(widthMeasureSpec, heightMeasureSpec);
        this.childView.measure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(i2, MeasureSpec.EXACTLY));
        setMeasuredDimension(i1, i2);
    }

    public boolean onScroll(MotionEvent motionEvent1, MotionEvent motionEvent2,
                            float distanceX, float distanceY) {
        float y_dis;
        y_dis = distanceY * (float) (0.6D * (getScrollY() * 1.0f / this.refreshView.getHeight()));
        if (y_dis + getScrollY() >= this.refreshView.getHeight())
            y_dis = this.refreshView.getHeight() - getScrollY();
        scrollBy(0, (int) y_dis);
        int loadingTop = this.loadingView.getTop();
        if ((getScrollY() > loadingTop) && (this.pull_refresh_statue == OVER_PULL_REFRESH)) {
            this.pull_refresh_statue = ENTER_PULL_REFRESH;
            this.textView.setText("下拉可以刷新");
        }

        if ((getScrollY() < loadingTop) && (this.pull_refresh_statue == ENTER_PULL_REFRESH)) {
            this.pull_refresh_statue = OVER_PULL_REFRESH;
            this.textView.setText("松开可以刷新");
        }
        if (this.pull_refresh_statue == NONE_PULL_REFRESH) {
            this.pull_refresh_statue = ENTER_PULL_REFRESH;
            this.textView.setText("下拉可以刷新");
        }

        return true;
    }

    public void onShowPress(MotionEvent e) {
    }

    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (pull_refresh_statue == EXIT_PULL_REFRESH) {
            if (this.canHide)
                return true;

        }
        int i1 = 1;
        if ((motionEvent.getAction() == i1)
                || (motionEvent.getAction() == 3)) {
            scrollInvalidate();
            return true;
        }
        return this.mGesture.onTouchEvent(motionEvent);
    }

    public static abstract interface OnRefreshLister {
        public abstract void refresh();
    }
}