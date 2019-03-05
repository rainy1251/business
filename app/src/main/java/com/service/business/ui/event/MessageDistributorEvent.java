package com.service.business.ui.event;

public class MessageDistributorEvent {
    private String id;
    public MessageDistributorEvent(String message){
        this.id=message;
    }
 
    public String getMessage() {
        return id;
    }
 
    public void setMessage(String id) {
        this.id = id;
    }
}