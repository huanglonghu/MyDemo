package com.xx.yuefang.ui.fragment.me;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.FansDetailList;
import com.xx.yuefang.bean.FansList;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.FragmentFansdetailBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.FansDetailAdapter;
import com.xx.yuefang.ui.adapter.FansListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;

public class FansDetail extends BaseFragment {

    private FragmentFansdetailBinding binding;
    private ArrayList<FansDetailList.DataBean> list;
    private FansDetailAdapter fansDetailAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fansdetail, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        FansList.DataBeanX.DataBean dataBean = (FansList.DataBeanX.DataBean) getArguments().getSerializable("fans");
        binding.setDataBean(dataBean);
        String avatar = dataBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        Drawable circle = ImagUtil.circle(bitmap);
                        binding.ivHead.setImageDrawable(circle);
                    }
            );
        }
        HttpUtil.getInstance().getFansDetail(dataBean.getUserId()).subscribe(
                str -> {
                    FansDetailList fansDetailList = GsonUtil.fromJson(str, FansDetailList.class);
                    list.addAll(fansDetailList.getData());
                    fansDetailAdapter.notifyDataSetChanged();
                }
        );
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        fansDetailAdapter = new FansDetailAdapter(getContext(), list, R.layout.appoint_item);
        binding.lvFansDetail.setAdapter(fansDetailAdapter);

    }

    @Override
    public void initlisten() {

        binding.lvFansDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FansDetailList.DataBean dataBean = list.get(position);
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",dataBean.getPremisesId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");

            }
        });
    }
}
