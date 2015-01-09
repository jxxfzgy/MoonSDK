package com.moon.litepalsdk.dao;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by moon.zhong on 2015/1/9.
 */
public class MShop extends DataSupport {

    private String address;

    private String name;

    private List<MBook> books;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MBook> getBooks() {
        return books;
    }

    public void setBooks(List<MBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "MShop{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
