package com.xx.yuefang.ui.fragment.me.developer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointList;
import com.xx.yuefang.bean.GetAllApoint;
import com.xx.yuefang.databinding.EmptyLayout2Binding;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.FragmentFzsSellerRecordBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.SellRecordAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.me.MyAppointDetail;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SellRecord extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private FragmentFzsSellerRecordBinding binding;
    private SellRecordAdapter sellRecordAdapter;
    private List<AppointBean> list;
    private GetAllApoint getAllApoint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fzs_seller_record, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.listSellRecord.setRefreshData(this);
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        List<Integer> states = new ArrayList<>();
        getAllApoint = new GetAllApoint();
        getAllApoint.setLimit(10);
        getAllApoint.setPage(1);
        states.add(7);
        states.add(8);
        getAllApoint.setStates(states);
        getAppointList(getAllApoint);
    }

    private void getAppointList(GetAllApoint getAllApoint) {
        // 1待接单 3已接单 6已完成 2用户取消 4销售取消 用户爽约
        HttpUtil.getInstance().getAllApoint(getAllApoint).subscribe(
                str -> {
                    AppointList appointList = GsonUtil.fromJson(str, AppointList.class);
                    AppointList.DataBeanX data = appointList.getData();
                    if (data != null) {
                        List<AppointBean> data1 = data.getData();
                        if (data1 != null && data1.size() > 0) {
                            list.addAll(data1);
                            binding.setCount(list.size());
                            sellRecordAdapter.notifyDataSetChanged();
                            binding.listSellRecord.setLoading(true);
                        } else {
                            binding.listSellRecord.setLoading(false);
                            if (getAllApoint.getPage() == 1) {
                                list.clear();
                                sellRecordAdapter.clearView();
                            }
                        }
                    }
                    showEmptyLayout();
                }
        );
    }

    private View emptyView;
    public void showEmptyLayout() {
        if (list.size() ==0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("暂无卖房成交记录");
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
        sellRecordAdapter = new SellRecordAdapter(getContext(), list, R.layout.list_sellrecord_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(list.get(position).getId());
                        HttpUtil.getInstance().deleteAppoint(ids).subscribe(
                                str -> {
                                    Toast.makeText(getContext(), "删除记录成功", Toast.LENGTH_SHORT).show();
                                    list.remove(position);
                                    sellRecordAdapter.clearView();
                                    sellRecordAdapter.notifyDataSetChanged();
                                    binding.setCount(list.size());
                                }
                        );
                    }
                }, "您确定要删除这条记录").show();
            }

            @Override
            public void select() {
                if (binding.getIsSelectAll()) {
                    binding.setIsSelectAll(false);
                }
            }
        });
        binding.listSellRecord.setAdapter(sellRecordAdapter);
    }

    @Override
    public void initlisten() {
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelectAll = binding.getIsSelectAll();
                binding.setIsSelectAll(!isSelectAll);
                if (sellRecordAdapter != null) {
                    sellRecordAdapter.selectAll(!isSelectAll);
                }
            }
        });

        binding.listSellRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAppointDetail myAppointDetailFragment = new MyAppointDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointBean", list.get(position));
                myAppointDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(myAppointDetailFragment, "appointDetail");
            }
        });

        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "没有记录可删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<AppointBean> selectList = sellRecordAdapter.getSelectList();
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选记录", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getContext(), "批量删除记录成功", Toast.LENGTH_SHORT).show();
                                    list.removeAll(selectList);
                                    binding.setIsSelectAll(false);
                                    sellRecordAdapter.clearSelectList();
                                    sellRecordAdapter.notifyDataSetChanged();
                                    binding.setCount(list.size());
                                }
                        );
                    }
                }, "您确定要删除勾选的记录").show();
            }
        });


    }

    @Override
    public void refreshData(int page) {
        getAllApoint.setPage(page);
        getAppointList(getAllApoint);
    }
}
