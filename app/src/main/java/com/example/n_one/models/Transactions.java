package com.example.n_one.models;

import android.content.Intent;

public class Transactions {

    private Integer img;
    private String title, desc;

    public Transactions(Integer img, String title, String desc) {
        this.img = img;
        this.title = title;
        this.desc = desc;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
