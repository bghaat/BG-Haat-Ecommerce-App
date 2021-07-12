package com.bgsourcingltd.bghaat.models;

import java.io.Serializable;

public class NewArrivalModel implements Serializable {

    private int imageUrl;
    private String foodTitle;
    private String price;
    private String strikePrice;
    private int numberInCart;

    public NewArrivalModel(int imageUrl, String foodTitle, String price, String strikePrice) {
        this.imageUrl = imageUrl;
        this.foodTitle = foodTitle;
        this.price = price;
        this.strikePrice = strikePrice;
    }

    public NewArrivalModel(int imageUrl, String foodTitle, String price, String strikePrice, int numberInCart) {
        this.imageUrl = imageUrl;
        this.foodTitle = foodTitle;
        this.price = price;
        this.strikePrice = strikePrice;
        this.numberInCart = numberInCart;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFoodTitle() {
        return foodTitle;
    }

    public void setFoodTitle(String foodTitle) {
        this.foodTitle = foodTitle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(String strikePrice) {
        this.strikePrice = strikePrice;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
