package com.service.business.ui.event;

public class MessageUpDataPriceEvent {
    private String message;
    private int position;

    public MessageUpDataPriceEvent(String message, int position) {
        this.message = message;
        this.position = position;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPosition() {
        return position;
    }

    public void setPessage(int position) {
        this.position = position;
    }
}