package com.service.business.ui.view;

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