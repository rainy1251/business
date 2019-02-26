package com.service.business.ui.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.service.business.R;
import com.service.business.model.GoodsOneBean;
import com.service.business.model.GoodsThreeBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.OneAdapter;
import com.service.business.ui.adapter.ThreeAdapter;
import com.service.business.ui.adapter.TwoAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;
import com.service.business.ui.view.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

public class SelectGoodsActivity extends BaseActivity {

    @BindView(R.id.lv_one)
    ListView lvOne;
    @BindView(R.id.lv_two)
    ListView lvTwo;
    @BindView(R.id.lv_detail)
    GridView lvDetail;
    @BindView(R.id.tv_right)
    TextView tv_right;
    private ArrayList<GoodsOneBean.DatasBean> dataOne;
    private OneAdapter oneAdapter;
    private TwoAdapter twoAdapter;
    private ArrayList<GoodsOneBean.DatasBean> dataTwo;
    private ArrayList<GoodsThreeBean.ItemBean.ListBean> dataThree;
    private ArrayList<GoodsThreeBean.ItemBean.ListBean> goodsList=new ArrayList<>();
    private ThreeAdapter threeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_goods;
    }

    @Override
    public void initView() {
        setToolbarNoEN(R.id.toolbar, "购物清单");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("完成");
    }

    @Override
    public void initData() {
        oneAdapter = new OneAdapter(this);
        lvOne.setAdapter(oneAdapter);
        twoAdapter = new TwoAdapter(this);
        lvTwo.setAdapter(twoAdapter);
        threeAdapter = new ThreeAdapter(this);
        lvDetail.setAdapter(threeAdapter);
        requeastGoodsOne();
    }

    @Override
    protected void initListener() {
        lvOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String one_value = dataOne.get(position).value;
                requeastGoodsTwo(one_value);
            }
        });
        lvTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String two_value = dataTwo.get(position).value;
                requeastGoodsDetail(two_value);
            }
        });
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsThreeBean.ItemBean.ListBean item = (GoodsThreeBean.ItemBean.ListBean) parent.getAdapter().getItem(position);
                if (!item.isCheck){
                    item.isCheck=true;
                    goodsList.add(item);
                    MyToast.show(item.name);
                }else{
                    MyToast.show("已添加过该商品");
                }

            }
        });
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(goodsList));
                finish();
            }
        });

    }


    private void requeastGoodsOne() {
        NetUtils.getBuildByGet("/app/category/catLevelOne").execute(new GenericsCallback<GoodsOneBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(GoodsOneBean response, int id) {
                dataOne = response.data;
                if (dataOne.size() > 0) {
                    oneAdapter.addData(dataOne, true);
                }
            }
        });
    }

    private void requeastGoodsTwo(String value) {
        NetUtils.getBuildByGet("/app/category/catLevelTwo").execute(new GenericsCallback<GoodsOneBean>(new JsonGenericsSerializator()) {

            @Override
            public void onResponse(GoodsOneBean response, int id) {
                dataTwo = response.data;
                twoAdapter.addData(dataTwo, true);
            }
        });
    }


    private void requeastGoodsDetail(String value) {
        NetUtils.getBuildByGet("/app/goods/list?catId=" + value + "&page=1&limit=20&sort=add_time&order=desc").execute(new GenericsCallback<GoodsThreeBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(GoodsThreeBean response, int id) {
                dataThree = response.data.items.list;

                threeAdapter.addData(dataThree, true);

            }
        });
    }



}
