package sdk.moon.com.moonsdk.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import sdk.moon.com.moonsdk.R;

/**
 * Created by moon.zhong on 2014/10/22.
 * 创建自定义dialog
 */
public class MDialogBuilder extends AlertDialog.Builder {
    private Context mContext ;
    public MDialogBuilder(Context context) {
        super(context);
        mContext = context ;
    }

    public MDialogBuilder(Context context, int theme) {
        super(context, theme);
        mContext = context ;
    }

    @Override
    public AlertDialog show() {
        AlertDialog dialog = super.show() ;
        final int titleId = mContext.getResources().getIdentifier("alertTitle","id","android") ;
        if(titleId != 0){
            final TextView textTitle = (TextView)dialog.findViewById(titleId) ;
            textTitle.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        final int dividerId = mContext.getResources().getIdentifier("titleDivider", "id", "android") ;
        if(dividerId != 0){
            final View view = dialog.findViewById(dividerId) ;
            view.setBackgroundResource(R.color.green);
        }
        return dialog;
    }
}
