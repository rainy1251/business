package com.service.business.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.service.business.hxim.DemoHelper;
import com.service.business.ui.utils.SPUtils;
import com.service.business.R;
import com.service.business.model.StateBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;
import com.service.business.ui.utils.UiUtils;
import com.service.business.ui.event.MessageUpDataUIEvent;
import com.service.business.ui.view.TimerCount;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_mobile)
    LinearLayout llMobile;
    @BindView(R.id.ll_code)
    LinearLayout llCode;
    @BindView(R.id.rl_radio)
    RelativeLayout rl_radio;
    @BindView(R.id.rg_type)
    RadioGroup rgType;
    @BindView(R.id.rb_y)
    RadioButton rbY;
    @BindView(R.id.rb_p)
    RadioButton rbp;
    private boolean isLogin;
    private String api;
    private String type = "1";

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        if (isLogin) {
            setToolbarNoEN(R.id.toolbar, "登录");
            tv_title.setText("登录");
            llMobile.setVisibility(View.GONE);
            llCode.setVisibility(View.GONE);
            rl_radio.setVisibility(View.GONE);
        } else {

            setToolbarNoEN(R.id.toolbar, "注册");
            tv_title.setText("注册");
//            etCode.setVisibility(View.VISIBLE);
//            etUsername.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initData() {

    }


    @Override
    protected void initListener() {
        rgType.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.tv_getCode, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                String username = etUsername.getText().toString().trim();
                if (UiUtils.isMobile(username)) {
                    Map<String, String> map = new HashMap();
                    map.put("mobile", username);
                    String postContent = getPostContent(map);
                    String api = "/app/auth/regCaptcha";
                    getCode(api, postContent);
                } else {
                    MyToast.show("请输入正确的手机号");
                }
                break;
            case R.id.tv_register:
                String mobile = etUsername.getText().toString().trim();
                String code = etCode.getText().toString().trim();
                String nickname = etNickname.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (isLogin) {

                } else {
                    if (mobile.isEmpty() && !UiUtils.isMobile(mobile)) {
                        MyToast.show("请输入手机号");
                        return;
                    }
                    if (!UiUtils.isMobile(mobile)) {
                        MyToast.show("请输入正确的手机号");
                        return;
                    }

                    if (code.isEmpty()) {
                        MyToast.show("验证码不能为空");
                        return;
                    }

                }


                if (nickname.isEmpty()) {
                    MyToast.show("请输入用户名");
                    return;
                }

                if (password.isEmpty()) {
                    MyToast.show("密码不能为空");
                    return;
                }


                Map<String, String> map = new HashMap();
                map.put("username", nickname);
                map.put("password", password);
                if (isLogin) {
                    api = "/app/auth/login";
                } else {
                    map.put("mobile", mobile);
                    map.put("code", code);
                    map.put("type", type);
                    api = "/app/auth/register";
                }

                String postContent = getPostContent(map);
                getRegister(api, postContent);

                break;
        }
    }

    /***
     * 获取请求体
     * @return
     * @param map
     */
    private String getPostContent(Map<String, String> map) {
        JSONObject jsonObject = (JSONObject) JSONObject.wrap(map);
        String content = jsonObject.toString();
        return content;
    }

    /**
     * 获取验证码
     */
    private void getCode(String api, String content) {
        NetUtils.getBuildByPost(api, content)
                .execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator(), etUsername) {
                    @Override
                    public void onResponse(StateBean response, int id) {

                        if (response.errmsg.equals("成功")) {
                            MyToast.show("已发送");
                            TimerCount timer = new TimerCount(60000, 1000, tvGetCode, true);
                            timer.start();
                        } else {
                            MyToast.show("发送失败");
                        }

                    }
                });
    }

    /**
     * 注册
     */
    private void getRegister(String api, String content) {
        NetUtils.getBuildByPostToken(api, content)
                .execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator(), etUsername) {
                    @Override
                    public void onResponse(StateBean response, int id) {

                        if (response.errmsg.equals("成功")) {
                            String userId = response.data.userInfo.userId;
                            String token = response.data.token;
                            int type = response.data.userInfo.type;
                            SPUtils.save("token", token);
                            SPUtils.save("userId", userId);
                            SPUtils.save("type", type);
                            String password = etPassword.getText().toString().trim();
//                            doLogin(userId, response.data.imToken);
//                            SPUtils.save("IMToken", response.data.imToken);
                            if (!isLogin) {
                            } else {
                                MyToast.show("登陆成功");
                            }
                            loginIM(userId, password);
                            finish();
                            EventBus.getDefault().post(new MessageUpDataUIEvent("更新"));
                        } else if (response.errmsg.contains("审核")) {
                            MyToast.show(response.errmsg);

                        } else if (response.errmsg.contains("不存在")) {
                            MyToast.show(response.errmsg);
                            showLoginDialog();
                        } else {
                            MyToast.show(response.errmsg);
                        }
                    }
                });
    }

//    /**
//     * 请求im登陆令牌
//     *
//     * @param userId
//     * @param token
//     */
//    private void requestIMSign(String api, final String userId, final String token) {
//
//        NetUtils.getBuildByGet(api)
//                .execute(new GenericsCallback<IMBean>(new JsonGenericsSerializator(), etUsername) {
//                    @Override
//                    public void onResponse(IMBean response, int id) {
//
//                        if (response.errno == 0) {
//
//                            int index1 = response.data.indexOf("token\":\"");
//                            int index2 = response.data.indexOf("\",\"accid\"");
//                            if (index2 - index1 > 8) {
//
//                                String IMToken = response.data.substring(index1 + 8, index2);
//                                doLogin(userId, IMToken);
//                                SPUtils.save("IMToken", IMToken);
//                            }
//                        }
//
//
//                    }
//
//                });
//
//    }
//TODO 网易云信登录
//    public void doLogin(String account, String token) {
//
//        //MyLog.show(account+"\n"+token);
//        AbortableFuture<LoginInfo> loginRequest = NimUIKit.login(new LoginInfo(account, token), new RequestCallback<LoginInfo>() {
//            @Override
//            public void onSuccess(LoginInfo param) {
//                MyToast.show("登录IM成功");
//                String account = param.getAccount();
//                String token = param.getToken();
//                SPUtils.save(Contents.IMAccoune, account);
//                SPUtils.save(Contents.IMToken, token);
//                NimUIKit.setAccount(account);
//                AVChatKit.setAccount(account);
//            }
//
//            @Override
//            public void onFailed(int code) {
//
//                if (code == 302 || code == 404) {
//                    MyToast.show("登录IM失败");
//                    MyLog.show(code+"===code");
//                } else {
//                    MyToast.show("登录IM失败:" + code);
//                }
//
//            }
//
//            @Override
//            public void onException(Throwable exception) {
//                MyToast.show("登录IM异常");
//
//            }
//
//
//        });
//    }

    private void loginIM(String userId, String pwd) {

        EMClient.getInstance().login(userId, pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
              //  DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName("levi");
               DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                Intent intent = new Intent(RegisterActivity.this, ChatActivity.class);
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！" + message + "," + code);
            }
        });
    }
    private void setIMUserName( String username) {
        Map<String, String> map = new HashMap();
        map.put("nickname", "frewf");
        String postContent = getPostContent(map);
        NetUtils.getBuildByPostToken("/app/im/uinfos/update", postContent)
                .execute(new GenericsCallback<StateBean>(new JsonGenericsSerializator(), etUsername) {
                    @Override
                    public void onResponse(StateBean response, int id) {

                        if (response.errmsg.equals("成功")) {

                        } else {
                            MyToast.show(response.errmsg);
                        }
                    }
                });
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.rb_y:
                type = "1";
                break;
            case R.id.rb_p:
                type = "2";
                break;

        }
    }

    public void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        // 设置参数
        builder.setTitle("提示")
                .setMessage("请先注册")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                        intent.putExtra("isLogin", false);
                        startActivity(intent);
                        finish();
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
