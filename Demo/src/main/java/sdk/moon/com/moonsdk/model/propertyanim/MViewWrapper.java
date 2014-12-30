package sdk.moon.com.moonsdk.model.propertyanim;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by moon.zhong on 2014/12/30.
 */
public class MViewWrapper {
    private View view ;
    private int width ;
    private int height ;
    private float x,y ;
    private float translationY ;
    private float translationX ;
    public MViewWrapper(View view) {
        this.view = view ;
    }

    public int getWidth() {
        this.width = view.getLayoutParams().width ;
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.view.getLayoutParams().width = this.width;
        this.view.requestLayout();
    }

    public int getHeight() {
        this.height = view.getLayoutParams().height ;
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.view.getLayoutParams().height = this.height ;
        this.view.requestLayout();
    }

    public void setScaleType(ImageView.ScaleType scaleType){
        ((ImageView)view).setScaleType(scaleType);
    }

    public float getX() {
        x = this.view.getX() ;
        return x;
    }

    public void setX(float x) {
        this.x = x;
        this.view.setX(x);
    }

    public float getY() {
        y = this.view.getY() ;
        return y;
    }

    public void setY(float y) {
        this.y = y;
        this.view.setY(this.y);
    }

    public View getView(){
        return view ;
    }

    public float getTranslationY() {
        this.translationY = this.view.getTranslationY() ;
        return translationY;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
        this.view.setTranslationY(this.translationY);
    }
    public float getTranslationX() {
        this.translationX = this.view.getTranslationX() ;
        return translationX;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
        this.view.setTranslationX(this.translationX);
    }


}
