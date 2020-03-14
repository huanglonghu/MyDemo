package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ListMycommetItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.me.AppointCommentFirst;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class CommentsAdapter extends BaseAdapter {
    private Context context;
    private List<AppointBean> list;

    public CommentsAdapter(Context context, List<AppointBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        ListMycommetItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_mycommet_item, parent, false);
        AppointBean appointBean = list.get(position);
        initAppointItem(binding,appointBean);
        binding.lookComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointCommentFirst commentDetailFragment = new AppointCommentFirst();
                Bundle bundle = new Bundle();
                bundle.putInt("id",appointBean.getId());
                commentDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(commentDetailFragment, "commentDetail");
            }
        });
        return binding.getRoot();
    }


    private void initAppointItem(ListMycommetItemBinding binding, AppointBean appointBean) {
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
}
