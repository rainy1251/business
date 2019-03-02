package com.service.business.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.service.business.R;
import com.service.business.model.OrderBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.OrderDetailAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.NetUtils;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity {

//
//    @BindView(R.id.tv_orderUserId)
//    TextView tvOrderUserId;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerview;
    private OrderDetailAdapter orderDetailAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initView() {
        setToolbarNoEN(R.id.toolbar,"订单详情");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(linearLayoutManager);
        orderDetailAdapter = new OrderDetailAdapter(this);
        mRecyclerview.setAdapter(orderDetailAdapter);
    }

    @Override
    public void initData() {
        String orderId = getIntent().getStringExtra("orderId");
        getOrderDetail(orderId);
    }

    /**
     * h获取订单详情
     * @param orderId
     */
    private void getOrderDetail(String orderId) {
        NetUtils.getBuildByGet("/app/order/detail?orderId=" + orderId)
                .execute(new GenericsCallback<OrderBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onResponse(OrderBean response, int id) {
                        orderDetailAdapter.setOrderInfo(response.data.orderInfo);
                        orderDetailAdapter.notifyDataSetChanged();
                        orderDetailAdapter.addData(response.data.orderGoods,true);
                    }
                });
    }

    @Override
    protected void initListener() {

    }


}
