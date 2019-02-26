package com.service.business.ui.base;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.netease.nim.uikit.SPUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.service.business.avchat.DemoCache;
import com.service.business.avchat.IMConfig;
import com.service.business.ui.view.MyLoadingView;


public class BaseApplication extends Application {

    private static BaseApplication instance;


//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//
//
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRefreshLayout();
        SPUtils.instance(this);
        MultiDex.install(this);
        getInitIM();

    }

    private void getInitIM() {
        DemoCache.setContext(getApplicationContext());
        com.netease.nim.uikit.SPUtils.instance(getApplicationContext());
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, IMConfig.loginInfo(), IMConfig.options(getApplicationContext()));
        // crash handler
        IMConfig.initUiKit(getApplicationContext());
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initRefreshLayout() {

        TwinklingRefreshLayout.setDefaultHeader(ProgressLayout.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(MyLoadingView.class.getName());
    }


    public static BaseApplication getInstance() {
        return instance;
    }


}
