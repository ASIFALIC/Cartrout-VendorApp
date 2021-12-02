package com.oliek.cartrout.model.responsemodel;


import java.io.Serializable;

public class StatusResponseModel  implements Serializable {

    private int order;
    private int status;
    private boolean success;
    private String message;
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}