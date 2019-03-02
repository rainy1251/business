package com.service.business.ui.event;

public class ConfirmEvent {
    private String message;
    public ConfirmEvent(String message){
        this.message=message;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}