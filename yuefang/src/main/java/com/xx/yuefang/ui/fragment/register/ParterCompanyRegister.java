package com.xx.yuefang.ui.fragment.register;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.RegisterParterCompanyBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.MyViewPagerAdapter;
import com.xx.yuefang.ui.base.BaseFragment;

import java.util.ArrayList;

public class ParterCompanyRegister extends BaseFragment {

    private RegisterParterCompanyBinding binding;
    private ArrayList<BaseFragment> fragments;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.register_parter_company, container, false);
            binding.setPresenter(Presenter.getInstance());
            initView();
            initlisten();
        }
        return binding.getRoot();
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        ParterRegister parterRegister = new ParterRegister();
        CompanyRegister companyRegister = new CompanyRegister();
        fragments.add(parterRegister);
        fragments.add(companyRegister);
        getChildFragmentManager().beginTransaction().replace(R.id.register_container, fragments.get(0)).commit();
    }

    @Override
    public void initlisten() {
        binding.parter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.register_container, fragments.get(0)).commit();
                binding.setSelectPosition(0);
            }
        });
        binding.company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getChildFragmentManager().beginTransaction().replace(R.id.register_container, fragments.get(1)).commit();
                binding.setSelectPosition(1);
            }
        });
    }
}
