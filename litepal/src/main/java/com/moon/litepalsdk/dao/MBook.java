package com.moon.litepalsdk.dao;

import org.litepal.crud.DataSupport;

/**
 * Created by moon.zhong on 2015/1/8.
 */
public class MBook extends DataSupport {
    private float price ;

    private String name ;

    private String author ;

    private String publish ;

    private String pinYing ;

    public String getPinYing() {
        return pinYing;
    }

    public void setPinYing(String pinYing) {
        this.pinYing = pinYing;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    @Override
    public String toString() {
        return "MBook{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publish='" + publish + '\'' +
                '}';
    }
}
