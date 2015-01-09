package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.moon.litepalsdk.dao.MPublish;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseAdapter;

/**
 * Created by moon.zhong on 2015/1/9.
 */
public class MPublishAdapter extends MBaseAdapter<MPublish, MPublishAdapter.ViewHolder> {

    public MPublishAdapter(Context context, List<MPublish> dataList) {
        super(context, dataList);
    }

    @Override
    public View createView(LayoutInflater inflater, int position) {
        return inflater.inflate(R.layout.item_litepal_one_many, null);
    }

    @Override
    public ViewHolder createHolder(View view, int position) {
        ViewHolder viewHolder = new ViewHolder() ;
        viewHolder.txtName = findView(view, R.id.name) ;
        viewHolder.txtAddress = findView(view,R.id.address) ;
        return viewHolder;
    }

    @Override
    public void showData(ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText(getItem(position).getName());
        viewHolder.txtAddress.setText(getItem(position).getAddress());
    }

    public static class ViewHolder{
        TextView txtName ;
        TextView txtAddress ;
    }
}
