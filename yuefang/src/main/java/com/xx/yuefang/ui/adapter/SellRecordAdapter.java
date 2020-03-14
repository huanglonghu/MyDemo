package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListSellrecordItemBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class SellRecordAdapter extends BaseListAdapter {

    private ClickSureListener clickSureListener;

    public SellRecordAdapter(Context context, List<AppointBean> datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListSellrecordItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), res, parent, false);
        AppointBean dataBean = (AppointBean) datas.get(position);
        initAppointItem(binding,dataBean);
        binding.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.cb.isSelected();
                binding.cb.setSelected(!selected);
                if (!selected) {
                    if (!selectList.contains(dataBean)) {
                        selectList.add(dataBean);
                    }
                } else {
                    clickSureListener.select();
                    if (selectList.contains(dataBean)) {
                        selectList.remove(dataBean);
                    }
                }
            }
        });
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSureListener.click(position);
            }
        });
        View view = binding.getRoot();
        view.setTag(binding);
        return view;
    }


    private void initAppointItem(ListSellrecordItemBinding binding, AppointBean appointBean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(appointBean.getAddress());
        appointItemView.setAveragePrice(appointBean.getAveragePrice()+"");
        appointItemView.setConstructionArea(appointBean.getConstructionArea());
        appointItemView.setPremisesName(appointBean.getPremisesName());
        appointItemView.setPropertyType(appointBean.getPropertyType());
        appointItemView.setRegion(appointBean.getRegion());
        binding.setBean(appointItemView);
        String picture = appointBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = appointBean.getCharacteristics();
        for (int i = 0; i < characteristics.size() + 1; i++) {
            if (i < 3) {
                TextView item;
                if (i == 0) {
                    item = (TextView) View.inflate(context, R.layout.layout_character1, null);
                    item.setText(appointBean.getState());
                } else {
                    item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                    item.setText(characteristics.get(i - 1));
                }
                binding.appointItem.llexpend.addView(item, i);
            }
        }
    }

    @Override
    public void setSelect(View view, boolean select,int position) {
        ListSellrecordItemBinding binding = (ListSellrecordItemBinding) view.getTag();
        binding.cb.setSelected(select);
        if (select) {
            selectList.add((AppointBean) datas.get(position));
        } else {
            selectList.remove(datas.get(position));
        }


    }
}
