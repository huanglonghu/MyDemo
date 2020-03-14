package com.xx.yuefang.ui.fragment.me.employee;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.EmployeeBean;
import com.xx.yuefang.bean.GetSellerList;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentFzsMyemployeeBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.MyEmployeeListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.widget.SearchDialog1;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyEmployee extends BaseFragment implements ScrollRefreshListView.RefreshData {

    private FragmentFzsMyemployeeBinding binding;
    private MyEmployeeListAdapter myEmployeeListAdapter;
    private UserBean userBean;
    private List<EmployeeBean.DataBeanX.DataBean> list;
    private GetSellerList getSellerList;
    private int developerId;
    private SearchDialog1 searchDialog1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userBean = UserOption.getInstance().querryUser();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fzs_myemployee, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        getSellerList = new GetSellerList();
        developerId = userBean.getId().intValue();
        getSellerList.setDeveloperId(developerId);
        getSellerList.setLimit(20);
        getSellerList.setPage(1);
        getAllEmployee(getSellerList);

    }

    private void getAllEmployee(GetSellerList getSellerList) {
        if (getSellerList.getPage() == 1 && list.size() > 0) {
            list.clear();
        }

        if (userBean.getUserType() == 2) {
            HttpUtil.getInstance().getSellerList(getSellerList).subscribe(
                    str -> {
                        setData(str);
                    }
            );
        } else if (userBean.getUserType() == 5) {
            HttpUtil.getInstance().getParterList(getSellerList).subscribe(
                    str -> {
                        setData(str);
                    }
            );
        }

    }

    private void setData(String str) {
        EmployeeBean employeeBean = GsonUtil.fromJson(str, EmployeeBean.class);
        EmployeeBean.DataBeanX data = employeeBean.getData();
        if (data != null) {
            List<EmployeeBean.DataBeanX.DataBean> datas = data.getData();
            if (datas != null && datas.size() > 0) {
                binding.listMyemployee.setLoading(true);
                list.addAll(datas);
                myEmployeeListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void initView() {
        list = new ArrayList<>();
        myEmployeeListAdapter = new MyEmployeeListAdapter(getContext(), list, R.layout.list_myemployee_item, userBean.getUserType(), new ClickSureListener() {
            @Override
            public void select() {
                if (binding.getIsSelectAll()) {
                    binding.setIsSelectAll(false);
                }
            }
        });
        binding.listMyemployee.setAdapter(myEmployeeListAdapter);
    }

    @Override
    public void initlisten() {

        binding.employeeToolbar.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchDialog1 == null){
                    searchDialog1 = new SearchDialog1((AppCompatActivity) getActivity(), new ClickSureListener() {
                        @Override
                        public void search(int conditionType, String condition) {
                            getSellerList.setPage(1);
                            getSellerList.setConditionType(conditionType);
                            getSellerList.setCondition(condition);
                            getAllEmployee(getSellerList);
                        }
                    });
                }
                searchDialog1.show();
            }
        });

        binding.selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.getIsSelectAll();
                binding.setIsSelectAll(!selected);
                myEmployeeListAdapter.selectAll(!selected);
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userBean.getUserType() == 2) {
                    AddSalerPerson addSalerPerson = new AddSalerPerson();
                    Presenter.getInstance().step2fragment(addSalerPerson, "addSaler");
                } else if (userBean.getUserType() == 5) {
                    AddParter addParter = new AddParter();
                    Presenter.getInstance().step2fragment(addParter, "addPater");
                }
            }
        });

        binding.deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请添加房源", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<Integer, Boolean> selectMap = myEmployeeListAdapter.getSelectMap();
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<EmployeeBean.DataBeanX.DataBean> selectList = new ArrayList<>();
                for (int i : selectMap.keySet()) {
                    Boolean isSelect = selectMap.get(i);
                    if (isSelect) {
                        EmployeeBean.DataBeanX.DataBean dataBean = list.get(i);
                        ids.add(dataBean.getId());
                        selectList.add(dataBean);
                    }
                }
                if (selectList.size() == 0) {
                    Toast.makeText(getContext(), "请勾选员工", Toast.LENGTH_SHORT).show();
                    return;
                }
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        if (userBean.getUserType() == 2) {
                            HttpUtil.getInstance().deleteSalepersons(ids).subscribe(
                                    str -> {
                                        list.removeAll(selectList);
                                        binding.selectAll.setSelected(false);
                                        myEmployeeListAdapter.clearSelectMap();
                                        myEmployeeListAdapter.notifyDataSetChanged();
                                    }
                            );
                        } else if (userBean.getUserType() == 5) {
                            HttpUtil.getInstance().deleteParters(ids).subscribe(
                                    str -> {
                                        list.removeAll(selectList);
                                        binding.selectAll.setSelected(false);
                                        myEmployeeListAdapter.clearSelectMap();
                                        myEmployeeListAdapter.notifyDataSetChanged();
                                    }
                            );
                        }

                    }
                }, "您确定要删除员工?").show();

            }
        });
    }

    @Override
    public void refreshData(int page) {
        getSellerList.setPage(page);
        getAllEmployee(getSellerList);
    }
}
