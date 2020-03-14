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
import com.xx.yuefang.bean.AgentList;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.FragmentAgentBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.AgentListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;

public class Agent extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private FragmentAgentBinding binding;
    private ArrayList<AgentList.DataBean> list;
    private AgentListAdapter agentListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_agent, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.lvMyAgent.setRefreshData(this);
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        getAgenDatas(1);
    }

    private void getAgenDatas(int page) {
        HttpUtil.getInstance().querryMyAgent(page).subscribe(
                str -> {
                    if (page == 1 && list.size() > 0) {
                        list.clear();
                        agentListAdapter.clearView();
                    }
                    AgentList agentList = GsonUtil.fromJson(str, AgentList.class);
                    List<AgentList.DataBean> data = agentList.getData();
                    if (data != null && data.size() > 0) {
                        list.addAll(data);
                        agentListAdapter.notifyDataSetChanged();
                        binding.lvMyAgent.setLoading(true);
                    } else {
                        binding.lvMyAgent.setLoading(false);
                    }
                    showEmptyLayout();
                    binding.setCount(list.size());
                }
        );
    }


    private View emptyView;

    public void showEmptyLayout() {
        if (list.size() == 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("您还没有经纪人");
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
        list = new ArrayList<>();
        agentListAdapter = new AgentListAdapter(getContext(), list, R.layout.list_agent_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                initData();
            }
        });
        binding.lvMyAgent.setAdapter(agentListAdapter);
    }

    @Override
    public void initlisten() {
        binding.lvMyAgent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AgentList.DataBean dataBean = list.get(position);
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", dataBean.getId());
                bundle.putBoolean("isFirst", true);
                bundle.putBoolean("initData", true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });
    }

    @Override
    public void refreshData(int page) {
        getAgenDatas(page);
    }
}
