package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.EarningsModel;

import java.util.ArrayList;

public class EarningsResponseModel {

    private String earnings;
    private ArrayList<EarningsModel> staff_earnings;

    public String getEarnings() {
        return earnings;
    }

    public void setEarnings(String earnings) {
        this.earnings = earnings;
    }

    public ArrayList<EarningsModel> getStaff_earnings() {
        return staff_earnings;
    }

    public void setStaff_earnings(ArrayList<EarningsModel> staff_earnings) {
        this.staff_earnings = staff_earnings;
    }
}