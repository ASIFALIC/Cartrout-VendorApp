package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.util.ArrayList;

public class OrderListResponseModel extends BaseResponse {


    private ArrayList<OrderModel> orders =new ArrayList<>();

    public ArrayList<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderModel> orders) {
        this.orders = orders;
    }
}