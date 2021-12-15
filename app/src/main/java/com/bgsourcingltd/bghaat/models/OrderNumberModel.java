package com.bgsourcingltd.bghaat.models;

import com.google.gson.annotations.SerializedName;

public class OrderNumberModel {

    @SerializedName("Order_Id")
    private String Order_Id;

    public OrderNumberModel(String order_Id) {
        Order_Id = order_Id;
    }

    public String getOrder_Id() {
        return Order_Id;
    }

    public void setOrder_Id(String order_Id) {
        Order_Id = order_Id;
    }
}
