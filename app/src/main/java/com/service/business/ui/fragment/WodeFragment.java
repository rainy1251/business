package com.service.business.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.netease.nim.uikit.SPUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.service.business.R;
import com.service.business.model.UserInfoBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.activity.RegisterActivity;
import com.service.business.ui.activity.SessionListActivity;
import com.service.business.ui.base.BaseFragment;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;
import com.service.business.ui.event.MessageUpDataUIEvent;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.service.business.ui.utils.UiUtils.showLoginDialog;


public class WodeFragment extends BaseFragment {
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.tv_login)
    TextView tv_login;
    private String errmsg;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_wode;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {

        requestInfo();

    }

    /**
     * 请求用户信息
     */
    private void requestInfo() {
        NetUtils.getBuildByGet("/app/userInfo/detail").execute(new GenericsCallback<UserInfoBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(UserInfoBean response, int id) {
                errmsg = response.errmsg;
                if (response.errmsg.equals("成功")) {
                    Glide.with(getActivity()).load(response.data.headUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(ivAvatar);
                    tv_login.setText(response.data.nickname);
                    SPUtils.save("username", response.data.nickname);

                    if (response.data.nickname != null) {

                        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
                        fields.put(UserInfoFieldEnum.Name, response.data.nickname);
                        fields.put(UserInfoFieldEnum.AVATAR, response.data.headUrl);

                        NIMClient.getService(UserService.class).updateUserInfo(fields)
                                .setCallback(new RequestCallbackWrapper<Void>() {
                                    @Override
                                    public void onResult(int i, Void aVoid, Throwable throwable) {

                                    }
                                });
                    }
                }
            }
        });
    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.tv_edit, R.id.tv_register, R.id.ll_login,R.id.iv_message})
    public void onViewClicked(View view) {
        String token = SPUtils.getString("token");
        switch (view.getId()) {
            case R.id.tv_edit:
                if (token.equals("")) {
                    MyToast.show("请先登录");
                    return;
                }
                if (errmsg.equals("成功")) {
//                    Intent intent_edit = new Intent(getContext(), EditUserDetailActivity.class);
//                    startActivity(intent_edit);
                } else {
                    MyToast.show("请先登录");
                }

                break;
            case R.id.tv_register:
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_message:
                String mtoken = SPUtils.getString("token");
                if (mtoken.equals("")) {
                    showLoginDialog(getActivity());
                    return;
                }
                Intent intent_session = new Intent(getContext(), SessionListActivity.class);
                startActivity(intent_session);
                break;
            case R.id.ll_login:

                if (token.equals("")) {
                    Intent intent_login = new Intent(getContext(), RegisterActivity.class);
                    intent_login.putExtra("isLogin", true);
                    startActivity(intent_login);
                } else {
                    showEnterDialog();
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpDataUIEvent messageEvent) {
        if (messageEvent.getMessage().equals("更新")) {

            requestInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    public void showEnterDialog() {

        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 设置参数
        builder.setTitle("提示")
                .setMessage("切换账号将会退出已有账户")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                        NIMClient.getService(AuthService.class).logout();
                        SPUtils.save("token", "");
                        SPUtils.save("type", 1);
                        Intent intent_login = new Intent(getContext(), RegisterActivity.class);
                        intent_login.putExtra("isLogin", true);
                        startActivity(intent_login);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}
