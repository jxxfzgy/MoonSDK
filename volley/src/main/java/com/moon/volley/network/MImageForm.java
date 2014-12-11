package com.moon.volley.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MImageForm implements MFormItem {
    private String fileName ;
    private String filePath ;
    private int width ,height;

    public MImageForm(String fileName, String filePath, int width, int height) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.width = width;
        this.height = height;
    }

    public MImageForm() {
    }

    @Override
    public String getName() {
        return this.fileName;
    }

    @Override
    public String getMimeType() {
        return "image/jpeg";
    }

    @Override
    public String getFilePath() {
        return "1416797888446.jpg";
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            Bitmap bitmap = createBestSizeBitmap(filePath, width, height);
            bitmap=fixAutoOrientation( filePath,bitmap);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        } catch (OutOfMemoryError e) {

        }
        return bos.toByteArray();
    }



    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,
                                           int actualSecondary) {
        // If no dominant value at all, just return the actual.
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {
            double ratio = (double) maxSecondary / (double) actualSecondary;
            return (int) (actualPrimary * ratio);
        }

        if (maxSecondary == 0) {
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;
        if (resized * ratio > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }


    public static Bitmap createBestSizeBitmap(String imageFileName, int maxWidth, int maxHeight) {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap = null;
        if (maxWidth == 0 && maxHeight == 0) {
            decodeOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            bitmap = BitmapFactory.decodeFile(imageFileName, decodeOptions);
        } else {
            // If we have to resize this image, first get the natural bounds.
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imageFileName, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;

            // Then compute the dimensions we would ideally like to decode to.
            int desiredWidth = getResizedDimension(maxWidth, maxHeight,
                    actualWidth, actualHeight);
            int desiredHeight = getResizedDimension(maxHeight, maxWidth,
                    actualHeight, actualWidth);

            // Decode to the nearest power of two scaling factor.
            decodeOptions.inJustDecodeBounds = false;
            // TODO(ficus): Do we need this or is it okay since API 8 doesn't support it?
            // decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
            decodeOptions.inSampleSize =
                    findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap =
                    BitmapFactory.decodeFile(imageFileName, decodeOptions);

            // If necessary, scale down to the maximal acceptable size.
            if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth ||
                    tempBitmap.getHeight() > desiredHeight)) {
                bitmap = Bitmap.createScaledBitmap(tempBitmap,
                        desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }
        return bitmap;
    }

    public static int findBestSampleSize(
            int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }
    public static Bitmap fixAutoOrientation(String path,Bitmap bitmap){
        int deg = 0 ;
        try {
            ExifInterface exifInterface = new ExifInterface(path) ;
            String rotate = exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION) ;
            int rotateValue = Integer.parseInt(rotate) ;
            switch (rotateValue){
                case ExifInterface.ORIENTATION_ROTATE_180:
                    deg = 180 ;
                    break ;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    deg = 90 ;
                    break ;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    deg = 270 ;
                    break ;
                default:
                    deg = 0 ;
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matrix matrix = new Matrix() ;
        matrix.postRotate(deg) ;
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bitmap ;
    }
}
