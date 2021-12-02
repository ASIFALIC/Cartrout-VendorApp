package com.oliek.cartrout.model.base;

import java.io.Serializable;

public class Baserequest implements Serializable {

    private String userId;
    private String token;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
