package sdk.moon.com.moonsdk.abst;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by moon.zhong on 2014/10/20.
 * 所有Adapter的父类
 * 简化子类的操作
 */
public abstract class MBaseAdapter<T,K> extends BaseAdapter{
    private List<T> dataList ;
    private LayoutInflater inflater ;

    public MBaseAdapter(Context context, List<T> dataList) {
        this.inflater = LayoutInflater.from(context) ;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        K viewHolder = null ;
        if(convertView == null){
            convertView = createView(inflater, position) ;
            viewHolder = createHolder(convertView,position) ;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (K)convertView.getTag() ;
        }
        showData(viewHolder,position) ;
        return convertView;
    }
    public <M extends View> M findView(View view ,int id) {
        return (M) view.findViewById(id) ;
    }
    public abstract View createView(LayoutInflater inflater, int position) ;

    public abstract K createHolder(View view, int position) ;

    public abstract void showData(K viewHolder,int position) ;

//    public <V extends View> V findView(View view, int id){
//        return (V) view.findViewById(id) ;
//    }

}
