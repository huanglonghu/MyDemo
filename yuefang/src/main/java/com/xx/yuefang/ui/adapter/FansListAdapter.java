package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.bean.FansList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListMyfansItemBinding;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class FansListAdapter extends BaseListAdapter {


    public FansListAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {

        ListMyfansItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        FansList.DataBeanX.DataBean dataBean = (FansList.DataBeanX.DataBean) datas.get(position);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        Drawable circle = ImagUtil.circle(bitmap);
                        binding.ivHead.setImageDrawable(circle);
                    }
            );
        }
        binding.setDataBean(dataBean);
        View view = binding.getRoot();
        return view;
    }
}
