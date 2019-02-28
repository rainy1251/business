package com.service.business.ui.activity;

import com.netease.nim.uikit.api.NimUIKit;
import com.service.business.R;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.MyToast;


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
        if (NimUIKit.getAccount()==null){
            MyToast.show("请登录");
        }

    }

    @Override
    protected void initListener() {

    }
}
