package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseAdapter;

/**
 * Created by gyzhong on 15/1/10.
 */
public class MWandAdapter extends MBaseAdapter<String,MWandAdapter.ViewHolder> {

    public MWandAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public View createView(LayoutInflater inflater, int position) {

        return inflater.inflate(R.layout.item_wandoujia,null);
    }

    @Override
    public ViewHolder createHolder(View view, int position) {
        ViewHolder viewHolder = new ViewHolder() ;
        viewHolder.txtInfo = findView(view,R.id.txtInfo) ;

        return viewHolder;
    }

    @Override
    public void showData(ViewHolder viewHolder, int position) {
        viewHolder.txtInfo.setText(getItem(position));
    }

    protected class ViewHolder{
        TextView txtInfo ;
    }
}
