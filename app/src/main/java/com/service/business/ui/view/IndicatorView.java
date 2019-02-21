package com.service.business.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.service.business.R;
import com.service.business.ui.utils.UiUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.List;

/**
 * Created by Levi on 2018/4/16 0016.
 */

public class IndicatorView {
    /**
     * 设置MagicIndicator(ViewPagerIndicator)
     *
     * @param magicIndicator magicIndicator
     * @param mViewPager     viewpager
     * @param mTitleDataList 标题集合
     * @param ishome  是否是主页
     */
    public static void magicIndicator(MagicIndicator magicIndicator, final ViewPager mViewPager, final List<String> mTitleDataList,
                                      final int mode, final int textsize, final int indicatorWidth, boolean ishome) {
        final int uncheckColor;
        final int checkedColor;
        final int lineColor;
        if (ishome) {
            uncheckColor = R.color.dark;
            checkedColor = R.color.dark;
            lineColor = R.color.dark;
        } else {
            uncheckColor = R.color.tab_unchecked;
            checkedColor = R.color.tab_checked;
            lineColor = R.color.dark;
        }

        CommonNavigator commonNavigator = new CommonNavigator(UiUtils.getContext());
        magicIndicator.setBackgroundColor(UiUtils.getContext().getResources().getColor(R.color.white));
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);


                colorTransitionPagerTitleView.setNormalColor(UiUtils.getContext().getResources().getColor(uncheckColor));
                colorTransitionPagerTitleView.setSelectedColor(UiUtils.getContext().getResources().getColor(checkedColor));
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setWidth(indicatorWidth / mTitleDataList.size());
                colorTransitionPagerTitleView.setTextSize(textsize);//设置标题字体大小
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(mode);
                indicator.setColors(UiUtils.getContext().getResources().getColor(lineColor));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

}
