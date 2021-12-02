package com.oliek.cartrout.model;

public class VendorModel {
   private String name;
    private String address;
    private String mobile_number;
    private String phone_number;
    private String location_link;

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getLocation_link() {
        return location_link;
    }

    public void setLocation_link(String location_link) {
        this.location_link = location_link;
    }
}
