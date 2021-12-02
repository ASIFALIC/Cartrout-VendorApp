package com.oliek.cartrout.model;

import java.util.ArrayList;

public class DashboardModel {
    private ArrayList<Itam> itam;
    private ArrayList<OrderModel> orders;
    private  int todayorders;
    private  int weekorders;
    private  int monthorders;

    public ArrayList<Itam> getItam() {
        return itam;
    }

    public void setItam(ArrayList<Itam> itam) {
        this.itam = itam;
    }

    public ArrayList<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderModel> orders) {
        this.orders = orders;
    }

    public int getTodayorders() {
        return todayorders;
    }

    public void setTodayorders(int todayorders) {
        this.todayorders = todayorders;
    }

    public int getWeekorders() {
        return weekorders;
    }

    public void setWeekorders(int weekorders) {
        this.weekorders = weekorders;
    }

    public int getMonthorders() {
        return monthorders;
    }

    public void setMonthorders(int monthorders) {
        this.monthorders = monthorders;
    }
}
