package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.util.ArrayList;

public class OrderHistoryResponseModel extends BaseResponse {


    private ArrayList<PendingOrderModel> order_list =new ArrayList<>();

    public ArrayList<PendingOrderModel> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(ArrayList<PendingOrderModel> order_list) {
        this.order_list = order_list;
    }
}