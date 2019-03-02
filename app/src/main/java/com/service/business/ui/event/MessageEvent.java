package com.service.business.ui.event;

import com.service.business.model.GoodsThreeBean;

import java.util.ArrayList;

public class MessageEvent {
    private ArrayList<GoodsThreeBean.ItemBean.ListBean> message;
    public MessageEvent(ArrayList<GoodsThreeBean.ItemBean.ListBean> message){
        this.message=message;
    }
 
    public ArrayList<GoodsThreeBean.ItemBean.ListBean> getMessage() {
        return message;
    }
 
    public void setMessage(ArrayList<GoodsThreeBean.ItemBean.ListBean> message) {
        this.message = message;
    }
}