package sdk.moon.com.moonsdk.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import sdk.moon.com.moonsdk.custom.MDialogBuilder;

/**
 * Created by moon.zhong on 2014/10/22.
 * 创建对话框工具类
 */
public class MDialogUtil {

    public static void showViewDialog(Context context,String title,View view,DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new MDialogBuilder(context) ;
        builder.setTitle(title)
               .setNegativeButton("取消",null)
               .setPositiveButton("确定",listener)
               .setView(view)
               .show();
    }
}
