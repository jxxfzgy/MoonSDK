package com.moon.volley.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moon.zhong on 2014/12/10.
 */
public class MList {
    /*清单Id*/
    @SerializedName("id")
    private String listId ;

    private boolean select ;

    /*现场ID*/
    @SerializedName("scene_id")
    private String liveId ;
    /*创建时间*/
    @SerializedName("ctime")
    private String cTime ;
    /*更新时间*/
    @SerializedName("utime")
    private String uTime ;
    /*清单名称*/
    private String name ;
    /*品牌*/
    private String brand ;
    /*规格*/
    private String spec ;
    /*购买地*/
    @SerializedName("buy_from_name")
    private String buyFrom ;
    @SerializedName("buy_from_1")
    private String buyFromId ;
    @SerializedName("buy_from_2")
    private String getBuyFromSubId ;
    /*描述*/
    private String descrption ;
    /*备注*/
    private String remark ;
    /*材料大分类*/
    @SerializedName("category_1")
    private String productId ;
    /*材料小分类*/
    @SerializedName("category_2")
    private String subProId ;
    /*材料名称*/
    @SerializedName("category_1_name")
    private String productName ;
    /*材料小分类*/
    @SerializedName("category_2_name")
    private String subProName ;
    /*总价钱*/
    @SerializedName("unit_price")
    private String price ;
    /*状态码*/
    private String status ;
    /*是否审核通过*/
    @SerializedName("filter_result")
    private String filterResult ;
    /*是否被日记占用*/
    @SerializedName("diary_used")
    private boolean diaryUsed ;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getuTime() {
        return uTime;
    }

    public void setuTime(String uTime) {
        this.uTime = uTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getBuyFrom() {
        return buyFrom;
    }

    public void setBuyFrom(String buyFrom) {
        this.buyFrom = buyFrom;
    }

    public String getBuyFromId() {
        return buyFromId;
    }

    public void setBuyFromId(String buyFromId) {
        this.buyFromId = buyFromId;
    }

    public String getGetBuyFromSubId() {
        return getBuyFromSubId;
    }

    public void setGetBuyFromSubId(String getBuyFromSubId) {
        this.getBuyFromSubId = getBuyFromSubId;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProId() {
        return subProId;
    }

    public void setSubProId(String subProId) {
        this.subProId = subProId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSubProName() {
        return subProName;
    }

    public void setSubProName(String subProName) {
        this.subProName = subProName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilterResult() {
        return filterResult;
    }

    public void setFilterResult(String filterResult) {
        this.filterResult = filterResult;
    }

    public boolean isDiaryUsed() {
        return diaryUsed;
    }

    public void setDiaryUsed(boolean diaryUsed) {
        this.diaryUsed = diaryUsed;
    }

    @Override
    public String toString() {
        return "mList{" +
                "listId='" + listId + '\'' +
                ", select=" + select +
                ", liveId='" + liveId + '\'' +
                ", cTime='" + cTime + '\'' +
                ", uTime='" + uTime + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", spec='" + spec + '\'' +
                ", buyFrom='" + buyFrom + '\'' +
                ", buyFromId='" + buyFromId + '\'' +
                ", getBuyFromSubId='" + getBuyFromSubId + '\'' +
                ", descrption='" + descrption + '\'' +
                ", remark='" + remark + '\'' +
                ", productId='" + productId + '\'' +
                ", subProId='" + subProId + '\'' +
                ", productName='" + productName + '\'' +
                ", subProName='" + subProName + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                ", filterResult='" + filterResult + '\'' +
                ", diaryUsed=" + diaryUsed +
                '}';
    }
}
