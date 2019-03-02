package com.service.business.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.OrderBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailAdapter extends RecyclerView.Adapter {



    private Context context;
    private OrderBean.DatasBean.OrderInfoBean orderInfo;
    public static final int TYPE_USERINFO = 0;
    public static final int TYPE_ORDERLIST = 1;
    public static final int TYPE_PRICE = 2;
    public static final int TYPE_SERVICE = 3;
    public static final int TYPE_SERVICE_MORE = 4;
    private ArrayList<OrderBean.DatasBean.OrderGoodsBean> datas = new ArrayList<>();

    public OrderDetailAdapter(Context context) {

        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;

        if (TYPE_USERINFO == viewType) {
            View view = View.inflate(context, R.layout.item_userinfo_layout, null);
            holder = new UserInfoHolder(view);

        } else if (TYPE_ORDERLIST == viewType) {
            View view = mInflater.inflate(R.layout.item_orderdetail_layout, parent, false);
            holder = new OrderListHolder(view);

        } else if (TYPE_PRICE == viewType) {
            View view = mInflater.inflate(R.layout.item_orderprice_layout, parent, false);
            holder = new PriceHolder(view);

        }
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UserInfoHolder) {
            if (orderInfo != null) {
                ((UserInfoHolder) holder).tvId.setText("客户ID：" + orderInfo.userId + "/" + orderInfo.consignee);
                ((UserInfoHolder) holder).tvNickname.setText(orderInfo.consignee);
                ((UserInfoHolder) holder).tvMobile.setText(orderInfo.mobile);
                ((UserInfoHolder) holder).tvAddress.setText(orderInfo.address);
            }
        } else if (holder instanceof OrderListHolder) {
            ((OrderListHolder) holder).tvGoodsname.setText(datas.get(position - 1).goodsName);
            ((OrderListHolder) holder).tvGoodsprice.setText("￥" + datas.get(position - 1).retailPrice);
            ((OrderListHolder) holder).tvGoodsnum.setText("x" + datas.get(position - 1).number);

        } else if (holder instanceof PriceHolder) {
           // ((PriceHolder) holder).tv_liuyan.setText(datas.);
            if (orderInfo != null) {
            ((PriceHolder) holder).tvAllprice.setText(orderInfo.actualPrice+"元");
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = (datas == null ? 0 : datas.size() + 2);
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_USERINFO;
        } else if (position == getItemCount() - 1) {
            return TYPE_PRICE;
        } else {
            return TYPE_ORDERLIST;
        }
    }

    public void setOrderInfo(OrderBean.DatasBean.OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }


    class UserInfoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_mobile)
        TextView tvMobile;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_id)
        TextView tvId;

        public UserInfoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    class OrderListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_goodsname)
        TextView tvGoodsname;
        @BindView(R.id.tv_goodsprice)
        TextView tvGoodsprice;
        @BindView(R.id.tv_goodsnum)
        TextView tvGoodsnum;

        public OrderListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    class PriceHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_liuyan)
        TextView tv_liuyan;
        @BindView(R.id.tv_allprice)
        TextView tvAllprice;
        public PriceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void addData(List data, boolean refresh) {
        if (refresh) {
            datas.clear();
        }
        datas.addAll(data);
        notifyDataSetChanged();
    }


}
