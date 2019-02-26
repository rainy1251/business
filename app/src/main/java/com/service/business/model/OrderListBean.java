package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class OrderListBean implements Serializable {
    public DatasBean data;

    public class DatasBean implements Serializable {
        public String  count;
        public ArrayList<DataBean> data;
    }

    public class DataBean implements Serializable {
        public String  actualPrice;
        public String  id;
        public String  orderSn;
        public String  orderStatusText;
        public ArrayList<GoodsBean> goodsList;
        public OptionBean handleOption;

        public class GoodsBean implements Serializable {
            public String id;
            public String goodsName;
            public String number;
            public String picUrl;
        }
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

    public String errmsg;
    public int errno;

}
