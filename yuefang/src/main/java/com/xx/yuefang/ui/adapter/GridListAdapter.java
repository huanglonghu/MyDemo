package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.xx.yuefang.bean.SearchBean;

import java.util.List;

public class GridListAdapter extends BaseAdapter {

        private List<SearchBean> searchBeans;
        private Context context;
        private int res;

        public GridListAdapter(Context context, List<SearchBean> searchBeans, int res) {
            this.searchBeans = searchBeans;
            this.context = context;
            this.res = res;
        }

        @Override
        public int getCount() {
            return searchBeans == null ? 0 : searchBeans.size();
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
            CheckedTextView t = (CheckedTextView) LayoutInflater.from(context).inflate(res, parent, false);
            t.setText(searchBeans.get(position).getText());
            convertView = t;
            return convertView;
        }
    }