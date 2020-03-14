package com.xx.yuefang.ui.fragment.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetPremisesList;
import com.xx.yuefang.bean.PremisesBean;
import com.xx.yuefang.bean.PremisesIdList;
import com.xx.yuefang.bean.PremisesList;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.SearchStrOption;
import com.xx.yuefang.database.entity.SearchStr;
import com.xx.yuefang.databinding.FragmentHomeBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.HomeAdapter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.house.HotSearch;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.SelectCity;
import com.xx.yuefang.ui.widget.PrivacyDialog;
import com.xx.yuefang.ui.widget.RequireWindow;
import com.xx.yuefang.ui.widget.TipDialog2;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends LazyLoadFragment implements HotSearch.SelectKeyWordBack, ScrollRefreshListView.RefreshData, ScrollRefreshListView.Back {
    private FragmentHomeBinding binding;
    private HomeAdapter homeAdapter;
    private View view;
    private List<PremisesBean> list;
    private RequireWindow requireWindow;
    private GetPremisesList getPremisesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
            binding.setFragment(this);
            requestPermission();
            initView();
            initlisten();
            view = binding.getRoot();
        }
        initData();

        return view;
    }
    private void requestPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.RECORD_AUDIO, Permission.CAMERA, Permission.ACCESS_FINE_LOCATION, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.ACCESS_COARSE_LOCATION)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();
    }


    @Override
    public void initData() {
        HttpUtil.getInstance().getTotalById(0).subscribe(
                str -> {
                    PremisesIdList premisesIdList = GsonUtil.fromJson(str, PremisesIdList.class);
                    PremisesIdList.DataBean data = premisesIdList.getData();
                    if (data != null) {
                        HttpParam.ids = (ArrayList<Integer>) data.getIds();
                    }
                }
        );

    }


    private void requirePremisesList(GetPremisesList getPremisesList) {
        HttpUtil.getInstance().getPremisesList(getPremisesList).subscribe(
                str -> {
                    if (getPremisesList.getPage() == 1 && list.size() > 0) {
                        homeAdapter.clearView();
                        list.clear();
                    }
                    PremisesList premisesList = GsonUtil.fromJson(str, PremisesList.class);
                    PremisesList.DataBeanX data = premisesList.getData();
                    if (data != null) {
                        List<PremisesBean> beans = data.getData();
                        if (beans != null) {
                            if (beans.size() > 0) {
                                binding.lv.setLoading(true);
                                list.addAll(beans);
                            } else {
                                binding.lv.setLoading(false);
                                if (getPremisesList.getPage() == 1) {
                                    binding.lv.showEmpty();
                                }
                            }
                        }
                    }
                    homeAdapter.notifyDataSetChanged();
                }
        );

    }


    @Override
    public void initView() {
        binding.lv.setRefreshData(this);
        binding.lv.setBack(this);
        list = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), list, R.layout.item_list_home);
        binding.lv.setAdapter(homeAdapter);
        if (Presenter.getInstance().hasNotchScreen(getActivity())) {
            binding.ll.setPadding(0, 90, 0, 0);
        }
    }

    @Override
    public void initlisten() {
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh();
                if (getPremisesList != null) {
                    getPremisesList.setPage(1);
                    binding.lv.setPage(1);
                    int selectPosition = binding.getSelectPosition();
                    LogUtil.log("==========selection=========" + selectPosition);
                    if (selectPosition == 1 || selectPosition == 2) {
                        getPremisesList.setSort(1);
                    } else if (selectPosition == 3) {
                        if (getPremisesList.getCharacteristicTypes().size() == 0 && getPremisesList.getEndArea() == 0 && getPremisesList.getEndPrice() == 0
                                && getPremisesList.getHouseTypes().size() == 0 && getPremisesList.getPropertyTypes().size() == 0 && getPremisesList.getRenovationType() == 0 && getPremisesList.getStartArea() == 0
                                && getPremisesList.getStartPrice() == 0 && getPremisesList.getStartTotalPrice() == 0 && getPremisesList.getState() == 0
                        ) {
                            getPremisesList.setSort(1);
                        }


                    } else if (selectPosition == 4) {
                        String where = getPremisesList.getWhere();
                        if (TextUtils.isEmpty(where)) {
                            getPremisesList.setSort(1);
                        }
                    }

                    requirePremisesList(getPremisesList);
                }
            }
        });

        binding.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCity selectCity = new SelectCity();
                Presenter.getInstance().step2fragment(selectCity, "selectCity");
            }
        });

        binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() > 0) {
                    PremisesBean dataBean = list.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", dataBean.getId());
                    HouseDetailContainer houseDetailContainer = new HouseDetailContainer();
                    houseDetailContainer.setArguments(bundle);
                    Presenter.getInstance().step2fragment(houseDetailContainer, "houseDetailContainer");
                }
            }
        });
    }

    public void search(View v, int position) {
        getPremisesList = new GetPremisesList();
        getPremisesList.setLimit(10);
        getPremisesList.setPage(1);
        getPremisesList.setUserInitialize(true);
        binding.lv.setPage(1);
        binding.lv.removefoot();
        switch (position) {
            case 1:
                getPremisesList.setSort(1);
                binding.setSelectPosition(position);
                requirePremisesList(getPremisesList);
                break;
            case 2:
                getPremisesList.setSort(1);
                binding.setSelectPosition(position);
                requirePremisesList(getPremisesList);
                break;
            case 3:

                if (requireWindow == null) {
                    requireWindow = new RequireWindow(getActivity(), new ClickSureListener() {
                        @Override
                        public void clickSure() {
                            binding.setSelectPosition(position);
                            requirePremisesList(getPremisesList);
                        }
                    });
                }
                requireWindow.show(v, getPremisesList);
                break;
            case 4:
                HotSearch hotSearch = new HotSearch();
                String content = binding.searchEt.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("content", content);
                hotSearch.setArguments(bundle);
                hotSearch.setSelectKeyWordBack(this);
                Presenter.getInstance().step2fragment(hotSearch, "hotSearch");
                binding.setSelectPosition(position);
                break;
            case 5:
                getPremisesList.setSort(1);
                binding.setSelectPosition(1);
                requirePremisesList(getPremisesList);
                break;
        }


    }


    @Override
    public void selectKeyWord(String keyWord) {
        binding.searchEt.setText(keyWord);
        getPremisesList.setWhere(keyWord);
        requirePremisesList(getPremisesList);
    }

    @Override
    public void refreshData(int page) {//页面发生变化 需要获取新的页面数据
        if (getPremisesList != null) {
            getPremisesList.setPage(page);
            if (binding.getSelectPosition() == 2) {
                getPremisesList.setSort(2);
            }
            requirePremisesList(getPremisesList);
        }
    }

    @Override
    public void back() {
        getPremisesList = new GetPremisesList();
        getPremisesList.setLimit(10);
        getPremisesList.setPage(1);
        binding.lv.setPage(1);
        binding.searchEt.setText("");
        requirePremisesList(getPremisesList);
    }

    @Override
    protected void loadData() {
        search(null, 1);
        SearchStr searchStr = SearchStrOption.getInstance().getSearchStr();
        if (searchStr == null) {
            HttpUtil.getInstance().getSearchDatas().subscribe(
                    str -> {
                        SearchStr searchStr1 = new SearchStr();
                        searchStr1.setStr(str);
                        SearchStrOption.getInstance().addSearchStr(searchStr1);
                    }
            );
        }

    }

}
