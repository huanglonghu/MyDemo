package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ItemListHomeBinding;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class HomeAdapter extends BaseListAdapter {

    public HomeAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ItemListHomeBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        PremisesBean dataBean = (PremisesBean) datas.get(position);
        if (dataBean != null) {
            binding.setDataBean(dataBean);
            String constructionArea = dataBean.getConstructionArea();
            String totalPrice = dataBean.getTotalPrice();
            if (constructionArea.equals("待定") || constructionArea.equals("暂无")) {
                binding.area.setText(constructionArea);
            } else {
                binding.area.setText(constructionArea);
            }
            if (totalPrice.equals("待定") || totalPrice.equals("暂无")) {
                binding.housePrice.setText(totalPrice);
            } else {
                binding.housePrice.setText(totalPrice + "万元/套");
            }

            String picture = dataBean.getPicture();
            String url = ImagUtil.handleUrl(picture);
            if (!TextUtils.isEmpty(url)) {
                Glide.with(context).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(resource);
                        binding.img.setBackground(bitmapDrawable);
                        binding.imgLoading.setVisibility(View.GONE);
                    }
                });
            }
            List<String> characteristics = dataBean.getCharacteristics();
            TextView tv = (TextView) View.inflate(context, R.layout.layout_character1, null);
            tv.setText(dataBean.getState());
            binding.llexpend.addView(tv);
            for (int i = 0; i < characteristics.size(); i++) {
                if (i < 4) {
                    TextView item;
                    item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                    item.setText(characteristics.get(i));
                    binding.llexpend.addView(item);
                } else {
                    break;
                }
            }
        }
        return binding.getRoot();
    }


}
