package com.oliek.cartrout.model;

import java.io.Serializable;

public class ProductModel implements Serializable {
    private  int id;
    private String name;
    private String description;
    private String a_name;
    private String a_description;
    private String image;
    private  double price;
    private  double offer_price;
    private  int  order;
    private  int user_id;
    private  int status;
    private  int web_product_category_id;

    public int getId() {
        return id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_description() {
        return a_description;
    }

    public void setA_description(String a_description) {
        this.a_description = a_description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(double offer_price) {
        this.offer_price = offer_price;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWeb_product_category_id() {
        return web_product_category_id;
    }

    public void setWeb_product_category_id(int web_product_category_id) {
        this.web_product_category_id = web_product_category_id;
    }
}
