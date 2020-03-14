package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.search.core.PoiInfo;
import com.xx.yuefang.databinding.ListLineItemBinding;

import java.util.List;

public class MapLineAdapter extends BaseListAdapter {
    public MapLineAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        PoiInfo poiInfo = (PoiInfo) datas.get(position);
        ListLineItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        binding.setPoi(poiInfo);
        return binding.getRoot();
    }
}
