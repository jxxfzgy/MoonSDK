package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.moon.sdk.loopviewpager.loop1.MBaseLoopAdapter;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.entity.MLoopViewBean;

/**
 * Created by moon.zhong on 2014/10/15.
 */
public class MBaseLoopMaxAdapter extends MBaseLoopAdapter<MLoopViewBean> {

    public MBaseLoopMaxAdapter(Context context, List<MLoopViewBean> mLoopViewBeans) {
        super(context, mLoopViewBeans);
    }

    @Override
    public View createView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.viewpager_loop_item, null) ;
        return view;
    }

    @Override
    public void showData(View view, int position) {
        ImageView imageView = findView(view,R.id.item_loop_img) ;
        imageView.setImageResource(getItem(position).drawableId);
    }
}
