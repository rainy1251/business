package com.service.business.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.GoodsThreeBean;
import com.service.business.ui.adapter.base.BaseHolder;
import com.service.business.ui.adapter.base.DefaultAdapter;
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

        @Override
        protected View initView() {
            View view = View.inflate(context, R.layout.layout_item_list, null);
            ButterKnife.bind(this, view);
            return view;
        }

        @Override
        protected void refreshView(final GoodsThreeBean.ItemBean.ListBean resultBean, final int position) {
            tvGoods.setText(resultBean.name);
            tvPrice.setText("￥"+resultBean.retailPrice);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num =tvNum.getText().toString();
//                    if (mOnUpDatePriceListener!=null){
//                        mOnUpDatePriceListener.onClick();
//                    }
                    tvNum.setText((Integer.valueOf(num)+1)+"");
                    resultBean.num=Integer.valueOf(num)+1;
                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price"));

                }
            });
            btnJian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String num = tvNum.getText().toString();
//                    if (mOnUpDatePriceListener!=null){
//                        mOnUpDatePriceListener.onClick();
//                    }
                    if (Integer.valueOf(num)>0){
                        tvNum.setText((Integer.valueOf(num)-1)+"");
                        resultBean.num=Integer.valueOf(num)-1;
                    }else{
                        getDatas().remove(position);
                        notifyDataSetChanged();
                    }
                    EventBus.getDefault().post(new MessageUpDataPriceEvent("price"));
                }
            });
        }
    }

    private OnUpDatePriceListener mOnUpDatePriceListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnUpPriceListener(OnUpDatePriceListener mOnUpDatePriceListener ) {
        this.mOnUpDatePriceListener=mOnUpDatePriceListener;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnUpDatePriceListener {
        public void onClick();
    }
}
