package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.ui.customview.ImageTextView;

import java.util.List;

public class CancleReasonAdapter extends BaseListAdapter {
    public CancleReasonAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ImageTextView it = (ImageTextView) layoutInflater.inflate(R.layout.list_cancle_reason_item, parent, false);
        it.setText((String) datas.get(position));
        it.setDrawable(context.getDrawable(R.drawable.rb1));
        return it;
    }

    private int selectPosition = -1;

    public void refreshSelectPosition(int position) {
        if (selectPosition != -1) {
            if (selectPosition != position) {
                ImageTextView it = (ImageTextView) getView(selectPosition, null, null);
                it.setDrawable(context.getDrawable(R.drawable.rb1));
                ImageTextView it2 = (ImageTextView) getView(position, null, null);
                it2.setDrawable(context.getDrawable(R.drawable.rb2));
                selectPosition = position;
            }
        }else {
            ImageTextView it2 = (ImageTextView) getView(position, null, null);
            it2.setDrawable(context.getDrawable(R.drawable.rb2));
            selectPosition = position;
        }
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }
}
