package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewArrivalModel implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("image")
    private String image;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private String price;
    @SerializedName("regular_price")
    private String regularPrice;
    @SerializedName("des")
    private String des;
    @SerializedName("attachment_ids")
    private List<String> variationsImage;
    @SerializedName("product_attr")
    private product_attr productAttr;

    private int numberInCart;

    public NewArrivalModel(String image,String url, String title, String price, String des, String regularPrice, String id, List<String> variationsImage, product_attr productAttr) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.des = des;
        this.regularPrice = regularPrice;
        this.id = id;
        this.variationsImage = variationsImage;
        this.productAttr = productAttr;
        this.url = url;

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

    public void setImage(String image) {
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

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getVariationsImage() {
        return variationsImage;
    }

    public void setVariationsImage(List<String> variationsImage) {
        this.variationsImage = variationsImage;
    }

    public product_attr getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(product_attr productAttr) {
        this.productAttr = productAttr;
    }
}
