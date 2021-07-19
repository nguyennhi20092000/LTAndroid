package com.example.appbanhangnew.model;

public class QuanHuyen {
    String name;
    String type;
    int code;

    public QuanHuyen(String name, String type,int code) {
        this.name = name;
        this.type = type;
        this.code=code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }
}
