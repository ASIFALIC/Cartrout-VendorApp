package com.oliek.cartrout.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PendingOrderModel implements Serializable {
    private int id;
    private String type;
    private String date;
    private String customer_name;
    private String address;
    private String order_status;
    private String scheduled_date;
    private int  DeliveryType ;
    private  int noofbags ;
    private  int kgslot;
    private ArrayList<String> images;
    private boolean isdeliverychargefromcustomer;
    private  double deliverycharge;
    private double valueofpackage;
    private int packagetype;
    private double amountcollectingfromcustomer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getScheduled_date() {
        return scheduled_date;
    }

    public void setScheduled_date(String scheduled_date) {
        this.scheduled_date = scheduled_date;
    }

    public int getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        DeliveryType = deliveryType;
    }

    public int getNoofbags() {
        return noofbags;
    }

    public void setNoofbags(int noofbags) {
        this.noofbags = noofbags;
    }

    public int getKgslot() {
        return kgslot;
    }

    public void setKgslot(int kgslot) {
        this.kgslot = kgslot;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public boolean isIsdeliverychargefromcustomer() {
        return isdeliverychargefromcustomer;
    }

    public void setIsdeliverychargefromcustomer(boolean isdeliverychargefromcustomer) {
        this.isdeliverychargefromcustomer = isdeliverychargefromcustomer;
    }

    public double getDeliverycharge() {
        return deliverycharge;
    }

    public void setDeliverycharge(double deliverycharge) {
        this.deliverycharge = deliverycharge;
    }

    public double getValueofpackage() {
        return valueofpackage;
    }

    public void setValueofpackage(double valueofpackage) {
        this.valueofpackage = valueofpackage;
    }

    public int getPackagetype() {
        return packagetype;
    }

    public void setPackagetype(int packagetype) {
        this.packagetype = packagetype;
    }

    public double getAmountcollectingfromcustomer() {
        return amountcollectingfromcustomer;
    }

    public void setAmountcollectingfromcustomer(double amountcollectingfromcustomer) {
        this.amountcollectingfromcustomer = amountcollectingfromcustomer;
    }
}
