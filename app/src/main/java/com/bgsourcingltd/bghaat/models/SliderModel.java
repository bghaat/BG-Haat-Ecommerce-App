package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

public class SliderModel {
    @SerializedName("guid")
    private String image;

    public SliderModel(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
