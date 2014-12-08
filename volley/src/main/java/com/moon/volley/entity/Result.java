package com.moon.volley.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by moon.zhong on 2014/12/8.
 */
public class Result {
    @SerializedName("tj")
    private String recommend ;
    @SerializedName("gqyxtj")
    private String highDefinition ;
    @SerializedName("mnqyx")
    private String emulator ;
    @SerializedName("dzjs")
    private String actionSpeed ;
    @SerializedName("qsyk")
    private String relax ;

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getHighDefinition() {
        return highDefinition;
    }

    public void setHighDefinition(String highDefinition) {
        this.highDefinition = highDefinition;
    }

    public String getEmulator() {
        return emulator;
    }

    public void setEmulator(String emulator) {
        this.emulator = emulator;
    }

    public String getActionSpeed() {
        return actionSpeed;
    }

    public void setActionSpeed(String actionSpeed) {
        this.actionSpeed = actionSpeed;
    }

    public String getRelax() {
        return relax;
    }

    public void setRelax(String relax) {
        this.relax = relax;
    }

    @Override
    public String toString() {
        return "Result{" +
                "recommend='" + recommend + '\'' +
                ", highDefinition='" + highDefinition + '\'' +
                ", emulator='" + emulator + '\'' +
                ", actionSpeed='" + actionSpeed + '\'' +
                ", relax='" + relax + '\'' +
                '}';
    }
}
