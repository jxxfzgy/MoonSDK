package com.moon.volley.api;

import com.android.volley.Response;
import com.google.gson.reflect.TypeToken;
import com.moon.volley.MApi;
import com.moon.volley.entity.MAvatar;
import com.moon.volley.network.MFormItem;
import com.moon.volley.network.MImageForm;
import com.moon.volley.network.MRequest;
import com.moon.volley.network.MRequestFormJson;
import com.moon.volley.network.MResponse;
import com.moon.volley.network.MStringForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class InfoApi extends MApi {
    public static void postAvatar(String filePath, MResponse<MAvatar> response){
        List<MFormItem> formItems = new ArrayList<>() ;
        formItems.add(new MStringForm("model","user"));
        formItems.add(new MStringForm("action","avatar"));
        formItems.add(new MStringForm("version","1.0"));
        formItems.add(new MStringForm("userid","1725150"));
        formItems.add(new MImageForm("filename",filePath,300,300));
        MRequest<MAvatar> request = new MRequestFormJson(formItems,new TypeToken<MAvatar>(){}.getType(),response) ;
        doRequest(request);
    }
}
