package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.bean.FansDetailList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.AppointItemBinding;
import com.xx.yuefang.databinding.ListItemFansdetailBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class FansDetailAdapter extends BaseListAdapter {
    public FansDetailAdapter(Context context, List datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListItemFansdetailBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item_fansdetail, parent, false);
        FansDetailList.DataBean dataBean = (FansDetailList.DataBean) datas.get(position);
        initAppointItem(binding, dataBean);
//        binding.appointItem.rlAppoint.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogUtil.log("==========clickFansDetail=============");
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_UP:
//                        HouseDetailContainer houseDetailContainer = new HouseDetailContainer();
//                        Bundle bundle = new Bundle();
//                        bundle.putInt("id", dataBean.getPremisesId());
//                        houseDetailContainer.setArguments(bundle);
//                        Presenter.getInstance().step2fragment(houseDetailContainer, "houseDetailContainer");
//                        break;
//                }
//                return false;
//            }
//        });
        return binding.getRoot();
    }

    private void initAppointItem(ListItemFansdetailBinding binding, FansDetailList.DataBean dataBean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(dataBean.getAddress());
        appointItemView.setAveragePrice(dataBean.getAveragePrice() + "");
        appointItemView.setConstructionArea(dataBean.getConstructionArea());
        appointItemView.setPremisesName(dataBean.getPremisesName());
        appointItemView.setPropertyType(dataBean.getPropertyType());
        appointItemView.setRegion(dataBean.getRegion());

        appointItemView.setDate(dataBean.getReservationTime());
        appointItemView.setReservationState(dataBean.getReservationState());
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
