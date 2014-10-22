package sdk.moon.com.moonsdk.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.moon.sdk.ormlite.entity.MContactBean;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseAdapter;
import sdk.moon.com.moonsdk.application.MApplication;

/**
 * Created by moon on 2014/10/22 0022.
 */
public class MContactAdapter extends MBaseAdapter<MContactBean,MContactAdapter.ViewHolder> {

    public MContactAdapter(List<MContactBean> dataList) {
        super(MApplication.getContext(), dataList);
    }

    @Override
    public View createView(LayoutInflater inflater, int position) {
        View view = inflater.inflate(R.layout.listview_contact_item, null) ;
        return view;
    }

    @Override
    public ViewHolder createHolder(View view, int position) {
        ViewHolder viewHolder = new ViewHolder() ;
        viewHolder.txtName = findView(view,R.id.contactName) ;
        viewHolder.txtTel = findView(view, R.id.contactTel) ;
        return viewHolder;
    }

    @Override
    public void showData(ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText(getItem(position).getName());
        viewHolder.txtTel.setText(getItem(position).getTel());
    }

    class ViewHolder{
        TextView txtName ;
        TextView txtTel ;
    }
}
