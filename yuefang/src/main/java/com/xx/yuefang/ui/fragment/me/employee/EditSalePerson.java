package com.xx.yuefang.ui.fragment.me.employee;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.EmployeeBean;
import com.xx.yuefang.bean.PremisesMsg;
import com.xx.yuefang.bean.UpdateSaler;
import com.xx.yuefang.databinding.FragmentFzsEditSalepersonBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.AdClickListener;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.PremisesMsgDialog;
import com.xx.yuefang.ui.widget.TypeSelect;
import com.xx.yuefang.util.GsonUtil;

import java.util.List;

public class EditSalePerson extends BaseFragment {
    private FragmentFzsEditSalepersonBinding binding;
    private List<PremisesMsg.DataBean> data;
    private UpdateSaler updateSaler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fzs_edit_saleperson, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        updateSaler = new UpdateSaler();
        EmployeeBean.DataBeanX.DataBean dataBean = (EmployeeBean.DataBeanX.DataBean) bundle.getSerializable("bean");
        updateSaler.setAddress(dataBean.getAddress());
        updateSaler.setBusinessCardName(dataBean.getBusinessCardName());
        updateSaler.setTelephone(dataBean.getTelephone());
        updateSaler.setEmail(dataBean.getEmail());
        updateSaler.setId(dataBean.getId());
        updateSaler.setPhoneNumber(dataBean.getPhoneNumber());
        updateSaler.setAvatar(dataBean.getAvatar());
        updateSaler.setSex(dataBean.isSex());
        updateSaler.setProfile(dataBean.getProfile());
        updateSaler.setPremisesBaseId(dataBean.getPremisesBaseId());
        binding.setUpdateSaler(updateSaler);
        if (data == null) {
            HttpUtil.getInstance().getPremiseMsgByDdeveloperId().subscribe(
                    str -> {
                        PremisesMsg premisesMsg = GsonUtil.fromJson(str, PremisesMsg.class);
                        data = premisesMsg.getData();
                        if (data != null && data.size() > 0) {
                            for (int i = 0; i < data.size(); i++) {
                                PremisesMsg.DataBean dataBean1 = data.get(i);
                                if (dataBean1.getId() == updateSaler.getPremisesBaseId()) {
                                    binding.premisesName.setText(dataBean1.getPremisesName());
                                    break;
                                }
                            }
                        }
                    }
            );
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

        binding.sexLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] sexArray = {"男", "女"};
                TypeSelect typeSelect = new TypeSelect(getContext(), sexArray, new AdClickListener() {
                    @Override
                    public void click(int position) {
                        String sex = sexArray[position];
                        updateSaler.setSex(sex.equals("男"));
                        binding.tvSex.setText(sex);

                    }
                });
                typeSelect.show();
            }
        });

        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(updateSaler.getBusinessCardName())) {
                    Toast.makeText(getContext(), "请输入驻场名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 名字、性别、电话
                if (TextUtils.isEmpty(updateSaler.getPhoneNumber())) {
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpUtil.getInstance().updateSaler(updateSaler).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "编辑成功", Toast.LENGTH_SHORT).show();
                            Presenter.getInstance().back();
                        }
                );


            }
        });


        binding.premises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PremisesMsgDialog premisesMsgDialog = new PremisesMsgDialog(getContext(), data, new ClickSureListener() {
                    @Override
                    public void click(int position) {
                        PremisesMsg.DataBean selectPremisesData = data.get(position);
                        binding.premisesName.setText(selectPremisesData.getPremisesName());
                        updateSaler.setPremisesBaseId(selectPremisesData.getId());
                    }
                });
                premisesMsgDialog.show();
            }
        });

    }
}
