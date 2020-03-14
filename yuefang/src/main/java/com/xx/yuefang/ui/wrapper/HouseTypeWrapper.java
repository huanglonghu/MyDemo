package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.databinding.LayoutHousetypeItemBinding;
import com.xx.yuefang.databinding.WrapperHousesTypeBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.HouseTypeDetailFragment;
import com.xx.yuefang.ui.fragment.house.HouseTypeFragment;
import com.xx.yuefang.util.ImagUtil;
import java.util.ArrayList;
import java.util.List;

public class HouseTypeWrapper extends Wrapper {
    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true)
            .error(R.mipmap.icon_image_error);

    private WrapperHousesTypeBinding binding;


    public HouseTypeWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_houses_type, this, false);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        binding.setData(data);
        if (!binding.getIsInit()) {
            binding.setIsInit(true);
            List<PremisesDetail.DataBean.HouseTypeInfosBean> houseTypeInfos = data.getHouseTypeInfos();
            binding.setCount(houseTypeInfos.size());
            for (int i = 0; i < houseTypeInfos.size(); i++) {
                PremisesDetail.DataBean.HouseTypeInfosBean houseTypeInfosBean = houseTypeInfos.get(i);
                if (i < 2) {
                    String picture = houseTypeInfosBean.getPicture();
                    LayoutHousetypeItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_housetype_item, binding.llContainer, false);
                    String propertyType = data.getPropertyType();
                    itemBinding.setPropertyType(propertyType);
                    itemBinding.setBean(houseTypeInfosBean);
                    String url = ImagUtil.handleUrl(picture);
                    if (!TextUtils.isEmpty(url)) {
                        Glide.with(context).asBitmap().load(url).apply(mPreOptions).diskCacheStrategy(DiskCacheStrategy.ALL).thumbnail(0.1f).into(itemBinding.img);
                    }
                    String characteristic = houseTypeInfosBean.getCharacteristic();
                    if (!TextUtils.isEmpty(characteristic)) {
                        String[] characteristics = characteristic.split(",");
                        for (int j = 0; j < characteristics.length; j++) {
                            TextView item;
                            item = (TextView) View.inflate(context, R.layout.layout_character4, null);
                            item.setText(characteristics[j]);
                            if (j < 3) {
                                itemBinding.llexpend1.addView(item);
                            } else {
                                itemBinding.llexpend2.addView(item);
                            }
                        }
                    }
                    View itemView = itemBinding.getRoot();
                    ArrayList<String> houseTypes = (ArrayList<String>) data.getHouseTypes();
                    itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HouseTypeDetailFragment houseTypeDetailFragment = new HouseTypeDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("houseTypeInfo", houseTypeInfosBean);
                            bundle.putStringArrayList("houseTypes", houseTypes);
                            bundle.putSerializable("data", data);
                            bundle.putBoolean("isFromList", false);
                            bundle.putInt("selectPosition", 0);
                            houseTypeDetailFragment.setArguments(bundle);
                            Presenter.getInstance().step2fragment(houseTypeDetailFragment, "houseTypeDetail");
                        }
                    });
                    binding.llContainer.addView(itemView);
                }

            }
            binding.setData(data);
            binding.allType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HouseTypeFragment houseTypeFragment = new HouseTypeFragment();
                    houseTypeFragment.setPremisesData(data);
                    ArrayList<String> houseTypes = (ArrayList<String>) data.getHouseTypes();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("houseTypes", houseTypes);
                    bundle.putInt("selectPosition", 0);
                    houseTypeFragment.setArguments(bundle);
                    Presenter.getInstance().step2fragment(houseTypeFragment, "houseType");
                }
            });
        }
    }
}
