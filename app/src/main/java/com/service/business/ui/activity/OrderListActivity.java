package com.service.business.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.service.business.R;
import com.service.business.model.OrderListBean;
import com.service.business.model.StateBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.OrderAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.event.ConfirmEvent;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.service.business.ui.utils.UiUtils.getPostContent;

public class OrderListActivity extends BaseActivity {


    @BindView(R.id.lv_list)
    ListView lvList;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderListBean.DataBean> data;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initView() {
        setToolbarNoEN(R.id.toolbar, "订单管理");
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        orderAdapter = new OrderAdapter(this);
        lvList.setAdapter(orderAdapter);
        requeastList();
    }

    @Override
    protected void initListener() {
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                intent.putExtra("orderId", data.get(position).id);
                startActivity(intent);
            }
        });
    }

    private void requeastList() {
        NetUtils.getBuildByGet("/app/order/list").execute(new GenericsCallback<OrderListBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(OrderListBean response, int id) {
                data = response.data.data;
                orderAdapter.addData(data, true);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(ConfirmEvent confirmEvent) {
        String orderId = confirmEvent.getMessage();
        requeastConfirm(orderId);

    }

    private void requeastConfirm(String orderId) {
        Map<String, String> map = new HashMap();
        map.put("orderId", orderId);
        String postContent = getPostContent(map);
        NetUtils.getBuildByPostToken("/app/order/confirmReceivingGoods",postContent).execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(StateBean response, int id) {
                if (response.errno==0){
                    MyToast.show("已确认收货");
                    requeastList();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
