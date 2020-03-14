package com.xx.yuefang.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.yuefang.R;


public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public View mConvertView;
    public ImageView ivCheck;
    public ImageView ivImage;
    public View vShadown;


    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mConvertView = itemView;
        ivCheck = mConvertView.findViewById(R.id.iv_check);
        ivImage = mConvertView.findViewById(R.id.iv_img);
        vShadown = mConvertView.findViewById(R.id.v_shadown);

    }


}
