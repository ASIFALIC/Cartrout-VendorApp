package com.oliek.cartrout.model.responsemodel;

import com.oliek.cartrout.model.ComplaintTypeModel;
import com.oliek.cartrout.model.base.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplaintTypeResponsModel extends BaseResponse implements Serializable {
    ArrayList<ComplaintTypeModel> arrayList=new ArrayList<>();

    public ArrayList<ComplaintTypeModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ComplaintTypeModel> arrayList) {
        this.arrayList = arrayList;
    }
}
