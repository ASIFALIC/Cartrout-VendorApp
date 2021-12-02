package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.DeliveryPartner;
import com.oliek.cartrout.model.UserModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;

public class LoginResponseModel extends BaseResponse implements Serializable {


   private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}