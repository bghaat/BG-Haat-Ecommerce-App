package com.bgsourcingltd.bghaat.models;

public class TopBrandsModel {

    private String name;
    private int imgUrl;

    public TopBrandsModel(String name, int imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }
}
