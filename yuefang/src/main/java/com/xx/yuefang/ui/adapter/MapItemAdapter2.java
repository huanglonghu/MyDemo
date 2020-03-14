package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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

import java.util.ArrayList;

public class MapItemAdapter2 extends RecyclerView.Adapter<MapItemAdapter2.VrItemViewHolder> {
    private ClickSureListener clickSureListener;
    private RecyclerView mRv;
    private ArrayList<MapItemBean> datas;
    private LayoutInflater inflater;


    public MapItemAdapter2(Context context, ArrayList<MapItemBean> datas, ClickSureListener clickSureListener, RecyclerView recyclerView) {
        this.datas = datas;
        this.clickSureListener = clickSureListener;
        this.mRv = recyclerView;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public VrItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_rv_map, viewGroup, false);
        VrItemViewHolder vrItemViewHolder = new VrItemViewHolder(view);
        return vrItemViewHolder;
    }
    private int mSelectedPos;

    @Override
    public void onBindViewHolder(@NonNull VrItemViewHolder viewHolder, int i) {
        viewHolder.setPosition(i);
        MapItemBean mapItemBean = datas.get(i);
        if (i == mSelectedPos) {
            viewHolder.tv.setTextColor(Color.parseColor("#018bee"));
        }
        viewHolder.tv.setText(mapItemBean.getName() + "(" + mapItemBean.getCount() + ")");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class VrItemViewHolder extends RecyclerView.ViewHolder {

        private int position;
        private TextView tv;
        public void setPosition(int position) {
            this.position = position;
        }
        public VrItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv = itemView.findViewById(R.id.title);
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
            tv.setTextColor(Color.parseColor("#018bee"));
        }
    }
}