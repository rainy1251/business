package com.service.business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.ui.activity.GoodsListActivity;
import com.service.business.ui.activity.OrderListActivity;
import com.service.business.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FindFragment extends BaseFragment {


    @BindView(R.id.tv_create_order)
    TextView tvCreateOrder;
    @BindView(R.id.tv_order_list)
    TextView tvOrderList;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }



    @OnClick({R.id.tv_create_order, R.id.tv_order_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_create_order:
                Intent intent =new Intent(getActivity(),GoodsListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_order_list:

                Intent intent_order =new Intent(getActivity(),OrderListActivity.class);
                startActivity(intent_order);
                break;
        }
    }
}
