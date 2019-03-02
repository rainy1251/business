package com.service.business.ui.event;

public class MessageUpDataUIEvent {
    private String message;
    public MessageUpDataUIEvent(String message){
        this.message=message;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}