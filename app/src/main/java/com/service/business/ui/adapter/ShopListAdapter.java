package com.service.business.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.GoodsThreeBean;
import com.service.business.ui.adapter.base.BaseHolder;
import com.service.business.ui.adapter.base.DefaultAdapter;
import com.service.business.ui.event.MessagePositionEvent;
import com.service.business.ui.event.MessageUpDataPriceEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopListAdapter extends DefaultAdapter {

    Context context;

    public ShopListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected BaseHolder getHolder(int position) {
        return new ViewHolder();
    }


    public class ViewHolder extends BaseHolder<GoodsThreeBean.ItemBean.ListBean> {


        @BindView(R.id.tv_goods)
        TextView tvGoods;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_jian)
        Button btnJian;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.btn_add)
        Button btnAdd;
        @BindView(R.id.tv_decimal)
        TextView tvDecimal;
        @BindView(R.id.btn_decimal_jian)
        Button btnDecimalJian;
        @BindView(R.id.btn_decimal_add)
        Button btnDecimalAdd;

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.layout_item_list, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        protected void refreshView(final GoodsThreeBean.ItemBean.ListBean resultBean, final int position) {
            tvGoods.setText(resultBean.name);
            tvPrice.setText("￥" + resultBean.retailPrice);
            if (resultBean.num.contains(".")){
                String[] split = resultBean.num.split("\\.");
                if (split.length>0){
                    tvNum.setText(split[0]);

                }
                if (split.length>1){
                    tvNum.setText(split[0]);
                    tvDecimal.setText(split[1]);
                }
            }else{
                tvNum.setText(resultBean.num);
                tvDecimal.setText(0+"");
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tvNum.getText().toString();
                    String decimal = tvDecimal.getText().toString();

                    tvNum.setText((Integer.parseInt(num) + 1) + "");
                    if (TextUtils.isEmpty(decimal)) {
                        resultBean.num = tvNum.getText().toString() + "";
                    } else {

                        resultBean.num = tvNum.getText().toString() + "." + decimal;
                    }
                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price", position));

                }
            });
            btnJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tvNum.getText().toString();
                    String decimal = tvDecimal.getText().toString().trim();

                    if (Integer.valueOf(num) > 0) {
                        tvNum.setText((Integer.valueOf(num) - 1) + "");
                        resultBean.num = tvNum.getText().toString() + "." + decimal;
                    } else {

                        resultBean.isCheck = false;
                        getDatas().remove(position);
                        EventBus.getDefault().post(new MessagePositionEvent(position));
                        notifyDataSetChanged();
                    }
                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price", position));
                }
            });
            btnDecimalJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tvNum.getText().toString();
                    String decimal = tvDecimal.getText().toString().trim();

                    if (Integer.valueOf(decimal) > 0) {
                        tvDecimal.setText((Integer.valueOf(decimal) - 1) + "");
                        resultBean.num = tvNum.getText().toString() + "." + tvDecimal.getText().toString();
                    } else {

                    }
                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price", position));
                }
            });
            btnDecimalAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tvNum.getText().toString();
                    String decimal = tvDecimal.getText().toString();
                    tvDecimal.setText((Integer.parseInt(decimal) + 1) + "");
                    if (TextUtils.isEmpty(decimal)) {
                        resultBean.num = tvNum.getText().toString() + "";
                    } else {

                        resultBean.num = tvNum.getText().toString() + "." + tvDecimal.getText().toString();
                    }

                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price", position));
                }
            });
        }
    }

    private OnUpDatePriceListener mOnUpDatePriceListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnUpPriceListener(OnUpDatePriceListener mOnUpDatePriceListener) {
        this.mOnUpDatePriceListener = mOnUpDatePriceListener;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnUpDatePriceListener {
        public void onClick();
    }
}
