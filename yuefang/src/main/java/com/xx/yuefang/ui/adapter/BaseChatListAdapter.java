package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baidu.mapapi.map.MapView;
import com.xx.yuefang.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.jpush.im.android.api.enums.ContentType;

public abstract class BaseChatListAdapter<T> extends BaseAdapter {

    public List<T> datas;
    public Context context;
    private HashMap<Integer, View> viewMap = new HashMap<>();
    private LayoutInflater layoutInflater;
    public HashMap<Integer, ContentType> typeMap = new HashMap<>();
    private int res;
    public ArrayList<Object> selectList = new ArrayList<>();

    public BaseChatListAdapter(Context context, List<T> datas, int res) {
        this.datas = datas;
        this.context = context;
        this.res = res;
        layoutInflater = LayoutInflater.from(context);
    }

    public HashMap<Integer, View> getViewMap() {
        return viewMap;
    }

    public void setViewMap(HashMap<Integer, View> viewMap) {
        this.viewMap = viewMap;
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
        if (viewMap.get(position) == null) {
            View view = initView(layoutInflater, res, datas, position, parent);
            viewMap.put(position, view);
        }
        return viewMap.get(position);
    }

    protected abstract View initView(LayoutInflater layoutInflater, int res, List<T> datas, int position, ViewGroup parent);




    public void clearView() {
        viewMap.clear();
    }


    public ArrayList<Object> getSelectList() {
        return selectList;
    }



}
