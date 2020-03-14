package com.xx.yuefang.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;
import java.util.ArrayList;

public class NewsPageAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private ArrayList<BaseFragment> fragments;


    public NewsPageAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        LogUtil.log("==========getPageTitle==============");
        return titles[position];
    }


    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }


    public void setTitles(String[] titles) {
        this.titles = titles;
    }
}
