package com.xx.yuefang.ui.fragment.me.developer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetHouseResourcesList;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.bean.PremisesNumResponse;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentFzsHousesResourcesBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.NewsPageAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.widget.SearchDialog1;
import com.xx.yuefang.ui.widget.SearchDialog3;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;

public class HouseResouces extends LazyLoadFragment {

    private FragmentFzsHousesResourcesBinding binding;
    private String[] titles;
    private NewsPageAdapter pageAdapter;
    private ArrayList<BaseFragment> fragments;
    private SearchDialog3 searchDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fzs_houses_resources, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }


    private String premisesName;

    public String getPremisesName() {
        return premisesName;
    }

    public void setPremisesName(String premisesName) {
        this.premisesName = premisesName;
    }

    @Override
    public void initData() {
        getNum();
    }

    public void getNum() {
        HttpUtil.getInstance().getPremisesNum(premisesName).subscribe(
                str -> {
                    PremisesNumResponse premisesNumResponse = GsonUtil.fromJson(str, PremisesNumResponse.class);
                    PremisesNumResponse.DataBean data = premisesNumResponse.getData();
                    int allPremisesNum = data.getAllPremisesNum();
                    int isActivePremisesNum = data.getIsActivePremisesNum();
                    int isNoActivePremisesNum = data.getIsNoActivePremisesNum();
                    titles[0] = originalTitles.get(0) + "(" + allPremisesNum + ")";
                    titles[1] = originalTitles.get(1) + "(" + isActivePremisesNum + ")";
                    titles[2] = originalTitles.get(2) + "(" + isNoActivePremisesNum + ")";
                    binding.tab.setViewPager(binding.vp);
                }
        );
    }


    private ArrayList<String> originalTitles = new ArrayList<>();

    @Override
    public void initView() {
        UserBean userBean = UserOption.getInstance().querryUser();
        titles = new String[3];
        if (userBean.getUserType() == 2) {
            originalTitles.add("全部");
            originalTitles.add("上架楼盘");
            originalTitles.add("未上架楼盘");
        } else {
            originalTitles.add("全部");
            originalTitles.add("上架房源");
            originalTitles.add("未上架房源");
        }
        for (int i = 0; i < originalTitles.size(); i++) {
            String title = originalTitles.get(i);
            titles[i] = title;
        }
        HouseResourcesPage all = new HouseResourcesPage();
        all.setType(0);
        HouseResourcesPage sj = new HouseResourcesPage();
        sj.setType(1);
        HouseResourcesPage wsj = new HouseResourcesPage();
        wsj.setType(2);
        fragments = new ArrayList<>();
        fragments.add(all);
        fragments.add(sj);
        fragments.add(wsj);
        pageAdapter = new NewsPageAdapter(getChildFragmentManager(), fragments, titles);
        binding.vp.setAdapter(pageAdapter);
        binding.vp.setOffscreenPageLimit(3);
        binding.tab.setViewPager(binding.vp);
    }

    @Override
    public void initlisten() {
        binding.houseResoucesToolbar.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchDialog == null) {
                    searchDialog = new SearchDialog3((AppCompatActivity) getActivity(), new ClickSureListener() {
                        @Override
                        public void search3(String premisesName) {
                            int currentItem = binding.vp.getCurrentItem();
                            for (int i = 0; i < fragments.size(); i++) {
                                HouseResourcesPage houseResourcesPage = (HouseResourcesPage) fragments.get(i);
                                if (i == currentItem) {
                                    houseResourcesPage.search(premisesName, true);
                                } else {
                                    houseResourcesPage.search(premisesName, false);
                                }

                            }
                        }
                    });
                }
                searchDialog.show();


            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    protected void loadData() {

    }
}
