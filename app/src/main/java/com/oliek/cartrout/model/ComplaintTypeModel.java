package com.oliek.cartrout.model;

public class ComplaintTypeModel {
    private int id;
    private String complaint_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public ComplaintTypeModel(int id, String complaint_type) {
        this.id = id;
        this.complaint_type = complaint_type;
    }
}
