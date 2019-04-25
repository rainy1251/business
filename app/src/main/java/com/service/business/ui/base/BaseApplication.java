package com.service.business.ui.base;

import android.app.Application;
import android.content.IntentFilter;
import android.support.multidex.MultiDex;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;
import com.hyphenate.util.EMLog;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.service.business.hxim.CallReceiver;
import com.service.business.hxim.DemoHelper;
import com.service.business.ui.utils.SPUtils;
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
        getInitHXIM();

    }

    private void getInitHXIM() {
//        EMOptions options = new EMOptions();
//// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
//        EaseUI.getInstance().init(this, options);
//        //EMClient.getInstance().setDebugMode(true);
//        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
//        registerReceiver(new CallReceiver(), callFilter);

        DemoHelper.getInstance().init(this);

        // 请确保环信SDK相关方法运行在主进程，子进程不会初始化环信SDK（该逻辑在EaseUI.java中）
        if (EaseUI.getInstance().isMainProcess(this)) {
            // 初始化华为 HMS 推送服务, 需要在SDK初始化后执行
            //    HMSPushHelper.getInstance().initHMSAgent(instance);

            EMPushHelper.getInstance().setPushListener(new PushListener() {
                @Override
                public void onError(EMPushType pushType, long errorCode) {
                    // TODO: 返回的errorCode仅9xx为环信内部错误，可从EMError中查询，其他错误请根据pushType去相应第三方推送网站查询。
                    EMLog.e("PushClient", "Push client occur a error: " + pushType + " - " + errorCode);
                }
            });
        }

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
