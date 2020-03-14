package com.xx.yuefang.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;


public class HouseDetailPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<HouseDetailFragment> fragments = new ArrayList<>();


    public HouseDetailPagerAdapter(FragmentManager fm, ArrayList<HouseDetailFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



}
