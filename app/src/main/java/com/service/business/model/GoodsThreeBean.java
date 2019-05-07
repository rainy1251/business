package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class GoodsThreeBean implements Serializable {
    public DatasBean data;

    public class DatasBean implements Serializable {
        public ItemBean items;
    }

    public class ItemBean implements Serializable {
        public int  currPage;
        public int  totalCount;
        public int  totalPage;
        public int  pageSize;
        public ArrayList<ListBean> list;

        public class ListBean implements Serializable {
            public String id;
            public String name;
            public String picUrl;
            public String retailPrice;
            public boolean isCheck;
            public String  num;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }

    public String errmsg;
    public int errno;

}
