package sdk.moon.com.moonsdk.custom;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;


public class MParentFocusEditView extends EditText {
    private Runnable mUpdateDrawableStateRunnable = new Runnable() {
        @Override
        public void run() {
            updateFocusedState();
        }
    };
    public MParentFocusEditView(Context context) {
        super(context);
    }

    public MParentFocusEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MParentFocusEditView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        postUpdateFocusedState();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        postUpdateFocusedState();
    }

    private void updateFocusedState(){
        boolean focused = hasFocus();
        ViewGroup parent = (ViewGroup)getParent();
        Drawable drawable = parent.getBackground();
        if(drawable != null)
            drawable.setState(focused ? FOCUSED_STATE_SET : EMPTY_STATE_SET);
    }

    private void postUpdateFocusedState(){
        post(mUpdateDrawableStateRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mUpdateDrawableStateRunnable);
    }
}
