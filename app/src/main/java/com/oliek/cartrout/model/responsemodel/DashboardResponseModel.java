package com.oliek.cartrout.model.responsemodel;

import com.oliek.cartrout.model.DashboardModel;
import com.oliek.cartrout.model.Itam;
import com.oliek.cartrout.model.OrderModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardResponseModel extends BaseResponse implements Serializable {
   private DashboardModel  dashboard;

    public DashboardModel getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardModel dashboard) {
        this.dashboard = dashboard;
    }
}
