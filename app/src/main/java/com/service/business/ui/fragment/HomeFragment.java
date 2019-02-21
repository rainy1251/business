package com.service.business.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.service.business.R;
import com.service.business.ui.adapter.MyFragmentAdapter;
import com.service.business.ui.base.BaseFragment;
import com.service.business.ui.utils.UiUtils;
import com.service.business.ui.view.IndicatorView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> mTitleDataList;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        fillfragment();

        initIndicatorTitle();

        initViewPagerIndicator();
    }

    @Override
    protected void initListener() {

    }

    /**
     * 填充fragment
     */
    private void fillfragment() {
        fragments.add(new RecentFragment());
        fragments.add(new ShopingFragment());
        fragments.add(new ServiceFragment());
        fragments.add(new YiLiaoFragment());
    }

    /**
     * 初始化indicator的标题
     */
    private void initIndicatorTitle() {
        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("最近");
        mTitleDataList.add("语音购物");
        mTitleDataList.add("社区服务");
        mTitleDataList.add("医疗保健");

    }

    /**
     * 初始化viewpagerIndicator
     */
    private void initViewPagerIndicator() {
        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(), (ArrayList<Fragment>) fragments);
        IndicatorView.magicIndicator(magicIndicator, mViewPager, mTitleDataList,
                LinePagerIndicator.MODE_EXACTLY, 18, UiUtils.getScreenWidth(),
                true);
        mViewPager.setAdapter(myFragmentAdapter);
    }

}
