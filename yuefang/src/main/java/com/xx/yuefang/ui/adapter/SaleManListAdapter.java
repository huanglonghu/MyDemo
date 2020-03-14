package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.SalePresonList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.SalemanListItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class SaleManListAdapter extends BaseListAdapter {

    private ClickSureListener clickSureListener;

    public SaleManListAdapter(Context context, List datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        SalemanListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), res, parent, false);
        SalePresonList.DataBean dataBean = (SalePresonList.DataBean) datas.get(position);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).load(url).into(binding.photo, 1);
        }
        binding.setData(dataBean);
        View view = binding.getRoot();
        String gradeType = dataBean.getGradeType();
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

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataBean.getTelephone()));//跳转到拨号界面
                context.startActivity(intent);
            }
        });

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSureListener.click(position);
            }
        });


        return view;
    }
}
