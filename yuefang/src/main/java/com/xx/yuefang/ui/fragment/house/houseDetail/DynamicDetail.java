package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.databinding.FragmentDynamicDetailBinding;
import com.xx.yuefang.databinding.ListDynamicItemBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.BaseListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;
import java.util.List;

public class  DynamicDetail extends BaseFragment {

    private FragmentDynamicDetailBinding binding;
    private List<PremisesDetail.DataBean.PremisesNewsInfosBean> datas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic_detail, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        return binding.getRoot();
    }

    @Override
    public void initData() {}

    public void setDatas(List<PremisesDetail.DataBean.PremisesNewsInfosBean> datas) {
        this.datas = datas;
    }

    @Override
    public void initView() {
        DynamicListAdapter dynamicListAdapter = new DynamicListAdapter(getContext(), datas, R.layout.list_dynamic_item);
        binding.listDynamic.setAdapter(dynamicListAdapter);
    }

    @Override
    public void initlisten() {

    }

    private class DynamicListAdapter extends BaseListAdapter {

        public DynamicListAdapter(Context context, List<PremisesDetail.DataBean.PremisesNewsInfosBean> datas, int res) {
            super(context, datas, res);
        }

        @Override
        protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
            ListDynamicItemBinding itemBinding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
            PremisesDetail.DataBean.PremisesNewsInfosBean data = (PremisesDetail.DataBean.PremisesNewsInfosBean) datas.get(position);
            String richTextContent = data.getRichTextContent();
            if(!TextUtils.isEmpty(richTextContent)){
                if(richTextContent.contains("href")){
                    richTextContent=richTextContent.replaceAll("href","");
                }
                Spanned spanned = Html.fromHtml(richTextContent);
                itemBinding.content.setText(spanned);
            }
            itemBinding.setData(data);
            itemBinding.toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemBinding.content.toggle();
                    int expandState = itemBinding.content.getExpandState();
                    itemBinding.setState(expandState);
                }
            });
            return itemBinding.getRoot();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
    }


}
