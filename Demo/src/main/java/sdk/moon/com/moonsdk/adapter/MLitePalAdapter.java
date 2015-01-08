package sdk.moon.com.moonsdk.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moon.litepalsdk.dao.MBook;

import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseAdapter;
import stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by moon.zhong on 2015/1/8.
 */
public class MLitePalAdapter extends MBaseAdapter<MBook,MLitePalAdapter.ViewHolder> implements StickyListHeadersAdapter {

    public MLitePalAdapter(Context context, List<MBook> dataList) {
        super(context, dataList);
    }

    @Override
    public View createView(LayoutInflater inflater, int position) {
        View view = inflater.inflate(R.layout.item_litepal,null) ;
        return view;
    }

    @Override
    public ViewHolder createHolder(View view, int position) {
        ViewHolder viewHolder ;
        viewHolder = new ViewHolder() ;
        viewHolder.txtName = findView(view,R.id.name) ;
        viewHolder.txtPrice = findView(view,R.id.price) ;
        viewHolder.txtPublish = findView(view,R.id.publish) ;
        return viewHolder;
    }

    @Override
    public void showData(ViewHolder viewHolder, int position) {
        viewHolder.txtName.setText(getItem(position).getName());
        viewHolder.txtPrice.setText(getItem(position).getPrice()+"元");
        viewHolder.txtPublish.setText(getItem(position).getPublish());
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderView headerView ;
        if(convertView != null){
            headerView = (HeaderView)convertView.getTag() ;
        }else {
            headerView = new HeaderView() ;
            convertView = getInflater().inflate(R.layout.item_litepal_header,null) ;
            headerView.txtPinyin = findView(convertView,R.id.pinyin) ;
            convertView.setTag(headerView);
        }
        headerView.txtPinyin.setText(getItem(position).getPinYing().substring(0,1));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getPinYing().charAt(0);
    }

    protected class ViewHolder{
        TextView txtName ;
        TextView txtPublish ;
        TextView txtPrice ;
    }

    protected class HeaderView{
        TextView txtPinyin ;
    }
}
