package com.moon.litepalsdk.dao;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by moon.zhong on 2015/1/9.
 */
public class MPublish extends DataSupport {

    private int id ;

    private String name ;

    private List<MBook> books ;

    private float area ;

    private String address ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MBook> getBooks() {
        return MPublish.where("MPublish_id=?",id+"").find(MBook.class);
    }

    public void setBooks(List<MBook> books) {
        this.books = books;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MPublish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", books=" + getBooks() +
                ", area=" + area +
                ", address='" + address + '\'' +
                '}';
    }
}
