package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseViewAdapter;
import sdk.moon.com.moonsdk.entity.MLoopViewBean;

/**
 * Created by Administrator on 2014/10/14 0014.
 */
public class MLoopAdapter extends MBaseViewAdapter<MLoopViewBean> {

    public MLoopAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.viewpager_loop_item, null) ;
        return view;
    }

    @Override
    public void showData(View view ,int position) {
        ImageView imageView = findView(view,R.id.item_loop_img) ;
        imageView.setImageResource(getItem(position).drawableId);
    }
}
