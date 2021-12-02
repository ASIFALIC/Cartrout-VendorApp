package com.oliek.cartrout.model.responsemodel;


import com.oliek.cartrout.model.CreditModel;

import java.util.ArrayList;

public class CreditResponseModel {

    private String current_balance;
    private ArrayList<CreditModel> staff_credits;

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public ArrayList<CreditModel> getStaff_credits() {
        return staff_credits;
    }

    public void setStaff_credits(ArrayList<CreditModel> staff_credits) {
        this.staff_credits = staff_credits;
    }
}