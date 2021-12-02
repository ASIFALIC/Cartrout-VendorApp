package com.oliek.cartrout.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrderData {


    @SerializedName("order_number")
    @Expose
    private String order_number;




    @SerializedName("order_time")
    @Expose
    private String order_time;

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }
}
