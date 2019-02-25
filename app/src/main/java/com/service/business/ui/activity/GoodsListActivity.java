package com.service.business.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.service.business.R;
import com.service.business.model.GoodsOneBean;
import com.service.business.net.GenericsCallback;
import com.service.business.net.JsonGenericsSerializator;
import com.service.business.ui.adapter.ShopCarAdapter;
import com.service.business.ui.base.BaseActivity;
import com.service.business.ui.utils.MyLog;
import com.service.business.ui.utils.MyToast;
import com.service.business.ui.utils.NetUtils;
import com.service.business.ui.view.ChildView;
import com.service.business.ui.view.ExpandTabView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.generateViewId;

public class GoodsListActivity extends BaseActivity {

    @BindView(R.id.tv_goods)
    TextView tvGoods;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    private ArrayList<String> selectGoodsList = new ArrayList<>();
    private String goods = "";
    private ChildView viewLeft;
    @BindView(R.id.expandtab_view)
    ExpandTabView expandTabView;

    public ArrayList<GoodsOneBean.DatasBean> getDataOne() {
        return dataOne;
    }

    public void setDataOne(ArrayList<GoodsOneBean.DatasBean> dataOne) {
        this.dataOne = dataOne;
    }

    private ArrayList<GoodsOneBean.DatasBean> dataOne;

    public ArrayList<GoodsOneBean.DatasBean> getDataTwo() {
        return dataTwo;
    }

    public void setDataTwo(ArrayList<GoodsOneBean.DatasBean> dataTwo) {
        this.dataTwo = dataTwo;
    }

    private ArrayList<GoodsOneBean.DatasBean> dataTwo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goodslist;
    }

    @Override
    public void initView() {

        setToolbarNoEN(R.id.toolbar, "购物清单");
        NetUtils.getBuildByGet("/app/category/catLevelOne").execute(new GenericsCallback<GoodsOneBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(GoodsOneBean response, int id) {
                dataOne = response.data;
                if (dataOne.size()>0){
                    requeastGoodsTwo();
                }
            }
        });

    }

    private void requeastGoodsTwo() {
        NetUtils.getBuildByGet("/app/category/catLevelTwo").execute(new GenericsCallback<GoodsOneBean>(new JsonGenericsSerializator()) {
            @Override
            public void onResponse(GoodsOneBean response, int id) {
                dataTwo = response.data;
                setDataOne(dataOne);
                setDataTwo(dataTwo);
                setSelectList();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void setSelectList() {
        viewLeft = new ChildView(this);
        mViewArray.add(viewLeft);
        //设置顶部数据信息
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("全部");
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle(viewLeft.getShowText(), 0);
        viewLeft.setOnSelectListener(new ChildView.OnSelectListener() {

            @Override
            public void getValue(String showText) {

                onRefresh(viewLeft, showText);

            }
        });
    }

    @Override
    protected void initListener() {

    }

    //视图被点击后刷新数据
    private void onRefresh(View view, String showText) {

        selectGoodsList.add(showText);

        if (!goods.equals("")) {
            goods = goods + "," + showText;
        } else {
            goods = goods + showText;
        }
        expandTabView.setTitle(goods, 0);

        Toast.makeText(GoodsListActivity.this, goods, Toast.LENGTH_SHORT).show();

    }

    //获取当前的view
    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {

        if (!expandTabView.onPressBack()) {
            finish();
        }

    }


    @OnClick({R.id.tv_goods, R.id.tv_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_goods:
                showPop(selectGoodsList);
                break;
            case R.id.tv_order:

                break;
        }
    }
    /**
     * 弹出pop
     *
     * @param titleList
     */
    private void showPop(final ArrayList<String> titleList) {
        //设置contentView
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.layout_pop, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //设置各个控件的点击响应
        ListView lv_shop = (ListView) contentView.findViewById(R.id.lv_shop);
        TextView tv_close = (TextView) contentView.findViewById(R.id.tv_close);
        ShopCarAdapter allAdapter = new ShopCarAdapter(this);
        allAdapter.addData(titleList, false);
        lv_shop.setAdapter(allAdapter);

        //底部显示PopupWindow
        mPopWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPopWindow != null) {
                    mPopWindow.dismiss();
                }
            }
        });
//
        lv_shop.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            private TextView tv_title;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {

                tv_title = (TextView) view.findViewById(R.id.tv_title);

            }
        });
    }

}
