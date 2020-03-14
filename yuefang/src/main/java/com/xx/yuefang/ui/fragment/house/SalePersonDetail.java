package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AgentBean;
import com.xx.yuefang.bean.Salesperson;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.FragmentSalepersonBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class SalePersonDetail extends BaseFragment {

    private FragmentSalepersonBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saleperson, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        return binding.getRoot();
    }


    @Override
    public void initData() {

        AgentBean.DataBean agentBean = (AgentBean.DataBean) getArguments().getSerializable("agent");
        String avatar = agentBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable circle = ImagUtil.circle(imageBean.getBitmap());
                        binding.ivHead.setBackground(circle);
                    }
            );
        }
        int leadSee = agentBean.getLeadSee();
        int turnover = agentBean.getTurnover();
        String str1 = "本盘带看" + leadSee + "次|";
        String str2 = "总成交" + turnover + "套";
        binding.dk.setText(str1);
        binding.cj.setText(str2);
        String gradeType = agentBean.getGradeType();
        switch (gradeType) {
            case "金牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_f);
                break;
            case "银牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_g);
                break;
            case "铜牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_e);
                break;
            case "无牌":
                binding.gradeType.setVisibility(View.INVISIBLE);
                break;
        }

        binding.setData(agentBean);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

    }
}
