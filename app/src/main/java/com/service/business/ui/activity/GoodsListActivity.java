package com.service.business.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.service.business.R;
import com.service.business.model.GoodsThreeBean;
import com.service.business.model.OrderDetailBean;
import com.service.business.model.StateBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.ShopListAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.MyLog;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;
import com.service.business.ui.utils.UiUtils;
import com.service.business.ui.view.MessageEvent;
import com.service.business.ui.view.MessageUpDataPriceEvent;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.tv_goods)
    TextView tvGoods;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_select)
    TextView tv_select;
    @BindView(R.id.tv_orderUserId)
    TextView tv_orderUserId;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    private ArrayList<GoodsThreeBean.ItemBean.ListBean> selectGoodsList = new ArrayList<>();
    private ShopListAdapter allAdapter;
    private TextView tvallPrice;
    private PopupWindow mPopWindow;
    private String orderUserId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goodslist;
    }

    @Override
    public void initView() {

        setToolbarNoEN(R.id.toolbar, "购物清单");
        EventBus.getDefault().register(this);

    }


    @Override
    public void initData() {
        orderUserId = getIntent().getStringExtra("orderUserId");
        if (orderUserId != null) {

            tv_orderUserId.setText("客户ID：" + orderUserId);
        }
    }


    @Override
    protected void initListener() {

    }


    @OnClick({R.id.tv_goods, R.id.tv_order, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods:
                showPop(selectGoodsList);
                break;
            case R.id.tv_select:
                Intent intent = new Intent(this, SelectGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_order:
                if (allAdapter == null) {
                    MyToast.show("请先选择商品");
                    return;
                }
                ArrayList<GoodsThreeBean.ItemBean.ListBean> datas = (ArrayList<GoodsThreeBean.ItemBean.ListBean>) allAdapter.getDatas();
                if (datas.size() == 0) {
                    MyToast.show("请先选择商品");
                    return;
                }
                OrderDetailBean detailBean = new OrderDetailBean();
                String price = tvallPrice.getText().toString();
                if (price.contains("元")) {
                    String[] prices = price.split("元");
                    detailBean.actualPrice = prices[0];
                }
                if (orderUserId != null) {
                    detailBean.userId = orderUserId;
                }
                ArrayList<OrderDetailBean.GoodsBean> orderGoods = new ArrayList<>();
                detailBean.orderGoods = orderGoods;
                for (int i = 0; i < datas.size(); i++) {
                    OrderDetailBean.GoodsBean goodsBean = new OrderDetailBean.GoodsBean(datas.get(i).name, datas.get(i).num, datas.get(i).retailPrice);
                    detailBean.orderGoods.add(goodsBean);
                }
                String s = new Gson().toJson(detailBean);
                createOrder(s);
                break;
        }
    }

    /**
     * 弹出pop
     *
     * @param titleList
     */
    private void showPop(final ArrayList<GoodsThreeBean.ItemBean.ListBean> titleList) {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.layout_pop, null);
        int screenHeight = UiUtils.getScreenHeight();
        mPopWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, screenHeight);
        ListView lv_shop = (ListView) contentView.findViewById(R.id.lv_shop);
        TextView tv_close = (TextView) contentView.findViewById(R.id.tv_close);
        tvallPrice = contentView.findViewById(R.id.tv_allprice);
        allAdapter = new ShopListAdapter(this);
        lv_shop.setAdapter(allAdapter);
        allAdapter.addData(titleList, true);
        mPopWindow.showAtLocation(ll_bottom, Gravity.NO_GRAVITY, 0, -UIUtil.dip2px(GoodsListActivity.this, 80));

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
            }
        });
        int allPrice = 0;
        for (int i = 0; i < titleList.size(); i++) {
            titleList.get(i).num = 1;
            allPrice = (int) (allPrice + Float.valueOf(titleList.get(i).retailPrice) * 1);
        }
        tvallPrice.setText(allPrice + "元");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        selectGoodsList = messageEvent.getMessage();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpDataPriceEvent messageEvent) {
        String message = messageEvent.getMessage();
        if (message.equals("price")) {
            int allPrice = 0;
            ArrayList<GoodsThreeBean.ItemBean.ListBean> datas = (ArrayList<GoodsThreeBean.ItemBean.ListBean>) allAdapter.getDatas();
            for (int i = 0; i < datas.size(); i++) {
                allPrice = (int) (allPrice + Float.valueOf(datas.get(i).retailPrice) * datas.get(i).num);
            }
            tvallPrice.setText(allPrice + "元");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 创建订单
     * @param content
     */
    public void createOrder(String content) {
        NetUtils.getBuildByPostToken("/app/order/createOrder", content).execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(StateBean response, int id) {
                if (response.errno == 0) {
                    MyToast.show("下单成功");
                    if (mPopWindow != null) {
                        mPopWindow.dismiss();
                    }
                }
            }
        });
    }
}
