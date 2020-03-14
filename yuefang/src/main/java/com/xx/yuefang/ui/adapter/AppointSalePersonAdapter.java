package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.bean.SalePresonList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListAppointSalepersonItemBinding;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class AppointSalePersonAdapter extends BaseListAdapter {
    public AppointSalePersonAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListAppointSalepersonItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        SalePresonList.DataBean dataBean = (SalePresonList.DataBean) datas.get(position);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if(!TextUtils.isEmpty(url)){
            RxImageLoader.with(context).load(url).into(binding.photo,1);
        }
        binding.setData(dataBean);
        boolean isThePremises = dataBean.isIsThePremises();
        binding.cb.setSelected(isThePremises);
        if(isThePremises){
            selectList.add(dataBean);
        }
        binding.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.cb.isSelected();
                binding.cb.setSelected(!selected);
                if (!selected) {
                    if (!selectList.contains(dataBean)) {
                        selectList.add(dataBean);
                    }
                } else {
                    if (selectList.contains(dataBean)) {
                        selectList.remove(dataBean);
                    }
                }

            }
        });
        View view = binding.getRoot();
        view.setTag(binding);
        return view;
    }
    @Override
    public void setSelect(View view, boolean select, int position) {
        ListAppointSalepersonItemBinding binding  = (ListAppointSalepersonItemBinding) view.getTag();
        binding.cb.setSelected(select);
        if (select) {
            selectList.add((SalePresonList.DataBean) datas.get(position));
        } else {
            selectList.remove(datas.get(position));
        }
    }

}
