package com.oliek.cartrout.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationsMain {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ArrayList<NotificationModel> data = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<NotificationModel> getData() {
        return data;
    }

    public void setData(ArrayList<NotificationModel> data) {
        this.data = data;
    }

}
