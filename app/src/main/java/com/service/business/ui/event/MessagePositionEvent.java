package com.service.business.ui.event;

public class MessagePositionEvent {

    private int position;

    public MessagePositionEvent( int position) {

        this.position = position;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}