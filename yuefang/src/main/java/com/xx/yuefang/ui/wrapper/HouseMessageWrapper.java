package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.WrapperMessageBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.HouseMessageFragment;
import com.xx.yuefang.ui.fragment.house.houseDetail.MapDetail;
import com.xx.yuefang.ui.fragment.house.houseDetail.Poster;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class HouseMessageWrapper extends Wrapper {

    private WrapperMessageBinding binding;
    private Bitmap posterBitmap;

    public HouseMessageWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_message, this, false);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        binding.setData(data);
        if (!binding.getIsInit()) {
            binding.setIsInit(true);
            String posterPicture = data.getPosterPicture();
            String url = ImagUtil.handleUrl(posterPicture);
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(context).getBitmap(url).subscribe(
                        imageBean -> {
                            posterBitmap = imageBean.getBitmap();
                            if (posterBitmap != null) {
                                int width = posterBitmap.getWidth();
                                int height = posterBitmap.getHeight();
                                int viewWidth = binding.poster.getWidth();
                                int viewHeight = (viewWidth * height) / width;
                                ViewGroup.LayoutParams layoutParams = binding.poster.getLayoutParams();
                                layoutParams.height = viewHeight;
                                binding.poster.setLayoutParams(layoutParams);
                                Drawable conner = ImagUtil.conner(imageBean.getBitmap());
                                binding.poster.setBackground(conner);
                            }

                        }
                );
            } else {
                binding.poster.setVisibility(View.GONE);
            }

            binding.poster.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Poster poster = new Poster();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", data.getId());
                    bundle.putString("name", data.getPremisesName());
                    poster.setArguments(bundle);
                    Presenter.getInstance().step2fragment(poster, "poster");
                }
            });

            binding.toMapDetail.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapDetail mapDetail = new MapDetail();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dataBean", data);
                    mapDetail.setArguments(bundle);
                    Presenter.getInstance().step2fragment(mapDetail, "mapDetail");
                }
            });


            LinearLayout ll = (LinearLayout) View.inflate(context, R.layout.layout_characters, null);
            StringBuffer length = new StringBuffer();
            TextView state = (TextView) View.inflate(context, R.layout.layout_character1, null);
            state.setText(data.getState());
            length.append(data.getState());
            ll.addView(state);
            List<String> characteristics = data.getCharacteristics();
            binding.characeters.addView(ll);
            for (int i = 0; i < characteristics.size(); i++) {
                String s = characteristics.get(i);
                if (length.toString().length() + s.length() <= 22) {
                    length.append(s);
                    TextView item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                    item.setText(s);
                    ll.addView(item);
                } else {
                    length = new StringBuffer();
                    ll = (LinearLayout) View.inflate(context, R.layout.layout_characters, null);
                    binding.characeters.addView(ll);
                }

            }
            binding.lookMore.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    HouseMessageFragment houseMessageFragment = new HouseMessageFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", data);
                    houseMessageFragment.setArguments(bundle);
                    Presenter.getInstance().step2fragment(houseMessageFragment, "houseMessage");
                }
            });

            StringBuffer sb = new StringBuffer();
            List<String> houseTypes = data.getHouseTypes();
            if (houseTypes != null) {
                for (int i = 0; i < houseTypes.size(); i++) {
                    String houseType = houseTypes.get(i);
                    if (i == 0) {
                        sb.append(houseType);
                    } else {
                        sb.append("/" + houseType);
                    }
                }
                binding.houseType.setPart2Text(sb.toString());
            }
        }
    }


    public void recycler() {
        if (posterBitmap != null) {
            posterBitmap.recycle();
            posterBitmap = null;
        }
    }

}
