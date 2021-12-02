package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.CartModel;
import com.oliek.cartrout.model.Customer;
import com.oliek.cartrout.model.OrderImage;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.VendorModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderViewResponseModel  extends BaseResponse implements Serializable{


    private OrderModel order;

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }


}