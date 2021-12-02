package com.oliek.cartrout.model.requestmodel;


import com.oliek.cartrout.model.base.Baserequest;

public class VendorrequestModel extends Baserequest {
    private String entity_id;

    public String getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(String entity_id) {
        this.entity_id = entity_id;
    }
}
