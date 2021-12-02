package com.oliek.cartrout.model.responsemodel;

import com.oliek.cartrout.model.ComplaintModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplaintResponsModel extends BaseResponse implements Serializable {
    ArrayList<ComplaintModel> arrayList=new ArrayList<>();

    public ArrayList<ComplaintModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ComplaintModel> arrayList) {
        this.arrayList = arrayList;
    }
}
