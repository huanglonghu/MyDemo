package com.xx.yuefang.ui.fragment.me.member;

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
import com.xx.yuefang.bean.Collection;
import com.xx.yuefang.bean.EmployeeBean;
import com.xx.yuefang.bean.PremisesBean;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutBasicBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.CollectionListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseArFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyCollection extends BaseFragment {

    private LayoutBasicBinding binding;
    private List<Collection.DataBeanX.DataBean> data;
    private CollectionListAdapter myCollectionListAdapter;

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
        if (data.size() == 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("您还没有收藏");
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
        HttpUtil.getInstance().getCollectionList().subscribe(
                str -> {
                    Collection collection = GsonUtil.fromJson(str, Collection.class);
                    Collection.DataBeanX dataBeanX = collection.getData();
                    if (dataBeanX != null) {
                        data.clear();
                        List<Collection.DataBeanX.DataBean> list = dataBeanX.getData();
                        data.addAll(list);
                        binding.top.setText("共收藏" + data.size() + "套房源");
                        binding.setCount(data.size());
                        myCollectionListAdapter.notifyDataSetChanged();
                    }
                    showEmptyLayout();
                }
        );
    }

    @Override
    public void initView() {
        binding.setTitle("我的收藏");
        data = new ArrayList<>();
        myCollectionListAdapter = new CollectionListAdapter(getContext(), data, R.layout.list_myfootprint_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        Collection.DataBeanX.DataBean dataBean = data.get(position);
                        HttpUtil.getInstance().userCollection(dataBean.getPremisesBaseId()).subscribe(
                                str -> {
                                    data.remove(position);
                                    myCollectionListAdapter.removeByPosition(position);
                                    myCollectionListAdapter.notifyDataSetChanged();
                                    binding.setCount(data.size());
                                    binding.top.setText("共收藏" + data.size() + "套房源");
                                }
                        );
                    }
                }, "您确定要删除这条收藏").show();
            }

            @Override
            public void select() {
                binding.selectAll.setSelected(false);
            }
        });
        binding.lvData.setAdapter(myCollectionListAdapter);
    }

    @Override
    public void initlisten() {
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.selectAll.isSelected();
                binding.selectAll.setSelected(!selected);
                myCollectionListAdapter.selectAll(!selected);
            }
        });

        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 0) {
                    Toast.makeText(getContext(), "没有收藏记录可删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<Integer, Boolean> selectMap = myCollectionListAdapter.getSelectMap();
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<Collection.DataBeanX.DataBean> selectList = new ArrayList<>();
                for (int i : selectMap.keySet()) {
                    Boolean isSelect = selectMap.get(i);
                    if (isSelect) {
                        Collection.DataBeanX.DataBean dataBean = data.get(i);
                        ids.add(dataBean.getId());
                        selectList.add(dataBean);
                    }
                }
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选收藏记录", Toast.LENGTH_SHORT).show();
                    return;
                }
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        HttpUtil.getInstance().deleteCollectionRecords(ids).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "批量删除成功", Toast.LENGTH_SHORT).show();
                                    for (int i = 0; i < selectList.size(); i++) {
                                        Collection.DataBeanX.DataBean dataBean = selectList.get(i);
                                        data.remove(dataBean);
                                    }
                                    myCollectionListAdapter.clearSelectMap();
                                    binding.setCount(data.size());
                                    binding.selectAll.setSelected(false);
                                }
                        );

                    }
                }, "您确定要删除勾选的收藏").show();


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
