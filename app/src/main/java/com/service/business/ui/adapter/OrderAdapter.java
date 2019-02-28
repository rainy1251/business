package com.service.business.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.OrderListBean;
import com.service.business.ui.adapter.base.BaseHolder;
import com.service.business.ui.adapter.base.DefaultAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends DefaultAdapter {

    Context context;

    public OrderAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new ViewHolder();
    }


    public class ViewHolder extends BaseHolder<OrderListBean.DataBean> {


        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_orderSn)
        TextView tvOrderSn;
        @BindView(R.id.tv_state)
        TextView tvState;

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.item_order_list, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        protected void refreshView(final OrderListBean.DataBean resultBean, int position) {
            tvName.setText("商品名称："+resultBean.goodsList.get(0).goodsName);
            tvOrderSn.setText("订单编号："+resultBean.orderSn);
            tvState.setText("订单状态："+resultBean.orderStatusText);
            tvPrice.setText("订单金额："+resultBean.actualPrice+"元");
        }
    }


}