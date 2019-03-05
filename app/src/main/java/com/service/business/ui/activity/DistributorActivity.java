package com.service.business.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.DistributorBean;
import com.service.business.model.StateBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.DistributorAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.event.MessageDistributorEvent;
import com.service.business.ui.event.MessageUpDataUIEvent;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class DistributorActivity extends BaseActivity {

    @BindView(R.id.lv_view)
    ListView mListView;
    private ArrayList<DistributorBean.ItemBean.ListBean> distributorList;
    private DistributorAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_distributor;
    }

    @Override
    public void initView() {
        setToolbarNoEN(R.id.toolbar, "选择配送商");
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        getDistributorList();
        mAdapter = new DistributorAdapter(this);
        mListView.setAdapter(mAdapter);

    }


    @Override
    protected void initListener() {

    }

    /**
     * 获取配送商列表
     */
    private void getDistributorList() {
        NetUtils.getBuildByGet("/app/userInfo/distributors?page=1&limit=10&sort=add_time&order=desc")
                .execute(new GenericsCallback<DistributorBean>(new JsonGenericsSerializator()) {
                    @Override
                    public void onResponse(DistributorBean response, int id) {
                        if (response.errno==0){

                            distributorList = response.data.items.list;
                            mAdapter.addData(distributorList,true);
                        }
                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageDistributorEvent messageEvent) {
        if (messageEvent.getMessage()!=null) {
            finish();
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
