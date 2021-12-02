package com.oliek.cartrout.model;

import java.io.Serializable;

public class UserModel implements Serializable {

    private int id;
    private String name;
    private String mobile;
    private String email;
    private String api_tocken;
    private String fb_tocken;
    private Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApi_tocken() {
        return api_tocken;
    }

    public void setApi_tocken(String api_tocken) {
        this.api_tocken = api_tocken;
    }

    public String getFb_tocken() {
        return fb_tocken;
    }

    public void setFb_tocken(String fb_tocken) {
        this.fb_tocken = fb_tocken;
    }
}
