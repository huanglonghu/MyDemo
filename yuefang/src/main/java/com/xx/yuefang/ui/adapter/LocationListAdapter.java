package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baidu.mapapi.search.core.PoiInfo;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LocationListItemBinding;

import java.util.List;

public class LocationListAdapter extends BaseAdapter {
    private List<PoiInfo> poiInfos;
    private Context context;

    public LocationListAdapter(Context context, List<PoiInfo> poiInfos) {
        this.context = context;
        this.poiInfos = poiInfos;
    }

    @Override
    public int getCount() {
        return poiInfos == null ? 0 : poiInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LocationListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.location_list_item, parent, false);
        binding.setPoinfo(poiInfos.get(position));
        convertView = binding.getRoot();
        return convertView;
    }
}
