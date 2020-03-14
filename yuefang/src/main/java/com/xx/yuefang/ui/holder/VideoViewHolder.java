package com.xx.yuefang.ui.holder;

import android.support.v7.widget.RecyclerView;

import com.xx.yuefang.databinding.VideoItemBinding;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    private VideoItemBinding binding;

    public VideoViewHolder(VideoItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public VideoItemBinding getBinding() {
        return binding;
    }

}
