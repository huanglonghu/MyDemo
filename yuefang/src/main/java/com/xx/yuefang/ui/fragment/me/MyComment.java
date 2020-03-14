package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointList;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutBasic2Binding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.CommentsAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseArFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MyComment extends BaseFragment {

    private LayoutBasic2Binding binding;
    private List<AppointBean> list;
    private CommentsAdapter commentsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_basic2, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        HttpUtil.getInstance().queryCommentList().subscribe(
                str -> {
                    AppointList appointList = GsonUtil.fromJson(str, AppointList.class);
                    AppointList.DataBeanX data = appointList.getData();
                    if (data != null) {
                        list.addAll(data.getData());
                        commentsAdapter.notifyDataSetChanged();
                        binding.setCount(list.size());
                        binding.top.setText("已点评" + list.size());
                    }
                    showEmptyLayout();
                }

        );
    }

    private View emptyView;

    public void showEmptyLayout() {
        if (list.size() == 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("暂无评论");
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
    public void initView() {
        binding.setTitle("我的点评");
        list = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(getContext(), list);
        binding.lvData.setAdapter(commentsAdapter);
    }

    @Override
    public void initlisten() {
        binding.lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",list.get(position).getPremisesBaseId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });
    }
}
