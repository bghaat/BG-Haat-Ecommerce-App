package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewArrivalModel implements Serializable {

    @SerializedName("image")
    private String image;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private String price;
    @SerializedName("des")
    private String des;
    private int numberInCart;

    public NewArrivalModel(String image, String title, String price, String des) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.des = des;

    }

    public NewArrivalModel(String image, String title, String price, int numberInCart,String des) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.numberInCart = numberInCart;
        this.des = des;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String imageUrl) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
