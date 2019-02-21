package com.service.business.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/6.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    //    ArrayList<Fragment> list;
//
//    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
//        super(fm);
//        this.list = list;
//    }
//
//
//
//    @Override
//    public Fragment getItem(int arg0) {
//        return list.get(arg0);
//    }
//    @Override
//    public int getCount() {
//
//        return list.size();
//    }
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    public MyFragmentAdapter(FragmentManager fm, ArrayList fragments) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = fragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        fm.beginTransaction().hide(fragments.get(position)).commit();
    }

}