package com.xx.yuefang.ui.fragment.me.developer;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.BrowseRecords;
import com.xx.yuefang.bean.GetHouseResourcesList;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.bean.HouseResourceBean;
import com.xx.yuefang.bean.HouseResourcesList;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutHouseresourceBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.HouseResourcesAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.fragment.house.HouseArFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.widget.SearchDialog1;
import com.xx.yuefang.ui.widget.SearchDialog3;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HouseResourcesPage extends LazyLoadFragment implements ScrollRefreshListView.RefreshData {
    private List<HouseResourceBean> list;
    private UserBean userBean;
    private LayoutHouseresourceBinding binding;
    private HouseResourcesAdapter houseResourcesAdapter;
    private HouseResouces parent;
    private GetHouseResourcesList getHouseResourcesList;
    private View emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            userBean = UserOption.getInstance().querryUser();
            setNeedReloadData(true);
            parent = (HouseResouces) getParentFragment();
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_houseresource, null, false);
            binding.lvHouseResource.setRefreshData(this);
            binding.setUserType(userBean.getUserType());
            binding.setIsActive(type == 1 ? true : false);
            binding.setType(type);
            initData();
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void search(String premisesName, boolean search) {
        if (getHouseResourcesList == null) {
            initData();
        }
        getHouseResourcesList.setPage(1);
        getHouseResourcesList.setPremisesName(premisesName);
        if (search) {
            getHouseResource(getHouseResourcesList);
            parent.setPremisesName(premisesName);
            parent.getNum();
        }
    }

    @Override
    public void initData() {
        if (getHouseResourcesList == null) {
            getHouseResourcesList = new GetHouseResourcesList();
            getHouseResourcesList.setLimit(20);
            getHouseResourcesList.setActive(type);
            getHouseResourcesList.setPage(1);
        }

    }

    private void getHouseResource(GetHouseResourcesList getHouseResourcesList) {
        if (getHouseResourcesList.getPage() == 1 && list.size() > 0) {
            list.clear();
        }
        if (userBean.getUserType() == 3) {
            getHouseResourcesList.setSalespersonId(userBean.getId());
            HttpUtil.getInstance().getHouseResourceListBySeller(getHouseResourcesList).subscribe(
                    str -> {
                        handlerStr(str);
                    }
            );
        } else if (userBean.getUserType() == 2) {
            getHouseResourcesList.setDeveloperId(userBean.getId());
            HttpUtil.getInstance().getHouseResourceListByDeveloper(getHouseResourcesList).subscribe(
                    str -> {
                        handlerStr(str);
                    }
            );
        }
    }

    private void handlerStr(String str) {
        HouseResourcesList houseResourcesList = GsonUtil.fromJson(str, HouseResourcesList.class);
        HouseResourcesList.DataBeanX data = houseResourcesList.getData();
        if (data != null) {
            List<HouseResourceBean> datas = data.getData();
            if (datas != null && datas.size() > 0) {
                list.addAll(datas);
                binding.lvHouseResource.setLoading(true);
                houseResourcesAdapter.notifyDataSetChanged();
            }
        }
        showEmptyLayout();
    }

    public void showEmptyLayout() {
        if (list.size() == 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("暂无房源");
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void loadData() {
        getHouseResource(getHouseResourcesList);
    }


    @Override
    public void initView() {
        list = new ArrayList<>();
        houseResourcesAdapter = new HouseResourcesAdapter(getContext(), list, R.layout.list_house_resouces_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                getHouseResourcesList.setPage(1);
                getHouseResource(getHouseResourcesList);
                parent.initData();
            }

            @Override
            public void select() {
                binding.selectAll.setSelected(false);
            }
        });
        binding.lvHouseResource.setAdapter(houseResourcesAdapter);
    }

    @Override
    public void initlisten() {
        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelectAll = binding.selectAll.isSelected();
                binding.selectAll.setSelected(!isSelectAll);
                houseResourcesAdapter.selectAll(!isSelectAll);
            }
        });


        binding.lvHouseResource.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HouseResourceBean dataBean = list.get(position);
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", dataBean.getId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });


        binding.optionAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "没有房源可操作", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<Integer, Boolean> selectMap = houseResourcesAdapter.getSelectMap();
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<HouseResourceBean> selectList = new ArrayList<>();
                for (int i : selectMap.keySet()) {
                    Boolean isSelect = selectMap.get(i);
                    if (isSelect) {
                        HouseResourceBean dataBean = list.get(i);
                        ids.add(dataBean.getId());
                        selectList.add(dataBean);
                    }
                }
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选房源", Toast.LENGTH_SHORT).show();
                    return;
                }
                String tip = null;
                if (type == 1) {
                    tip = "您确定要批量下架";
                } else if (type == 2) {
                    tip = "您确定要批量上架";
                }
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        HttpUtil.getInstance().houseResourceOption(ids).subscribe(
                                str -> {
                                    list.removeAll(selectList);
                                    binding.selectAll.setSelected(false);
                                    houseResourcesAdapter.clearSelectMap();
                                    houseResourcesAdapter.notifyDataSetChanged();
                                    parent.getNum();
                                }
                        );
                    }
                }, tip).show();

            }
        });

    }

    @Override
    public void refreshData(int page) {
        getHouseResourcesList.setPage(page);
        getHouseResource(getHouseResourcesList);
    }
}
