package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DistributorBean implements Serializable {
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
            public String mobile;
            public String nickname;
            public String username;
            public String avatar;

        }
    }

    public String errmsg;
    public int errno;

}
