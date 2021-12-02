package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.DeliveryPartner;
import com.oliek.cartrout.model.PendingOrderModel;

import java.util.ArrayList;

public class StaffDashResponseModel {

    private String total_orders;
    private String total_today;
    private ArrayList<PendingOrderModel> order_list;
    private DeliveryPartner delivery_partner;

    public DeliveryPartner getDelivery_partner() {
        return delivery_partner;
    }

    public void setDelivery_partner(DeliveryPartner delivery_partner) {
        this.delivery_partner = delivery_partner;
    }

    public String getTotal_orders() {
        return total_orders;
    }

    public void setTotal_orders(String total_orders) {
        this.total_orders = total_orders;
    }

    public String getTotal_today() {
        return total_today;
    }

    public void setTotal_today(String total_today) {
        this.total_today = total_today;
    }

    public ArrayList<PendingOrderModel> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(ArrayList<PendingOrderModel> order_list) {
        this.order_list = order_list;
    }
}