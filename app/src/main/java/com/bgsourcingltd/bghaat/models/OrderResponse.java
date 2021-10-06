package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("error")
    private String error;
    @SerializedName("message")
    private String message;

    public OrderResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
