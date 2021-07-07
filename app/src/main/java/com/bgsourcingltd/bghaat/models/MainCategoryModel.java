package com.bgsourcingltd.bghaat.models;

import android.graphics.drawable.Drawable;

public class MainCategoryModel {
    private String catName;
    private int catImage;

    public MainCategoryModel(String catName, int catImage) {
        this.catName = catName;
        this.catImage = catImage;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatImage() {
        return catImage;
    }

    public void setCatImage(int catImage) {
        this.catImage = catImage;
    }
}
