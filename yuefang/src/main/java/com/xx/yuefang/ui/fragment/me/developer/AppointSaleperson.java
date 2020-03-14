package com.xx.yuefang.ui.fragment.me.developer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointSalepersonBean;
import com.xx.yuefang.bean.SalePresonList;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentAppointSalepresonBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.AppointSalePersonAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class AppointSaleperson extends BaseFragment {

    private FragmentAppointSalepresonBinding binding;
    private List<SalePresonList.DataBean> data;
    private AppointSalePersonAdapter salePersonAdapter;
    private int premisesId;
    private int developerId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appoint_salepreson, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        premisesId = getArguments().getInt("developerId");
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean != null) {
            developerId = userBean.getDeveloperId();
            HttpUtil.getInstance().getAllSalePerson(developerId, premisesId).subscribe(
                    str -> {
                        SalePresonList salePresonList = GsonUtil.fromJson(str, SalePresonList.class);
                        data.clear();
                        data.addAll(salePresonList.getData());
                        salePersonAdapter.notifyDataSetChanged();
                    }
            );
        }
    }

    @Override
    public void initView() {
        data = new ArrayList<>();
        salePersonAdapter = new AppointSalePersonAdapter(getContext(), data, R.layout.list_appoint_saleperson_item);
        binding.listSalePerson.setAdapter(salePersonAdapter);
    }

    @Override
    public void initlisten() {
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.selectAll.isSelected();
                binding.selectAll.setSelected(!selected);
                salePersonAdapter.selectAll(!selected);
            }
        });

        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.size() == 0) {
                    Toast.makeText(getContext(), "没有销售员可操作", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<SalePresonList.DataBean> selectList = salePersonAdapter.getSelectList();
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选销售员", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Integer> ids = new ArrayList<>();
                for (SalePresonList.DataBean bean : selectList) {
                    ids.add(bean.getId());
                }
                AppointSalepersonBean appointSalepersonBean = new AppointSalepersonBean();
                appointSalepersonBean.setPremisesBaseId(premisesId);
                appointSalepersonBean.setSalespersonIds(ids);
                HttpUtil.getInstance().appointSalePerson(appointSalepersonBean).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "指定成功", Toast.LENGTH_SHORT).show();
                            Presenter.getInstance().back();
                        }
                );


            }
        });
    }
}
