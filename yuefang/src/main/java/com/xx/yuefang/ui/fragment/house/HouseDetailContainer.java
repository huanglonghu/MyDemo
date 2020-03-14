package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesIdList;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.databinding.FragmentHouseDetailContainerBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.HouseDetailPagerAdapter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailContainer extends LazyLoadFragment {

    private FragmentHouseDetailContainerBinding binding;
    private int index;
    private ArrayList<HouseDetailFragment> fragments;
    private UserObserver<EventData> userObserver;
    private HouseDetailPagerAdapter houseDetailPagerAdapter;
    private ArrayList<Integer> backStacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house_detail_container, container, false);
            initlisten();
            initView();
            backStacks = new ArrayList<>();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        houseDetailPagerAdapter = new HouseDetailPagerAdapter(getChildFragmentManager(), fragments);
        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        index = HttpParam.ids.indexOf(id);
        boolean isAret = bundle.getBoolean("isAret");
        for (int i = 0; i < HttpParam.ids.size() + 2; i++) {
            HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
            Bundle bundle2 = new Bundle();
            if (i == 0) {
                bundle2.putInt("id", HttpParam.ids.get(HttpParam.ids.size() - 1));
            } else if (i == HttpParam.ids.size() + 1) {
                bundle2.putInt("id", HttpParam.ids.get(0));
            } else {
                bundle2.putInt("id", HttpParam.ids.get(i - 1));
            }

            if (i == index + 1) {
                bundle2.putBoolean("isFirst",true);
                if(isAret){
                    bundle2.putBoolean("isAret",true);
                }
            }
            houseDetailFragment.setArguments(bundle2);
            fragments.add(houseDetailFragment);
        }
        binding.page.setAdapter(houseDetailPagerAdapter);
        binding.page.setOffscreenPageLimit(HttpParam.ids.size() + 2);
        binding.page.setCurrentItem(index + 1);
    }

    @Override
    public void initlisten() {
        if (userObserver == null) {
            userObserver = new UserObserver<EventData>() {
                @Override
                public void onUpdate(UserObservable<EventData> observable, EventData data) {
                    switch (data.getEventType()) {
                        case UserObservable.TYPE_HOUSEDETAIL_TOGGLE:
                            Bundle bundle = data.getData();
                            int id = bundle.getInt("id");
                            int currentId = bundle.getInt("currentId");
                            int index = HttpParam.ids.indexOf(id);
                            int currentIndex = HttpParam.ids.indexOf(currentId);
                            backStacks.add(currentIndex);
                            binding.page.setCurrentItem(index + 1, false);
                            break;
                    }
                }
            };
            UserObservable.getInstance().register(userObserver);
        }
        binding.page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {//position表示在扩展Fragments中即将要显示的Fragment的索引
                LogUtil.log("========BBBBBBBBB=========="+position);
                if (position == 0) {//首位扩展的item
                    //延迟执行才能看到动画
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.page.setCurrentItem(fragments.size() - 2, false);//跳转到末位
                        }
                    }, 100);
                } else if (position == fragments.size() - 1) {//末位扩展的item
                    //延迟执行才能看到动画
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            binding.page.setCurrentItem(1, false);//跳转到首位
                        }
                    }, 100);
                }
                index = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userObserver != null) {
            UserObservable.getInstance().unregister(userObserver);
            userObserver = null;
        }
    }

    @Override
    protected void loadData() {}

    @Override
    public void onKeyDown() {
        super.onKeyDown();
        if (backStacks.size() > 0) {
            Integer index = backStacks.get(backStacks.size() - 1);
            backStacks.remove(index);
            binding.page.setCurrentItem(index + 1);
        } else {
            Presenter.getInstance().back();
        }
        getChildFragmentManager().popBackStack();
    }


}
