package com.to8to.clickstream.toolbox;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/25.
 */
public class FragmentName implements IFindRegister {

    protected static Map<String,String> fragmentEvent ;

    @Override
    public String findEventRegister(String key) {
        if (fragmentEvent == null) {
            fragmentEvent = new HashMap<>();
        }
        String value = fragmentEvent.get(key) ;
        if(TextUtils.isEmpty(value)){
            value = UN_STATISTICS;
        }
        return value ;
    }

    @Override
    public void addMap(String key, String value) {
        if(fragmentEvent == null){
            fragmentEvent = new HashMap<>() ;
        }
        fragmentEvent.clear();
        fragmentEvent.put(key,value) ;
    }

    @Override
    public void unRegister() {
        if (fragmentEvent != null)
            fragmentEvent.clear();
    }
}
