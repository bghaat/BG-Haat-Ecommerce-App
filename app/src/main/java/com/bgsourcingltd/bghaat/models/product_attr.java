package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class product_attr implements Serializable {

    @SerializedName("pa_size")
    private List<String> size;


    public product_attr(List<String> size) {
        this.size = size;

    }

    public List<String> getSize() {
        return size;
    }

    public void setSize(List<String> size) {
        this.size = size;
    }

}
