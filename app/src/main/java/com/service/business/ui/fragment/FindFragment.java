package com.service.business.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.service.business.R;
import com.service.business.ui.activity.GoodsListActivity;
import com.service.business.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FindFragment extends BaseFragment {

    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
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



    @OnClick(R.id.rl_bar)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), GoodsListActivity.class);
        startActivity(intent);
    }
}
