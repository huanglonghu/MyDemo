package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesBean;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.ItemListHomeBinding;
import com.xx.yuefang.databinding.WrapperGuessYouLikeeBinding;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class GuessYouLikeWrapper extends Wrapper {

    private WrapperGuessYouLikeeBinding binding;

    public GuessYouLikeWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_guess_you_likee, this, false);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        if (binding.getData() == null) {
            binding.setData(data);
            List<PremisesBean> premisesLists = data.getPremisesLists();
            if (premisesLists != null && premisesLists.size() > 0) {
                for (int i = 0; i < premisesLists.size(); i++) {
                    PremisesBean premisesListsBean = premisesLists.get(i);
                    String picture = premisesListsBean.getPicture();
                    ItemListHomeBinding b = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_list_home, binding.llExpend, false);
                    b.setDataBean(premisesListsBean);
                    String url = ImagUtil.handleUrl(picture);
                    if (!TextUtils.isEmpty(url)) {
                        RxImageLoader.with(context).getBitmap(url).subscribe(
                                imageBean -> {
                                    b.imgLoading.setVisibility(View.GONE);
                                    b.img.setBackground(new BitmapDrawable(context.getResources(),imageBean.getBitmap()));
                                }
                        );
                    }
                    initItem(data,b);
                    View view = b.getRoot();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventData eventData = new EventData(UserObservable.TYPE_HOUSEDETAIL_TOGGLE);
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", premisesListsBean.getId());
                            bundle.putInt("currentId",data.getId());
                            eventData.setData(bundle);
                            UserObservable.getInstance().notifyObservers(eventData);
                        }
                    });
                    binding.llExpend.addView(view);
                }
            }
        }

    }


    public void initItem(PremisesDetail.DataBean data,ItemListHomeBinding binding){
        if (data != null) {
            String constructionArea = data.getConstructionArea();
            String totalPrice = data.getTotalPrice();
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

            List<String> characteristics = data.getCharacteristics();
            TextView tv = (TextView) View.inflate(context, R.layout.layout_character1, null);
            tv.setText(data.getState());
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
    }

}
