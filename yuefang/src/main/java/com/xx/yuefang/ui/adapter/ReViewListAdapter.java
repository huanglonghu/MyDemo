package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ReViewResponse;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListReviewItemBinding;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class ReViewListAdapter extends BaseListAdapter {

    public ReViewListAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListReviewItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_review_item, parent, false);
        ReViewResponse.DataBeanX.DataBean dataBean = (ReViewResponse.DataBeanX.DataBean) datas.get(position);
        binding.setData(dataBean);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(binding.head);
        return binding.getRoot();
    }

}
