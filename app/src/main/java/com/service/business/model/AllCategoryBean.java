package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

public class AllCategoryBean {

    public DatasBean data;

    public class DatasBean implements Serializable {

        public ArrayList<TreeBean> tree;

        public class TreeBean implements Serializable {
            public boolean checked;
            public boolean disabled;
            public boolean halfCheck;
            public boolean nocheck;
            public String id;
            public String imgUrl;
            public String label;
            public String pid;
            public String url;
            public ArrayList<ChildrenBean> children;

            public class ChildrenBean implements Serializable {
                public boolean checked;
                public boolean disabled;
                public boolean halfCheck;
                public boolean nocheck;
                public String id;
                public String imgUrl;
                public String label;
                public String pid;
                public String url;

            }
        }
    }

    public String errmsg;
    public int errno;
}
