package com.service.business.ui.event;

public class MessageDistributorEvent {
    private String id;
    private String name;
    public MessageDistributorEvent(String message,String name){
        this.id=message;
        this.name=name;
    }
 
    public String getMessage() {
        return id;
    }
 
    public void setMessage(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}