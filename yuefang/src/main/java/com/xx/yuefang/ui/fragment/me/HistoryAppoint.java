package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointList;
import com.xx.yuefang.bean.GetAllApoint;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutBasicBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.AppointHistoryListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;

public class HistoryAppoint extends BaseFragment {

    private LayoutBasicBinding binding;
    private ArrayList<AppointBean> list;
    private AppointHistoryListAdapter appointHistoryListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_basic, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        getAppointList();
    }

    private void getAppointList() {
        GetAllApoint getAllApoint = new GetAllApoint();
        ArrayList<Integer> states = new ArrayList<>();
        states.add(2);
        states.add(4);
        states.add(5);
        states.add(6);
        states.add(7);
        states.add(8);
        getAllApoint.setLimit(100);
        getAllApoint.setPage(1);
        getAllApoint.setStates(states);
        // 1待接单 3已接单 6已完成 2用户取消 4销售取消 用户爽约 7成交8确认成交
        HttpUtil.getInstance().getAllApoint(getAllApoint).subscribe(
                str -> {
                    AppointList appointList = GsonUtil.fromJson(str, AppointList.class);
                    AppointList.DataBeanX data = appointList.getData();
                    if (data != null) {
                        list.addAll(data.getData());
                        binding.setCount(list.size());
                        appointHistoryListAdapter.notifyDataSetChanged();
                        binding.top.setText("共预约了" + list.size() + "套房源");
                        if (list.size() == 0) {
                            showEmptyLayout();
                        }
                    }


                }
        );
    }

    public void showEmptyLayout() {
        if(!binding.emptyLayout.isInflated()){
            View emptyView = binding.emptyLayout.getViewStub().inflate();
            EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
            emptyLayoutBinding.setTip("您还没有历史预约");
        }

    }

    @Override
    public void initView() {
        binding.setTitle("我的历史预约");
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            int userType = userBean.getUserType();
            binding.setUserType(userType);
            list = new ArrayList<>();
            appointHistoryListAdapter = new AppointHistoryListAdapter(getContext(), userType, list, R.layout.list_history_appoint_item, new ClickSureListener() {
                @Override
                public void click(int position) {
                    new TipDialog(getContext(), new ClickSureListener() {
                        @Override
                        public void clickSure() {
                            ArrayList<Integer> ids = new ArrayList<>();
                            ids.add(list.get(position).getId());
                            HttpUtil.getInstance().deleteAppoint(ids).subscribe(
                                    str -> {
                                        list.remove(position);
                                        appointHistoryListAdapter.clearView();
                                        appointHistoryListAdapter.notifyDataSetChanged();
                                        binding.setCount(list.size());
                                        binding.top.setText("共预约了" + list.size() + "套房源");
                                    }
                            );
                        }
                    }, "你确定要删除预约?").show();
                }

                @Override
                public void select() {
                    if (binding.selectAll.isSelected()) {
                        binding.selectAll.setSelected(false);
                    }

                }
            });
            binding.lvData.setAdapter(appointHistoryListAdapter);
        }

    }

    @Override
    public void initlisten() {

        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.selectAll.isSelected();
                binding.selectAll.setSelected(!selected);
                if (appointHistoryListAdapter != null) {
                    appointHistoryListAdapter.selectAll(!selected);
                }
            }
        });

        binding.lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAppointDetail myAppointDetail = new MyAppointDetail();
                AppointBean bean = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointBean", bean);
                myAppointDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(myAppointDetail, "appointDetail");
            }
        });

        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "没有预约可删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<AppointBean> selectList = appointHistoryListAdapter.getSelectList();
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选预约", Toast.LENGTH_SHORT).show();
                    return;
                }
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        for (AppointBean bean : selectList) {
                            ids.add(bean.getId());
                        }
                        HttpUtil.getInstance().deleteAppoint(ids).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "批量删除成功", Toast.LENGTH_SHORT).show();
                                    list.removeAll(selectList);
                                    binding.selectAll.setSelected(false);
                                    appointHistoryListAdapter.clearSelectList();
                                    appointHistoryListAdapter.notifyDataSetChanged();
                                    binding.setCount(list.size());
                                }
                        );
                    }
                }, "您确定要删除勾选的历史预约").show();
            }
        });
    }
}
