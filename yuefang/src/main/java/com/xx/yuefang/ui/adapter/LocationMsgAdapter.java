package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.search.core.PoiInfo;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.ListLocationmsgItemBinding;
import java.util.List;

public class LocationMsgAdapter extends BaseListAdapter {
    public LocationMsgAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        PoiInfo poiInfo = (PoiInfo) datas.get(position);
        ListLocationmsgItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_locationmsg_item, parent, false);
        binding.setPoi(poiInfo);
        if (selectPosition == position) {
            binding.rb.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }


    private int selectPosition;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public void refreshSelectPosition(int position) {
        if (selectPosition != position) {
            View v2 = getView(selectPosition, null, null);
            ListLocationmsgItemBinding binding2 = DataBindingUtil.findBinding(v2);
            binding2.rb.setVisibility(View.GONE);
            View v1 = getView(position, null, null);
            ListLocationmsgItemBinding binding = DataBindingUtil.findBinding(v1);
            binding.rb.setVisibility(View.VISIBLE);
            selectPosition = position;
        }

    }
}
