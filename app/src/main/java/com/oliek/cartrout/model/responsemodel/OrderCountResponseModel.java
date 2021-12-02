package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.PendingOrderModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.util.ArrayList;

public class OrderCountResponseModel extends BaseResponse {

private  int count ;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}