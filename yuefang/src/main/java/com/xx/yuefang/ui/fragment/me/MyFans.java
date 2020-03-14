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
import com.xx.yuefang.bean.FansList;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.FragmentFansBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.FansListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class MyFans extends BaseFragment implements ScrollRefreshListView.RefreshData {


    private FragmentFansBinding binding;
    private FansListAdapter fansListAdapter;
    private ArrayList<FansList.DataBeanX.DataBean> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fans, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.lvMyFans.setRefreshData(this);
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        getFansDatas(1);


    }

    private void getFansDatas(int page) {
        HttpUtil.getInstance().querryMyFans(page).subscribe(
                str -> {
                    if (page == 1 && list.size() > 0) {
                        list.clear();
                        fansListAdapter.clearView();
                    }
                    FansList fansList = GsonUtil.fromJson(str, FansList.class);
                    FansList.DataBeanX data = fansList.getData();
                    if (data != null) {
                        List<FansList.DataBeanX.DataBean> data1 = data.getData();
                        if (data1 != null && data1.size() > 0) {
                            list.addAll(data1);
                            binding.setCount(list.size());
                            fansListAdapter.notifyDataSetChanged();
                            binding.lvMyFans.setLoading(true);
                        } else {
                            binding.lvMyFans.setLoading(false);
                        }
                    }
                    showEmptyLayout();
                }
        );
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        fansListAdapter = new FansListAdapter(getContext(), list, R.layout.list_myfans_item);
        binding.lvMyFans.setAdapter(fansListAdapter);
    }

    private View emptyView;

    public void showEmptyLayout() {
        if (list.size() == 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("您还没有粉丝");
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
    public void initlisten() {

        binding.lvMyFans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FansList.DataBeanX.DataBean dataBean = list.get(position);
                FansDetail fansDetail = new FansDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("fans", dataBean);
                fansDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(fansDetail, "fansDetail");
            }
        });

    }

    @Override
    public void refreshData(int page) {

    }
}
