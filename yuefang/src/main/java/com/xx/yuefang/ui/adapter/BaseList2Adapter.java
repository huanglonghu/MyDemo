package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BaseList2Adapter<T> extends BaseAdapter {

    public List<T> datas;
    public Context context;
    private LayoutInflater layoutInflater;
    private int res;
    public HashMap<Integer, Boolean> selectMap = new HashMap<>();

    public HashMap<Integer, Boolean> getSelectMap() {
        return selectMap;
    }


    public BaseList2Adapter(Context context, List<T> datas, int res) {
        this.datas = datas;
        this.context = context;
        this.res = res;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = initView(layoutInflater, res, datas, position, parent, convertView);
        return view;
    }

    protected abstract View initView(LayoutInflater layoutInflater, int res, List<T> datas, int position, ViewGroup parent, View convertView);


    public void clearSelectMap() {
        selectMap.clear();
        notifyDataSetChanged();
    }

    public void selectAll(boolean select) {
        if (select) {
            for (int i = 0; i < datas.size(); i++) {
                selectMap.put(i, select);
            }
        } else {
            selectMap.clear();
        }
        notifyDataSetChanged();
    }

    public void  removeByPosition(int position){
        selectMap.remove(position);
    }

}
