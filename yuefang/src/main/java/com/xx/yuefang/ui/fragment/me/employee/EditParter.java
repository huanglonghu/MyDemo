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
import com.xx.yuefang.bean.UpdateParter;
import com.xx.yuefang.databinding.FragmentEditParterBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.AdClickListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.TypeSelect;

public class EditParter extends BaseFragment {
    private FragmentEditParterBinding binding;
    private UpdateParter updateParterBody;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_parter, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        EmployeeBean.DataBeanX.DataBean dataBean = (EmployeeBean.DataBeanX.DataBean) bundle.getSerializable("bean");
        updateParterBody = new UpdateParter();
        updateParterBody.setId(dataBean.getId());
        updateParterBody.setPhoneNumber(dataBean.getPhoneNumber());
        updateParterBody.setAvatar(dataBean.getAvatar());
        updateParterBody.setSex(dataBean.isSex());
        updateParterBody.setTelephone(dataBean.getTelephone());
        updateParterBody.setUserName(dataBean.getUserName());
        updateParterBody.setBusinessCardName(dataBean.getBusinessCardName());
        updateParterBody.setEmail(dataBean.getEmail());
        updateParterBody.setProfile(dataBean.getProfile());
        updateParterBody.setAddress(dataBean.getAddress());
        binding.setUpdateParter(updateParterBody);
    }

    @Override
    public void initView() {}

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
                        updateParterBody.setSex(sex.equals("男"));
                        binding.tvSex.setText(sex);

                    }
                });
                typeSelect.show();
            }
        });

        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 名字、性别、电话
                if (TextUtils.isEmpty(updateParterBody.getUserName())) {
                    Toast.makeText(getContext(), "请输入驻场名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(updateParterBody.getPhoneNumber())) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().updateParter(updateParterBody).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "编辑成功", Toast.LENGTH_SHORT).show();
                            Presenter.getInstance().back();
                        }
                );
            }
        });

    }
}
