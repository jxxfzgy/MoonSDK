package com.moon.sdk.imgloader.displayer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * Created by Administrator on 2014/10/12 0012.
 * 圆形图片显示器
 * 经过测试，只适合正方形的图片
 * 如果是矩形，则，需要知道长和宽，
 * 要不然会出现拉升现象，
 * 虽然调试了很久，但还是 没解决这个
 * 问题。
 */
public class MCircleDisplay implements BitmapDisplayer {
    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(new CircleDrawable(bitmap)) ;
    }

    public static class CircleDrawable extends Drawable {
        private final BitmapShader bitmapShader ;
        private final Paint paint ;
        private final RectF mBitmapRect ;
        private final RectF mRect = new RectF() ;
        private int radius ;
        public CircleDrawable(Bitmap bitmap) {
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF (0, 0,  bitmap.getWidth(),  bitmap.getHeight());
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        public void onBoundsChange(Rect bounds) {
            mRect.set(bounds);
            // Resize the original bitmap to fit the new bound
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
            bitmapShader.setLocalMatrix(shaderMatrix);
            int minSize = (int) Math.min(mRect.height(), mRect.width());
            radius = minSize >> 1;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(radius, radius, radius, paint);
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
