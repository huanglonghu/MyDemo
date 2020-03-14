package com.xx.yuefang.ui.fragment.me.member;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.BrowseRecords;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutBasicBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.MyFootPrintListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyFootPrint extends BaseFragment {

    private LayoutBasicBinding binding;
    private List<BrowseRecords.DataBeanX.DataBean> data;
    private MyFootPrintListAdapter myFootPrintListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_basic, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }


    private View emptyView;
    public void showEmptyLayout() {
        if (data.size() ==0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("您还没有浏览记录");
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void initData() {
        HttpUtil.getInstance().getFootprint().subscribe(
                str -> {
                    BrowseRecords browseRecords = GsonUtil.fromJson(str, BrowseRecords.class);
                    BrowseRecords.DataBeanX datax = browseRecords.getData();
                    if (datax != null) {
                        List<BrowseRecords.DataBeanX.DataBean> list = datax.getData();
                        data.clear();
                        data.addAll(list);
                        binding.top.setText("共浏览" + data.size() + "套房源");
                        binding.setCount(data.size());
                        myFootPrintListAdapter.notifyDataSetChanged();
                    }
                   showEmptyLayout();

                }
        );
    }

    @Override
    public void initView() {
        binding.setTitle("我的足迹");
        data = new ArrayList<>();
        myFootPrintListAdapter = new MyFootPrintListAdapter(getContext(), data, R.layout.list_myfootprint_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(data.get(position).getId());
                        HttpUtil.getInstance().deleteBrowseRecords(ids).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                                    data.remove(position);
                                    myFootPrintListAdapter.removeByPosition(position);
                                    binding.top.setText("共浏览" + data.size() + "套房源");
                                    binding.setCount(data.size());
                                    myFootPrintListAdapter.notifyDataSetChanged();
                                }
                        );
                    }
                }, "您确定要删除这条浏览记录?").show();
            }

            @Override
            public void select() {
                binding.selectAll.setSelected(false);
            }
        });
        binding.lvData.setAdapter(myFootPrintListAdapter);
    }

    @Override
    public void initlisten() {
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.selectAll.isSelected();
                binding.selectAll.setSelected(!selected);
                myFootPrintListAdapter.selectAll(!selected);
            }
        });

        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 0) {
                    Toast.makeText(getContext(), "没有足迹可删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<Integer, Boolean> selectMap = myFootPrintListAdapter.getSelectMap();
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<BrowseRecords.DataBeanX.DataBean> selectList = new ArrayList<>();
                for (int i : selectMap.keySet()) {
                    Boolean isSelect = selectMap.get(i);
                    if (isSelect) {
                        BrowseRecords.DataBeanX.DataBean dataBean = data.get(i);
                        ids.add(dataBean.getId());
                        selectList.add(dataBean);
                    }
                }
                if (ids.size() == 0) {
                    Toast.makeText(getContext(), "请勾选足迹记录", Toast.LENGTH_SHORT).show();
                    return;
                }
                new TipDialog(getContext(), new ClickSureListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void clickSure() {
                        HttpUtil.getInstance().deleteBrowseRecords(ids).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "批量删除成功", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < selectList.size(); i++) {
                                        BrowseRecords.DataBeanX.DataBean dataBean = selectList.get(i);
                                        data.remove(dataBean);
                                    }
                                    myFootPrintListAdapter.clearSelectMap();
                                    binding.setCount(data.size());
                                    binding.selectAll.setSelected(false);
                                }
                        );
                    }
                }, "您确定要删除勾选的足迹").show();


            }
        });


        binding.lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",data.get(position).getPremisesBaseId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });


    }


}
