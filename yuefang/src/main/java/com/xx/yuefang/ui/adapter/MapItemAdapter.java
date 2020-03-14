package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.MapItemBean;
import com.xx.yuefang.databinding.MapRvItemBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.MapUtil;

import java.util.ArrayList;

public class MapItemAdapter extends RecyclerView.Adapter<MapItemAdapter.VrItemViewHolder> {
    private Context context;
    private ClickSureListener clickSureListener;
    private RecyclerView mRv;
    private ArrayList<MapItemBean> datas;
    private LayoutInflater inflater;


    public MapItemAdapter(Context context, ArrayList<MapItemBean> datas, ClickSureListener clickSureListener, RecyclerView recyclerView) {
        this.context = context;
        this.datas = datas;
        this.clickSureListener = clickSureListener;
        this.mRv = recyclerView;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VrItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MapRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.map_rv_item, viewGroup, false);
        VrItemViewHolder vrItemViewHolder = new VrItemViewHolder(binding.getRoot());
        return vrItemViewHolder;
    }

    private int mSelectedPos;

    @Override
    public void onBindViewHolder(@NonNull VrItemViewHolder viewHolder, int i) {
        viewHolder.setPosition(i);
        MapItemBean mapItemBean = datas.get(i);
        if (i == 0) {
            viewHolder.img.setSelected(true);
            viewHolder.tv.setTextColor(Color.parseColor("#018bee"));
        }
        viewHolder.tv.setText(mapItemBean.getName());
        viewHolder.img.setBackgroundResource(mapItemBean.getImg());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class VrItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private int position;
        private TextView tv;

        public void setPosition(int position) {
            this.position = position;
        }

        public VrItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.rv_iv);
            this.tv = itemView.findViewById(R.id.rv_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickSureListener.click(position);
                    handlerClickItem();
                }
            });

        }

        public void handlerClickItem() {
            VrItemViewHolder vh = (VrItemViewHolder) mRv.findViewHolderForLayoutPosition(mSelectedPos);
            if (vh != null) {//还在屏幕里
                vh.img.setSelected(false);
                vh.tv.setTextColor(Color.parseColor("#8a8a8a"));
            } else {
                //add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                notifyItemChanged(mSelectedPos);
            }
            datas.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
            //设置新Item的勾选状态
            mSelectedPos = position;
            datas.get(mSelectedPos).setSelected(true);
            img.setSelected(true);
            tv.setTextColor(Color.parseColor("#018bee"));
        }
    }
}