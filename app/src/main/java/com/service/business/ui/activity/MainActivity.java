package com.service.business.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.service.business.R;
import com.service.business.hxim.ConversationListFragment;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.event.MessageUpDataUIEvent;
import com.service.business.ui.fragment.FindFragment;
import com.service.business.ui.fragment.SystemFragment;
import com.service.business.ui.fragment.WodeFragment;
import com.service.business.ui.utils.PermissionUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.rb_menu_home)
    RadioButton rbMenuHome;
    @BindView(R.id.rb_menu_find)
    RadioButton rbMenuFind;
    @BindView(R.id.rb_menu_wode)
    RadioButton rbMenuWode;
    @BindView(R.id.rg_menu)
    RadioGroup rgMenu;
    @BindView(R.id.rb_menu_system)
    RadioButton rbMenuSystem;
    private Fragment preFragment;
    private List<Fragment> fragments;
    private AlertDialog alertDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        if (PermissionUtils.checkReadPermission(new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                , PermissionUtils.REQUEST_Location, this)) {
        }
        EventBus.getDefault().register(this);

    }

    public static void logout(Context context, boolean b) {

    }

    @Override
    public void initData() {
            fragments = new ArrayList<Fragment>();
          fragments.add(new ConversationListFragment());
            fragments.add(new FindFragment());
            fragments.add(new SystemFragment());
            fragments.add(new WodeFragment());
            rgMenu.setOnCheckedChangeListener(this);
            rbMenuHome.setChecked(true);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_menu_home:
                changeFragment(0);
                break;
            case R.id.rb_menu_find:
                changeFragment(1);

                break;
            case R.id.rb_menu_system:

                changeFragment(2);
                break;
            case R.id.rb_menu_wode:

                changeFragment(3);
                break;
        }
    }

    private void changeFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        Fragment fragment = fragments.get(index);
        if (preFragment != null) {
            transaction.hide(preFragment);
        }
        if (fragment.isAdded()) {

            transaction.show(fragment);
        } else {

            transaction.add(R.id.container, fragment);
        }
        //transaction.commit();
        transaction.commitAllowingStateLoss();
        preFragment = fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
//        if (fragment instanceof FindFragment) {//判断是不是所属Fragment
//            WebView mwebview = (WebView) fragment.getView().findViewById(R.id.mwebview);
//            if (mwebview.canGoBack()) {
//                mwebview.goBack();
//            } else {
//                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//                    showDialog(100);
//                    return true;
//                } else
//                    return super.onKeyDown(keyCode, event);
//
//            }
//        }else{
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showDialog(100);
            return true;
        } else
            return super.onKeyDown(keyCode, event);

        //  }
        //return false;

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 100) {
            return new AlertDialog.Builder(this)
                    .setMessage("确定要退出?")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    finish();
                                    Process.killProcess(Process.myPid());

                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            }).create();
        }
        return null;
    }

    //fragment重叠bug
    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置参数
        builder.setTitle("提示")
                .setMessage("请先登录")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                        intent.putExtra("isLogin", true);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageUpDataUIEvent messageEvent) {
        if (messageEvent.getMessage().equals("更新")) {
            rbMenuHome.setChecked(true);
            changeFragment(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }
}
