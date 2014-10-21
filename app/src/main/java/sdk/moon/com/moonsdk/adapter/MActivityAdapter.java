package sdk.moon.com.moonsdk.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBExpandAdapter;
import sdk.moon.com.moonsdk.application.MApplication;
import sdk.moon.com.moonsdk.entity.MActivityBean;

/**
 * Created by moon.zhong on 2014/10/20.
 */
public class MActivityAdapter extends MBExpandAdapter<MActivityBean, MActivityAdapter.ViewHolder> {

    /*itme选择回调接口*/
    private OnItemSelect OnItemSelect;
    /*上一次选择的view*/
    private View lastTextView;
    /*上一次选择的 item */
    private int lastPosition = -1, lastItem = -1 ;

    public MActivityAdapter(List dataList) {
        super(MApplication.getContext(), dataList);
    }

    public void setOnItemSelect(OnItemSelect OnItemSelect) {
        this.OnItemSelect = OnItemSelect;
    }

    @Override
    public View createView(LayoutInflater inflater, int position) {
        View view = inflater.inflate(R.layout.listview_expand_item, null);
        return view;
    }

    @Override
    public ViewHolder createHolder(View view, final int position) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.functionName = findView(view, R.id.expandItem);
        List<TextView> textViews = new ArrayList<TextView>();
        List<MActivityBean> activityBeanList = getItem(position).getSubBean();
        if (activityBeanList != null && activityBeanList.size() > 0) {
            int length = activityBeanList.size();
            int lineNum = (length + 2) / 3;
            LinearLayout expandLinearLayout = findView(view, R.id.expandContext);
            for (int i = 0; i < lineNum; i++) {

                LinearLayout linearLayout = getLineLinerLayout();
                expandLinearLayout.addView(linearLayout);
                if (i * 3 < length) {
                    final int itemNum = i * 3 ;
                    TextView textView1 = getItemTextView(false);
                    linearLayout.addView(textView1);
                    textViews.add(textView1);
                    textView1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (OnItemSelect != null) {
                                OnItemSelect.select(position, itemNum);
                            }
                            if (lastTextView != null){
                                ((TextView) lastTextView).setTextColor(MApplication.getContext().getResources().getColor(R.color.dark_black));
                                lastTextView.setBackgroundResource(R.drawable.main_item_unselector);
                            }
                            ((TextView) v).setTextColor(MApplication.getContext().getResources().getColor(R.color.green));
                            v.setBackgroundResource(R.drawable.main_item_selector);
                            lastPosition = position ;
                            lastItem = itemNum ;
                            lastTextView = v;

                        }
                    });
                } else {
                    TextView textView1_1 = getEmptyTextView();
                    linearLayout.addView(textView1_1);
                }
                if (i * 3 + 1 < length) {
                    final int itemNum = i * 3 + 1;
                    TextView textView2 = getItemTextView(true);
                    linearLayout.addView(textView2);
                    textViews.add(textView2);
                    textView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (OnItemSelect != null) {
                                OnItemSelect.select(position, itemNum);
                            }
                            if (lastTextView != null){
                                ((TextView) lastTextView).setTextColor(MApplication.getContext().getResources().getColor(R.color.dark_black));
                                lastTextView.setBackgroundResource(R.drawable.main_item_unselector);
                            }
                            ((TextView) v).setTextColor(MApplication.getContext().getResources().getColor(R.color.green));
                            v.setBackgroundResource(R.drawable.main_item_selector);
                            lastPosition = position ;
                            lastItem = itemNum ;
                            lastTextView = v;
                        }
                    });
                } else {
                    TextView textView2_2 = getEmptyTextView();
                    linearLayout.addView(textView2_2);
                }
                if (i * 3 + 2 < length) {
                    final int itemNum = i * 3 + 2;
                    TextView textView3 = getItemTextView(true);
                    linearLayout.addView(textView3);
                    textViews.add(textView3);
                    textView3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (OnItemSelect != null) {
                                OnItemSelect.select(position, itemNum);
                            }
                            if (lastTextView != null){
                                ((TextView) lastTextView).setTextColor(MApplication.getContext().getResources().getColor(R.color.dark_black));
                                lastTextView.setBackgroundResource(R.drawable.main_item_unselector);
                            }
                            ((TextView) v).setTextColor(MApplication.getContext().getResources().getColor(R.color.green));
                            v.setBackgroundResource(R.drawable.main_item_selector);
                            lastPosition = position ;
                            lastItem = itemNum ;
                            lastTextView = v;
                        }
                    });
                } else {
                    TextView textView3_3 = getEmptyTextView();
                    linearLayout.addView(textView3_3);
                }
            }
            viewHolder.textViewList = textViews;
        }
        return viewHolder;
    }

    @Override
    public void showData(ViewHolder viewHolder, int position) {
        if (getItem(position).isExpand()) {
            viewHolder.functionName.setTextColor(MApplication.getContext().getResources().getColor(R.color.green));
        }
        viewHolder.functionName.setText(getItem(position).getFunctionName());
        List<MActivityBean> activityBeanList = getItem(position).getSubBean();
        if (activityBeanList != null && activityBeanList.size() > 0) {
            int length = activityBeanList.size();
            for (int i = 0; i < length; i++) {
                viewHolder.textViewList.get(i).setText(activityBeanList.get(i).getFunctionName());
                if(position == lastPosition){
                    viewHolder.textViewList.get(lastItem).setTextColor(MApplication.getContext().getResources().getColor(R.color.green));
                    viewHolder.textViewList.get(lastItem).setBackgroundResource(R.drawable.main_item_selector);
                    lastTextView = viewHolder.textViewList.get(lastItem) ;
                }
            }
        }
    }


    public class ViewHolder {
        TextView functionName;
        List<TextView> textViewList;
    }

    private LinearLayout getLineLinerLayout() {
        LinearLayout lineLayout = new LinearLayout(MApplication.getContext());
        LinearLayout.LayoutParams lineLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        lineLayout.setPadding(0, 0, 0, (int) MApplication.getContext().getResources().getDimension(R.dimen.main_layout_margin));
        lineLayout.setLayoutParams(lineLayoutParams);
        return lineLayout;
    }

    private TextView getItemTextView(boolean margin) {
        TextView textView = new TextView(MApplication.getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams((int) MApplication.getContext().getResources().getDimension(R.dimen.main_item_txt_width),
                (int) MApplication.getContext().getResources().getDimension(R.dimen.main_item_txt_height));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(MApplication.getContext().getResources().getColor(R.color.dark_black));
        textView.setTextSize(18);
        textView.setBackgroundResource(R.drawable.main_item_unselector);
        if (margin)
            textParams.leftMargin = (int) MApplication.getContext().getResources().getDimension(R.dimen.main_layout_margin);
        textView.setLayoutParams(textParams);
        return textView;
    }

    private TextView getEmptyTextView() {
        TextView textView = new TextView(MApplication.getContext());
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams((int) MApplication.getContext().getResources().getDimension(R.dimen.main_item_txt_width),
                (int) MApplication.getContext().getResources().getDimension(R.dimen.main_item_txt_height));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(0x00fffff);
        textView.setBackgroundColor(0x00fffff);
        textParams.leftMargin = (int) MApplication.getContext().getResources().getDimension(R.dimen.main_layout_margin);
        textView.setLayoutParams(textParams);
        return textView;
    }

    public interface OnItemSelect {
        public void select(int position, int item);
    }
}
