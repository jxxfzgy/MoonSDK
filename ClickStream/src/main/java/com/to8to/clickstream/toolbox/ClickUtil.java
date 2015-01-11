package com.to8to.clickstream.toolbox;

import com.to8to.clickstream.IClickStream;

/**
 * Created by moon.zhong on 2014/12/26.
 */
public class ClickUtil {
    private static IClickStream iClickStream ;

    public static IClickStream getClickStream() {
        return iClickStream;
    }

    public static void setClickStream(IClickStream iClickStream) {
        ClickUtil.iClickStream = iClickStream;
    }

}
