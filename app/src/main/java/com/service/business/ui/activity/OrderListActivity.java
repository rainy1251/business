package com.service.business.ui.activity;

import android.widget.ListView;
import android.widget.RadioGroup;

import com.service.business.R;
import com.service.business.model.OrderListBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.OrderAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.NetUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderListActivity extends BaseActivity {


    @BindView(R.id.lv_list)
    ListView lvList;
    private OrderAdapter orderAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        setToolbarNoEN(R.id.toolbar, "订单管理");
    }

    @Override
    public void initData() {
        orderAdapter = new OrderAdapter(this);
        lvList.setAdapter(orderAdapter);
        requeastList();
    }

    @Override
    protected void initListener() {

    }
    private void requeastList() {
        NetUtils.getBuildByGet("/app/order/list").execute(new GenericsCallback<OrderListBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(OrderListBean response, int id) {
                ArrayList<OrderListBean.DataBean> data = response.data.data;
                orderAdapter.addData(data,true);
            }
        });
    }


}
