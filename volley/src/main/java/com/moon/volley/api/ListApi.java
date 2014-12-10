package com.moon.volley.api;


import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.moon.volley.MApi;
import com.moon.volley.MConstant;
import com.moon.volley.entity.MList;
import com.moon.volley.network.MFormItem;
import com.moon.volley.network.MRequest;
import com.moon.volley.network.MRequestFormJson;
import com.moon.volley.network.MRequestJson;
import com.moon.volley.network.MResponse;
import com.moon.volley.network.MStringForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class ListApi extends MApi {
    /*写清单，通过参数的形式发送*/
    public static void addList(MList list ,MResponse response){
        Map<String, String> params = createParam() ;
        params.put("action","addproduct") ;
        params.put("scene_id", MConstant.SCENE_ID) ;
        params.put("name",list.getName()) ;
        params.put("brand",list.getBrand()) ;
        params.put("spec",list.getSpec()) ;
        params.put("buy_from_1","1") ;
        params.put("buy_from_2","500") ;
        params.put("buy_from_name",list.getBuyFrom()) ;
        params.put("remark",list.getRemark()) ;
        params.put("category_1","1") ;
        params.put("category_2","1") ;
        params.put("unit_price",list.getPrice()) ;
        Log.v("zgy", "===============params=====" + params) ;
        MRequest<MList> request = new MRequestJson<MList>(params,new TypeToken<MList>(){}.getType(),response) ;
        doRequest(request);
    }
    /*写清单，通过二进制数据的形式发送*/
    public static void addListPost(MList list ,MResponse response){
        List<MFormItem> formItemList = new ArrayList<>() ;
        formItemList.add(new MStringForm("version","2.0"));
        formItemList.add(new MStringForm("model","live"));
        formItemList.add(new MStringForm("action","addproduct"));
        formItemList.add(new MStringForm("scene_id", MConstant.SCENE_ID));
        formItemList.add(new MStringForm("name",list.getName()));
        formItemList.add(new MStringForm("brand",list.getBrand()));
        formItemList.add(new MStringForm("spec",list.getSpec()));
        formItemList.add(new MStringForm("buy_from_1","1"));
        formItemList.add(new MStringForm("buy_from_2","500"));
        formItemList.add(new MStringForm("buy_from_name",list.getBuyFrom()));
        formItemList.add(new MStringForm("remark",list.getRemark()));
        formItemList.add(new MStringForm("category_1","1"));
        formItemList.add(new MStringForm("category_2","1"));
        formItemList.add(new MStringForm("unit_price",list.getPrice()));
        MRequest<MList> request = new MRequestFormJson<MList>(formItemList,new TypeToken<MList>(){}.getType(),response) ;
        doRequest(request);

    }

    /*获取写清单详情*/
    public static void getListDetail(String master,String sceneId, MResponse response){
        Map<String ,String> params = createParam() ;
        params.put("action","ListProduct") ;
        params.put("asc","1") ;
        params.put("ids",MConstant.SCENE_ID) ;
        params.put("paging","0") ;
        params.put("page","0") ;
        params.put("perPage","1") ;
        params.put("scene_ids",sceneId) ;
        params.put("master",master) ;
        MRequest<MList> request = new MRequestJson<MList>(params, new TypeToken<MList>(){}.getType(),response) ;
        doRequest(request);
    }
}
