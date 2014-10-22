package com.moon.sdk.refresh;

/**
 * Created by moon on 2014/10/22 0022.
 */
public enum MRefreshStatue {
    /*正常状态，松开不能刷新*/
    REFRESH_NORMAL,
    /*准备刷新，松开可以刷新*/
    REFRESH_PREPARE,
    /*正在刷新*/
    REFRESH_ING,
    /*刷新结束，滚回原处*/
    REFRESH_END,
}
