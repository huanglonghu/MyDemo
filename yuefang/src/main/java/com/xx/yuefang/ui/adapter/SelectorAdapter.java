package com.xx.yuefang.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.xx.yuefang.bean.ISelectImageItem;
import com.xx.yuefang.bean.Img;
import com.xx.yuefang.R;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectorAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<ISelectImageItem> mData;
    private OnItemClickListener mListener;
    private RecyclerView rv;
    private int selectPosition = -1;

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
    }

    public SelectorAdapter(@NonNull OnItemClickListener listener, RecyclerView rv) {
        this.rv = rv;
        mData = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        return new RecyclerViewHolder(v);
    }

    private static final int[] COLORS = new int[]{
            R.color.image_selector_red,
            R.color.image_selector_orange,
            R.color.image_selector_yellow
    };

    private int getColor(int position) {
        int pos = position % COLORS.length;
        return COLORS[pos];
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        ISelectImageItem item = getItem(pos);
        if (item == null)
            return;
        Glide.with(holder.ivImage.getContext()).load(item.getImgPath()).placeholder(getColor(pos)).centerCrop().into(holder.ivImage);
        LogUtil.log("=============ZZZZZZZZZZZZZZZ============="+item.getImgPath());
        holder.mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(mData.get(pos), pos);
            }
        });
        if (selectPosition != position) {
            holder.ivCheck.setSelected(false);
            holder.vShadown.setVisibility(View.GONE);
        } else {
            holder.ivCheck.setSelected(true);
            holder.vShadown.setVisibility(View.VISIBLE);
        }
        holder.ivCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition != -1) {
                    if (selectPosition == position) {
                        holder.ivCheck.setSelected(false);
                        holder.vShadown.setVisibility(View.GONE);
                        selectPosition = -1;
                    } else {
                        RecyclerViewHolder vh = (RecyclerViewHolder) rv.findViewHolderForLayoutPosition(selectPosition);
                        if (vh != null) {//还在屏幕里
                            vh.ivCheck.setSelected(false);
                            vh.vShadown.setVisibility(View.GONE);
                        }
                        holder.ivCheck.setSelected(true);
                        holder.vShadown.setVisibility(View.VISIBLE);
                        selectPosition = position;
                    }
                } else {
                    holder.ivCheck.setSelected(true);
                    holder.vShadown.setVisibility(View.VISIBLE);
                    selectPosition = position;
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private ISelectImageItem getItem(int position) {
        if (position >= mData.size())
            return null;

        return mData.get(position);
    }

    public void replaceDatas(List<Img> data) {
        mData.clear();

        if (data != null && !data.isEmpty()) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        int onItemClick(ISelectImageItem item, int pos);
    }

    public String[] getPathByPosArray() {

        if (mData == null || mData.isEmpty())
            return null;
        String[] paths = new String[mData.size()];
        for (int i = 0; i < mData.size(); i++) {
            String imgPath = mData.get(i).getImgPath();
            paths[i] = imgPath;
        }
        return paths;
    }

    public String getPathByPosition(int position) {
        ISelectImageItem item = getItem(position);
        String path = null;
        if (item != null) {
            path = item.getImgPath();
        }
        return path;
    }

}
