package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.WrapperDynamicBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.Dynamic;
import com.xx.yuefang.ui.fragment.house.houseDetail.DynamicDetail;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.TimeUtil;

import java.util.List;

public class DynamicWrapper extends Wrapper {

    private WrapperDynamicBinding binding;


    public DynamicWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_dynamic, this, false);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        List<PremisesDetail.DataBean.PremisesNewsInfosBean> premisesNewsInfos = data.getPremisesNewsInfos();
        if (binding.getData() == null) {
            if (premisesNewsInfos != null && premisesNewsInfos.size() > 0) {
                binding.setData(data);
                PremisesDetail.DataBean.PremisesNewsInfosBean premisesNewsInfosBean = data.getPremisesNewsInfos().get(0);
                String picture = premisesNewsInfosBean.getPicture();
                String richTextContent = premisesNewsInfosBean.getRichTextContent();
                if (!TextUtils.isEmpty(richTextContent)) {
                    richTextContent = richTextContent.replaceAll("href=", "");
                    Spanned spanned = Html.fromHtml(richTextContent);
                    binding.content.setText(spanned);
                }
                PremisesDetail.DataBean.PremisesActivityBean premisesActivity = data.getPremisesActivity();
                if (premisesActivity != null) {
                    String creationTime = premisesActivity.getCreationTime();
                    creationTime = TimeUtil.getStringToDate(creationTime);
                    premisesActivity.setCreationTime(creationTime);
                    int id = premisesActivity.getId();
                    binding.rlDynamic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UserBean userBean = UserOption.getInstance().querryUser();
                            if (userBean != null) {
                                Dynamic dynamic = new Dynamic();
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", id);
                                dynamic.setArguments(bundle);
                                Presenter.getInstance().step2fragment(dynamic, "dynamic");
                            } else {
                                Presenter.getInstance().step2Fragment("login");
                            }

                        }
                    });

                }

                binding.rlNews.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DynamicDetail dynamicDetail = new DynamicDetail();
                        dynamicDetail.setDatas(premisesNewsInfos);
                        Presenter.getInstance().step2fragment(dynamicDetail, "dynamicDetail");
                    }
                });
                binding.setNewsinfo(premisesNewsInfosBean);
                binding.setDynamic(premisesActivity);
            }
        }


    }
}
