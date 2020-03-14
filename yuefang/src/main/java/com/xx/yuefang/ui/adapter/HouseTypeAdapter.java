package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.HousetypeListItemBinding;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class HouseTypeAdapter extends BaseListAdapter {
    public HouseTypeAdapter(Context context, List<PremisesDetail.DataBean.HouseTypeInfosBean> datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        HousetypeListItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        PremisesDetail.DataBean.HouseTypeInfosBean houseTypeInfosBean = (PremisesDetail.DataBean.HouseTypeInfosBean) datas.get(position);
        binding.setData(houseTypeInfosBean);
        String picture = houseTypeInfosBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).thumbnail(0.5f).centerCrop().into(binding.image);
        }
        String characteristic = houseTypeInfosBean.getCharacteristic();
        if(!TextUtils.isEmpty(characteristic)){
            String[] characteristics = characteristic.split(",");
            for (int i = 0; i < characteristics.length; i++) {
                TextView item;
                item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                item.setText(characteristics[i]);
                if (i < 3) {
                    binding.llexpend1.addView(item);
                } else {
                    binding.llexpend2.addView(item);
                }
            }
        }

        return binding.getRoot();
    }
}
