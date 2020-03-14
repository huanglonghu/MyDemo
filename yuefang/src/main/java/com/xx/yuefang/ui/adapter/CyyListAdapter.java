package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.yuefang.R;

import java.util.List;

public class CyyListAdapter extends BaseListAdapter {

    public CyyListAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {

        TextView tv = (TextView) layoutInflater.inflate(R.layout.list_cyy_item, parent, false);
        String content = (String) datas.get(position);
        tv.setText(content);
        return tv;
    }
}
