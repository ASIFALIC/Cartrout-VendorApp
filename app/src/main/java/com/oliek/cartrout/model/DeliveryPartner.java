package com.oliek.cartrout.model;

public class DeliveryPartner {
    private String name;
    private String address;
    private String landmark;
    private double latitude;
    private double longitude;
    private double current_balance;
    private double earnings;
    private int credit_limit;
    private String phone;
    private boolean is_delivery_partner_can_order;
    private String mobile_number;
    private String vehicle_number;
    private boolean can_start_service;
    private boolean active;
    private boolean is_started ;

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public boolean is_started() {
        return is_started;
    }

    public double getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(double current_balance) {
        this.current_balance = current_balance;
    }

    public boolean isIs_started() {
        return is_started;
    }

    public void setIs_started(boolean is_started) {
        this.is_started = is_started;
    }

    public void set_started(boolean is_started) {
        this.is_started = is_started;
    }

    public String getName() {
        return name;
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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(int credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIs_delivery_partner_can_order() {
        return is_delivery_partner_can_order;
    }

    public void setIs_delivery_partner_can_order(boolean is_delivery_partner_can_order) {
        this.is_delivery_partner_can_order = is_delivery_partner_can_order;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public boolean isCan_start_service() {
        return can_start_service;
    }

    public void setCan_start_service(boolean can_start_service) {
        this.can_start_service = can_start_service;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
