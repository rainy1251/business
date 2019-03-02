package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderBean {

    public DatasBean data;

    public class DatasBean implements Serializable {
        public OrderInfoBean orderInfo;
        public ArrayList<OrderGoodsBean> orderGoods;

        public class OrderInfoBean {
            public String actualPrice;
            public String addTime;
            public String consignee;
            public String expCode;
            public String expNo;
            public String freightPrice;
            public String goodsPrice;
            public String address;
            public String id;
            public String mobile;
            public String orderSn;
            public String orderStatusText;
            public String userId;
            public OptionBean handleOption;

            public class OptionBean implements Serializable {
                public boolean cancel;
                public boolean comment;
                public boolean confirm;
                public boolean delete;
                public boolean pay;
                public boolean rebuy;
                public boolean refund;

            }
        }

        public class OrderGoodsBean {
            public String goodsId;
            public String goodsName;
            public String id;
            public String number;
            public String orderId;
            public String picUrl;
            public String retailPrice;
        }
    }

    public String errmsg;
    public int errno;

}
