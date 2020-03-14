package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.yuefang.R;

import java.util.List;

public class GridListAdapter2 extends BaseListAdapter {

    private int selectPosition;

    public GridListAdapter2(Context context, List datas, int res, int selectPosition) {
        super(context, datas, res);
        this.selectPosition = selectPosition;
    }


    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        TextView t = (TextView) LayoutInflater.from(context).inflate(res, parent, false);
        t.setText((String) datas.get(position));
        if (position == selectPosition) {
            t.setBackgroundResource(R.drawable.shape6);
            t.setTextColor(Color.WHITE);
        } else {
            t.setBackgroundResource(R.drawable.shape7);
        }
        return t;
    }

    public void refreshCheckPosition(int position) {
        if (selectPosition != position) {
            TextView t1 = (TextView) getView(selectPosition, null, null);
            t1.setBackgroundResource(R.drawable.shape7);
            t1.setTextColor(Color.BLACK);
            TextView t = (TextView) getView(position, null, null);
            t.setTextColor(Color.WHITE);
            t.setBackgroundResource(R.drawable.shape6);
            selectPosition = position;
        }

    }
}