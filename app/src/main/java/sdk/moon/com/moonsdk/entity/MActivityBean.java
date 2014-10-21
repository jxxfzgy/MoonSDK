package sdk.moon.com.moonsdk.entity;

import java.util.List;

/**
 * Created by moon.zhong on 2014/10/20.
 * 每个需要跳转的activity信息集合
 */
public class MActivityBean {
    /*名称*/
    private String functionName ;
    /*跳转的activity*/
    private Class activityName ;
    /*包含子activity*/
    private List<MActivityBean> subBean ;
    /*该项是否已经展开*/
    private boolean expand ;
    /*该item是否被选中*/
    private boolean select ;

    public boolean isExpand() {
        return expand;
    }

    public MActivityBean setExpand(boolean expand) {
        this.expand = expand;
        return this ;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getFunctionName() {
        return functionName;
    }

    public MActivityBean setFunctionName(String functionName) {
        this.functionName = functionName;
        return this ;
    }

    public Class getActivityName() {
        return activityName;
    }

    public MActivityBean setActivityName(Class activityName) {
        this.activityName = activityName;
        return this ;
    }

    public List<MActivityBean> getSubBean() {
        return subBean;
    }

    public MActivityBean setSubBean(List<MActivityBean> subBean) {
        this.subBean = subBean;
        return this ;
    }
}
