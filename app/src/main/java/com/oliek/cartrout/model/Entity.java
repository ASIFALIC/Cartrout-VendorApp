package com.oliek.cartrout.model;

import java.io.Serializable;

public class Entity implements Serializable {
    private int id;
    private String name;
    private String  subdomain;
    private String       logo;
    private String  address;
    private String         image;
    private String mobile;
    private int is_it_open;
    private int is_it_busy;
    private String expiry_date;
    private String delivery_charge = "0";
    private String packing_charge = "0";
private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getPacking_charge() {
        return packing_charge;
    }

    public void setPacking_charge(String packing_charge) {
        this.packing_charge = packing_charge;
    }

    public int getIs_it_open() {
        return is_it_open;
    }

    public void setIs_it_open(int is_it_open) {
        this.is_it_open = is_it_open;
    }

    public int getIs_it_busy() {
        return is_it_busy;
    }

    public void setIs_it_busy(int is_it_busy) {
        this.is_it_busy = is_it_busy;
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

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
