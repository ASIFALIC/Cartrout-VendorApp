package com.oliek.cartrout.model;

public class Country {

    private int id;
    private String name;
    private int status;
    private String currency_name;
    private String currency_shot_name;
    private String currency_shot_symbols;
    private String country_mobile_code;
    private int country_mobile_number_length;
    private String country_lan_phone_code;
    private int country_lan_phone_number_length;
    private int order;
    private String timezone;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_shot_name() {
        return currency_shot_name;
    }

    public void setCurrency_shot_name(String currency_shot_name) {
        this.currency_shot_name = currency_shot_name;
    }

    public String getCurrency_shot_symbols() {
        return currency_shot_symbols;
    }

    public void setCurrency_shot_symbols(String currency_shot_symbols) {
        this.currency_shot_symbols = currency_shot_symbols;
    }

    public String getCountry_mobile_code() {
        return country_mobile_code;
    }

    public void setCountry_mobile_code(String country_mobile_code) {
        this.country_mobile_code = country_mobile_code;
    }

    public int getCountry_mobile_number_length() {
        return country_mobile_number_length;
    }

    public void setCountry_mobile_number_length(int country_mobile_number_length) {
        this.country_mobile_number_length = country_mobile_number_length;
    }

    public String getCountry_lan_phone_code() {
        return country_lan_phone_code;
    }

    public void setCountry_lan_phone_code(String country_lan_phone_code) {
        this.country_lan_phone_code = country_lan_phone_code;
    }

    public int getCountry_lan_phone_number_length() {
        return country_lan_phone_number_length;
    }

    public void setCountry_lan_phone_number_length(int country_lan_phone_number_length) {
        this.country_lan_phone_number_length = country_lan_phone_number_length;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }



    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
