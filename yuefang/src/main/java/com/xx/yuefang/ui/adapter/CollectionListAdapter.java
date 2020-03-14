package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.bean.Collection;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListMyfootprintItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class CollectionListAdapter extends BaseList2Adapter {
    private ClickSureListener clickSureListener;

    public CollectionListAdapter(Context context, List datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent, View convertView) {
        ListMyfootprintItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), res, parent, false);
        convertView = binding.getRoot();
        Collection.DataBeanX.DataBean bean = (Collection.DataBeanX.DataBean) datas.get(position);
        initAppointItem(binding, bean);
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSureListener.click(position);
            }
        });
        binding.cb.setSelected(selectMap.get(position)!=null);
        binding.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = v.isSelected();
                v.setSelected(!selected);
                if (!selected) {
                    selectMap.put(position, !selected);
                } else {
                    clickSureListener.select();
                    selectMap.remove(position);
                }
            }
        });
        binding.yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",bean.getPremisesBaseId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                bundle.putBoolean("isAret",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });
        binding.setPresenter(Presenter.getInstance());
        return convertView;
    }


    private void initAppointItem(ListMyfootprintItemBinding binding, Collection.DataBeanX.DataBean bean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(bean.getAddress());
        appointItemView.setAveragePrice(bean.getAveragePrice() + "");
        appointItemView.setConstructionArea(bean.getConstructionArea());
        appointItemView.setPremisesName(bean.getPremisesName());
        appointItemView.setPropertyType(bean.getPropertyType());
        appointItemView.setRegion(bean.getRegion());
        appointItemView.setId(bean.getPremisesBaseId());
        binding.setBean(appointItemView);
        String picture = bean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = bean.getCharacteristics();
        if (binding.appointItem.llexpend.getChildCount() == 0) {
            for (int i = 0; i < characteristics.size() + 1; i++) {
                if (i < 3) {
                    TextView item;
                    if (i == 0) {
                        item = (TextView) View.inflate(context, R.layout.layout_character1, null);
                        item.setText(bean.getState());
                    } else {
                        item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                        item.setText(characteristics.get(i - 1));
                    }
                    binding.appointItem.llexpend.addView(item, i);
                }
            }
        }

    }


}
