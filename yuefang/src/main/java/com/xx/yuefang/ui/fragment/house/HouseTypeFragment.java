package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayout2Binding;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.FragmentHousetypeBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.GridListAdapter2;
import com.xx.yuefang.ui.adapter.HouseTypeAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HouseTypeFragment extends BaseFragment {

    private FragmentHousetypeBinding binding;
    private List<PremisesDetail.DataBean.HouseTypeInfosBean> houseTypeInfos;
    private ArrayList<PremisesDetail.DataBean.HouseTypeInfosBean> datas;
    private HouseTypeAdapter houseTypeAdapter;
    private ArrayList<String> typeArray;
    private PremisesDetail.DataBean dataBean;
    private int selectPosition;
    private GridListAdapter2 gridListAdapter;

    public void setPremisesData(PremisesDetail.DataBean dataBean) {
        this.dataBean = dataBean;
        houseTypeInfos = dataBean.getHouseTypeInfos();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_housetype, container, false);
            binding.setFragment(this);
            binding.setPresenter(Presenter.getInstance());
            binding.setData(dataBean);
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean != null) {
                binding.setUserType(userBean.getUserType());
            }
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        datas = new ArrayList<PremisesDetail.DataBean.HouseTypeInfosBean>();
        typeArray = getArguments().getStringArrayList("houseTypes");
        selectPosition = getArguments().getInt("selectPosition");
        if (typeArray != null && typeArray.size() > 0) {
            String type = typeArray.get(selectPosition);
            datas.addAll(searchHouseType(type));
            gridListAdapter = new GridListAdapter2(getContext(), typeArray, R.layout.layout_select_housetype, selectPosition);
            binding.grid.setAdapter(gridListAdapter);
            houseTypeAdapter = new HouseTypeAdapter(getActivity(), datas, R.layout.housetype_list_item);
            binding.houseTypeLv.setAdapter(houseTypeAdapter);
        } else {
            showEmptyLayout();
            binding.grid.setVisibility(View.GONE);
        }


    }

    public void showEmptyLayout() {
        if (!binding.emptyLayout.isInflated()) {
            View emptyView = binding.emptyLayout.getViewStub().inflate();
            EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
            emptyLayoutBinding.setTip("暂无楼盘户型");
        }
    }

    @Override
    public void initlisten() {
        binding.houseTypeLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseTypeDetailFragment houseTypeDetailFragment = new HouseTypeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("houseTypes", typeArray);
                bundle.putSerializable("houseTypeInfo", houseTypeInfos.get(position));
                bundle.putSerializable("data", dataBean);
                bundle.putBoolean("isFromList", true);
                bundle.putInt("selectPosition", selectPosition);
                houseTypeDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseTypeDetailFragment, "houseTypeDetail");
            }
        });

        binding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggleList(position);
            }
        });

        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                switch (data.getEventType()) {
                    case UserObservable.TYPE_HOUSETYPEDETAL_BACK: {
                        Bundle bundle = data.getData();
                        int selectPosition = bundle.getInt("selectPosition");
                        gridListAdapter.refreshCheckPosition(selectPosition);
                        toggleList(selectPosition);
                    }
                    break;
                }
            }
        };
        UserObservable.getInstance().register(userObserver);
    }

    private void toggleList(int position) {
        datas.clear();
        houseTypeAdapter.clearView();
        selectPosition = position;
        gridListAdapter.refreshCheckPosition(position);
        ArrayList<PremisesDetail.DataBean.HouseTypeInfosBean> houseTypeInfosBeans = searchHouseType(typeArray.get(position));
        datas.addAll(houseTypeInfosBeans);
        houseTypeAdapter.notifyDataSetChanged();
    }


    private ArrayList<PremisesDetail.DataBean.HouseTypeInfosBean> searchHouseType(String type) {
        ArrayList<PremisesDetail.DataBean.HouseTypeInfosBean> list = new ArrayList<>();
        for (int i = 0; i < houseTypeInfos.size(); i++) {
            PremisesDetail.DataBean.HouseTypeInfosBean houseTypeInfosBean = houseTypeInfos.get(i);
            String houseType = houseTypeInfosBean.getHouseType();
            if (houseType.equals(type)) {
                list.add(houseTypeInfosBean);
            }
        }
        return list;
    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
