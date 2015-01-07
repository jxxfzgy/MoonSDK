package sdk.moon.com.moonsdk.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by moon.zhong on 2014/10/22.
 * 公共模块的工具类
 */
public class MComUtil {

    public static int[] getScreenSize(Context context){
        int[] ints = {0,0} ;
        DisplayMetrics displayMetrics = new DisplayMetrics() ;
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        ints[0] = displayMetrics.widthPixels ;
        ints[1] = displayMetrics.heightPixels ;
        return  ints;
    }
}
