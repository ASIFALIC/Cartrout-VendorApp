package com.oliek.cartrout.model;

import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel extends BaseResponse implements Serializable  {


    private int  id;
    private String name;
    private String mobil;
    private String address;
    private String locetion_link;
    private String total;
    private String subtotal;
    private String delivery_charge;
    private String packing_charge;
    private String time;
    private String diff;
    private String invoiceno;
    private int status;
    private int delivery_type;
    private String created_at;
    private String landmarks;

    public String getLandmarks() {
        return landmarks;
    }

    public void setLandmarks(String landmarks) {
        this.landmarks = landmarks;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }
    private ArrayList<CartModel> carts;

    public ArrayList<CartModel> getCarts() {
        return carts;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getPacking_charge() {
        return packing_charge;
    }

    public void setPacking_charge(String packing_charge) {
        this.packing_charge = packing_charge;
    }

    public void setCarts(ArrayList<CartModel> carts) {
        this.carts = carts;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocetion_link() {
        return locetion_link;
    }

    public void setLocetion_link(String locetion_link) {
        this.locetion_link = locetion_link;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(int delivery_type) {
        this.delivery_type = delivery_type;
    }
}
