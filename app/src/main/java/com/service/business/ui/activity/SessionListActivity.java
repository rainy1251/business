package com.service.business.ui.activity;

import com.service.business.R;
import com.service.business.ui.base.BaseActivity;


public class SessionListActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_session_list;
    }

    @Override
    public void initView() {

        setToolbarNoEN(R.id.toolbar, "消息列表");
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initListener() {

    }
}
