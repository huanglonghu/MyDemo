package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.HouseMessage;
import com.xx.yuefang.databinding.HousemessageListItem2Binding;
import com.xx.yuefang.databinding.HousemessageListItem3Binding;
import com.xx.yuefang.databinding.HousemessageListItem4Binding;
import com.xx.yuefang.databinding.HousemessageListItem5Binding;
import com.xx.yuefang.databinding.HousemessageListItem6Binding;
import com.xx.yuefang.databinding.HousemessageListItemBinding;
import com.xx.yuefang.databinding.ItemPremisesLicencesBinding;

import java.util.HashMap;
import java.util.List;

public class HousesMessageAdapter extends BaseAdapter {

    private HashMap<Integer, View> viewMap = new HashMap<>();
    private Context context;
    private HouseMessage.DataBean data;

    public HousesMessageAdapter(Context context, HouseMessage.DataBean data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewMap.get(position) == null) {
            int res = getLayoutRes(position);
            convertView = LayoutInflater.from(context).inflate(res, parent, false);
            viewMap.put(position, convertView);
            initItem(convertView, position);
        }
        return viewMap.get(position);
    }

    private void initItem(View convertView, int position) {
        switch (position) {
            case 0: {
                HousemessageListItem2Binding binding = DataBindingUtil.bind(convertView);
                binding.setData(data);
            }

            break;
            case 1: {
                HousemessageListItem3Binding binding = DataBindingUtil.bind(convertView);
                String houseTypes = data.getHouseTypes();
                if (!TextUtils.isEmpty(houseTypes)) {
                    if (houseTypes.contains(",")) {
                        String[] houseTypeArray = houseTypes.split(",");
                        StringBuffer houseType = new StringBuffer();
                        for (int i = 0; i < houseTypeArray.length; i++) {
                            if (i != houseTypeArray.length - 1) {
                                houseType.append(houseTypeArray[i] + "/");
                            } else {
                                houseType.append(houseTypeArray[i]);
                            }
                        }
                        binding.setHouseType(houseType.toString());


                    }


                }

                binding.setData(data);
            }
            break;
            case 2: {
                HousemessageListItem4Binding binding = DataBindingUtil.bind(convertView);
                List<HouseMessage.DataBean.PExtendsBean> pExtends = data.getPExtends();
                for (int i = 0; i < pExtends.size(); i++) {
                    HouseMessage.DataBean.PExtendsBean pExtendsBean = pExtends.get(i);
                    HousemessageListItemBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.housemessage_list_item, binding.llexpend, false);
                    binding1.setName(pExtendsBean.getKey());
                    binding1.setValue(pExtendsBean.getValue());
                    binding.llexpend.addView(binding1.getRoot());
                }
            }
            break;
            case 3: {
                HousemessageListItem5Binding binding = DataBindingUtil.bind(convertView);
                List<HouseMessage.DataBean.PremisesLicencesBean> premisesLicences = data.getPremisesLicences();
                if (premisesLicences != null && premisesLicences.size() > 0) {
                    for (int i = 0; i < premisesLicences.size(); i++) {
                        HouseMessage.DataBean.PremisesLicencesBean premisesLicencesBean = premisesLicences.get(i);
                        ItemPremisesLicencesBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_premises_licences, binding.itemContainer, false);
                        itemBinding.setData(premisesLicencesBean);
                        binding.itemContainer.addView(itemBinding.getRoot());
                    }
                }
            }
            break;

        }
    }

    public int getLayoutRes(int pos) {
        switch (pos) {
            case 0:
                return R.layout.housemessage_list_item2;
            case 1:
                return R.layout.housemessage_list_item3;
            case 2:
                return R.layout.housemessage_list_item4;
            case 3:
                return R.layout.housemessage_list_item5;
        }
        return 0;
    }

}
