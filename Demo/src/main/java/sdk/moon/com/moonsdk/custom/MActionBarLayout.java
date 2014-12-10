package sdk.moon.com.moonsdk.custom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import sdk.moon.com.moonsdk.R;

/**
 * Created by moon.zhong on 2014/10/31.
 */
public class MActionBarLayout extends RelativeLayout implements View.OnClickListener {
    private Context context;
    private RelativeLayout actionBar;
    private RelativeLayout backBtn;
    private TextView title;
    private TextView confirmBtn;
    private ImageView backImg ;
    private LayoutInflater inflater;
    private View layoutView;
    private View bottomLine ;
    private OnClickListener backOnclickListener;
    private OnClickListener confirmOnclickListener;

    public MActionBarLayout(Context context) {
        super(context);
        initView(context);
    }

    public MActionBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MActionBarLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);

    }

    private void initView(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        layoutView = inflater.inflate(R.layout.actionbar_layout, null);
        actionBar = findView(layoutView, R.id.barLayout);
        backBtn = findView(layoutView, R.id.barBack);
        title = findView(layoutView, R.id.barLabel);
        confirmBtn = findView(layoutView, R.id.barConfirm);
        backImg = findView(layoutView,R.id.backImg) ;
        bottomLine = findView(layoutView,R.id.bottomLine) ;
        backBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        addView(layoutView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private <K extends View> K findView(View view, int id) {
        return (K) view.findViewById(id);
    }

    public void setBackOnclickListener(OnClickListener backOnclickListener) {
        this.backOnclickListener = backOnclickListener;
    }

    public void setConfirmOnclickListener(OnClickListener confirmOnclickListener) {
        this.confirmOnclickListener = confirmOnclickListener;
    }

    public void setBarBackground(int resId) {
        actionBar.setBackgroundResource(resId);
    }

    public void setBarBackgroundColor(int color) {
        actionBar.setBackgroundColor(color);
    }

    public void setTitleTextSize(int size) {
        title.setTextSize(size);
    }

    public void setTitleTextColor(int color) {
        title.setTextColor(color);
    }

    public void setTitleText(CharSequence charSequence) {
        title.setText(charSequence);
    }

    public void setTitleText(int resId) {
        title.setText(resId);
    }

    public void setBackImgSrc(int resId)
    {
        backImg.setImageResource(resId);
    }

    public void hideConfirmBtn()
    {
        confirmBtn.setVisibility(View.GONE);
    }

    public void setConfirmBtnText(CharSequence charSequence) {
        confirmBtn.setText(charSequence);
    }

    public void setConfirmBtnText(int resId) {
        confirmBtn.setText(resId);
    }

    public TextView getConfirmBtn() {
        return confirmBtn;
    }

    public void setBottomLineColor(int color){
        bottomLine.setBackgroundColor(color);
    }

    public void setBackBtnBackground(int resId){
        backBtn.setBackgroundResource(resId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.barBack:
                if (backOnclickListener != null) {
                    backOnclickListener.onClick(v);
                } else {
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
                break;
            case R.id.barConfirm:
                if (confirmOnclickListener != null)
                    confirmOnclickListener.onClick(v);
                break;
        }
    }
}
