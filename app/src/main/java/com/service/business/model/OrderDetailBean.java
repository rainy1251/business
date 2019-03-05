package com.service.business.model;

import java.util.ArrayList;

public class OrderDetailBean {
    public String actualPrice;
    public String addTime;
    public String address;
    public String confirmTime;
    public String consignee;
    public int  couponPrice;
    public boolean deleted;
    public String endTime;
    public int  freightPrice;
    public int  goodsPrice;
    public int  id;
    public int  integralPrice;
    public String  mobile;
    public String  orderSn;
    public String  payId;
    public String  payTime;
    public String  shipChannel;
    public String  shipSn;
    public String  shipTime;
    public int  orderPrice;
    public int  orderStatus;
    public String  userId;
    public int  version;
    public ArrayList<GoodsBean>  orderGoods;
    public static class GoodsBean {
        public String  addTime;
        public boolean  deleted;
        public String  goodsId;
        public String  goodsName;
        public String  goodsSn;
        public String  picUrl;
        public int  id;
        public int  number;
        public int  orderId;
        public String  price;
        public int  productId;
        public int  version;

        public GoodsBean(String goodsName, int number, String price,String goodsId,String picUrl) {
            this.goodsName = goodsName;
            this.number = number;
            this.price = price;
            this.goodsId = goodsId;
            this.picUrl = picUrl;
        }
    }

}
