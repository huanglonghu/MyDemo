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
import com.xx.yuefang.bean.HouseResourceBean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.ListHouseResoucesItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.me.developer.AppointSaleperson;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.ImagUtil;

import java.util.ArrayList;
import java.util.List;

public class HouseResourcesAdapter extends BaseList2Adapter {

    private int userType;
    private ClickSureListener clickSureListener;

    public HouseResourcesAdapter(Context context, List<HouseResourceBean> datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean!=null){
            userType = userBean.getUserType();
        }
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent, View convertView) {
        ListHouseResoucesItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        HouseResourceBean dataBean = (HouseResourceBean) datas.get(position);
        initAppointItem(binding, dataBean);
        binding.setUserType(userType);
        binding.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tip = null;
                if (dataBean.isIsActive()) {
                    tip = "您确定要下架该房源";
                } else {
                    tip = "您确定要上架该房源";
                }
                new TipDialog(context, new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        ArrayList<Integer> ids = new ArrayList<>();
                        ids.add(dataBean.getId());
                        removeByPosition(position);
                        HttpUtil.getInstance().houseResourceOption(ids).subscribe(
                                str -> {
                                    clickSureListener.click(position);
                                }
                        );
                    }
                }, tip).show();

            }
        });

        binding.cb.setSelected(selectMap.get(position) != null);
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

        binding.selectSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointSaleperson appointSaleperson = new AppointSaleperson();
                Bundle bundle = new Bundle();
                bundle.putInt("developerId", dataBean.getId());
                appointSaleperson.setArguments(bundle);
                Presenter.getInstance().step2fragment(appointSaleperson, "apointSalepreson");
            }
        });

        View view = binding.getRoot();
        view.setTag(binding);
        return binding.getRoot();
    }


    private void initAppointItem(ListHouseResoucesItemBinding binding, HouseResourceBean dataBean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(dataBean.getAddress());
        appointItemView.setAveragePrice(dataBean.getAveragePrice() + "");
        appointItemView.setConstructionArea(dataBean.getConstructionArea());
        appointItemView.setPremisesName(dataBean.getPremisesName());
        appointItemView.setPropertyType(dataBean.getPropertyType());
        appointItemView.setRegion(dataBean.getRegion());
        appointItemView.setActive(dataBean.isIsActive());
        binding.setBean(appointItemView);
        String picture = dataBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = dataBean.getCharacteristics();
        if (binding.appointItem.llexpend.getChildCount() == 0) {
            for (int i = 0; i < characteristics.size() + 1; i++) {
                if (i < 3) {
                    TextView item;
                    if (i == 0) {
                        item = (TextView) View.inflate(context, R.layout.layout_character1, null);
                        item.setText(dataBean.getState());
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
