package com.bgsourcingltd.bghaat.models;

public class OfferModel {

    private String date;
    private String imageUrl;
    private String title;

    public OfferModel() {
    }

    public OfferModel(String date, String imageUrl, String title) {
        this.date = date;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
