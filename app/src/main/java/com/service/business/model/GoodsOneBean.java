package com.service.business.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/11.
 */

public class GoodsOneBean implements Serializable {
    public ArrayList<DatasBean> data;

    public class DatasBean implements Serializable {
        public String label;
        public String value;
    }

    public String errmsg;
    public int errno;

}
