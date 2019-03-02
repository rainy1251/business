package com.service.business.ui.event;

public class MessageUpDataPriceEvent {
    private String message;
    public MessageUpDataPriceEvent(String message){
        this.message=message;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
}