package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

public class RecordOrderModel {

    @SerializedName("post_status")
    private String post_status;

    public RecordOrderModel(String post_status) {
        this.post_status = post_status;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }
}
