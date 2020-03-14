package com.xx.yuefang.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentMainBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.activity.MainActivity;
import com.xx.yuefang.ui.adapter.MyViewPagerAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.fragment.main.HeadLineFragment;
import com.xx.yuefang.ui.fragment.main.HomeFragment;
import com.xx.yuefang.ui.fragment.main.MeFragment;
import com.xx.yuefang.ui.fragment.main.NewsFragment;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import java.util.ArrayList;

public class MainFragment extends LazyLoadFragment {

    private FragmentMainBinding binding;
    private MyViewPagerAdapter adapter;
    private View view;
    private ArrayList<BaseFragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
            binding.setFragment(this);
            view = binding.getRoot();
            initView();
            initlisten();
            MainActivity activity= (MainActivity) getActivity();
            Intent intent = activity.getIntent();
            if(intent!=null){
                activity.handleScheme(intent);
            }
        }
        return view;
    }


    @Override
    public void initData() {}

    @Override
    public void initView() {
        fragments = initFragments();
        adapter = new MyViewPagerAdapter(getChildFragmentManager(), fragments);
        binding.mainViewPager.setAdapter(adapter);
        binding.mainViewPager.setOffscreenPageLimit(3);
    }

    public void refreshNewsCount(int newsCount) {
        binding.setNewsCount(newsCount);
    }

    @Override
    public void initlisten() {
        binding.mainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                binding.setPosition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    private int position;

    public int getPosition() {
        return position;
    }


    public void togglePager(int index) {
        if (index == 3) {
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean == null) {
                Presenter.getInstance().step2Fragment("login");
                return;
            } else {
                binding.mainViewPager.setCurrentItem(index, false);
                binding.setPosition(index);
                this.position = index;
            }
        } else {
            binding.mainViewPager.post(new Runnable() {
                @Override
                public void run() {
                    binding.mainViewPager.setCurrentItem(index, false);
                    position = index;
                    binding.setPosition(index);
                }
            });

        }

    }


    public ArrayList initFragments() {
        String[] titleArray = {"首页", "头条", "消息", "我"};
        binding.setTitleArray(titleArray);
        ArrayList fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new HeadLineFragment());
        fragments.add(new NewsFragment());
        fragments.add(new MeFragment());
        return fragments;
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        setUserVisibleHint(false);
        dispatchParentHideState();
    }





}
