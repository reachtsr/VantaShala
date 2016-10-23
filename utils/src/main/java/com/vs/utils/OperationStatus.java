package com.vs.utils;

import com.vs.model.enums.OperationStatusEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by GeetaKrishna on 11/29/2015.
 */

public class OperationStatus {

    private String message;
    private OperationStatusEnum status;
    public static OperationStatus setStatus(OperationStatusEnum status, String message){
        OperationStatus s = new OperationStatus();
        s.setMessage(message);
        s.status = status;
        return s;
    }
    public static OperationStatus setStatus(OperationStatusEnum status){
        OperationStatus s = new OperationStatus();
        s.setMessage("");
        s.status = status;
        return s;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
