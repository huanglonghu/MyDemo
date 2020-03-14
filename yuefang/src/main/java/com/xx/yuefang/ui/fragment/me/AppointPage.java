package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointList;
import com.xx.yuefang.bean.GetAllApoint;
import com.xx.yuefang.bean.GetReservationBody;
import com.xx.yuefang.bean.QuerryReservationBody;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayoutBinding;
import com.xx.yuefang.databinding.LayoutMyappointBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.AppointAdapter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.ui.widget.SearchDialog2;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class AppointPage extends LazyLoadFragment implements ScrollRefreshListView.RefreshData {
    private AppointAdapter appointAdapter;
    private LayoutMyappointBinding binding;
    private MyAppoint parent;
    private QuerryReservationBody querryReservationBody;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.layout_myappoint, container, false);
            parent = (MyAppoint) getParentFragment();
            binding.lv.setRefreshData(this);
            setNeedReloadData(true);
            initData();
            initView();
            initlisten();
        }
        return binding.getRoot();
    }


    @Override
    public void initData() {
        if (querryReservationBody == null) {
            Bundle arguments = getArguments();
            ArrayList<Integer> state = arguments.getIntegerArrayList("state");
            querryReservationBody = new QuerryReservationBody();
            querryReservationBody.setLimit(10);
            querryReservationBody.setStates(state);
        }
    }


    public void search(int condionType, String condition, String startTime, String endTime, boolean search) {
        if (querryReservationBody == null) {
            initData();
        }
        querryReservationBody.setConditionType(condionType);
        querryReservationBody.setCondition(condition);
        querryReservationBody.setStartRTime(startTime);
        querryReservationBody.setEndRTime(endTime);
        querryReservationBody.setPage(1);
        if (search) {
            querryReservation(querryReservationBody);
            refreshAppointNum();
        }

    }

    private View emptyView;
    public void showEmptyLayout() {
        if (data.size()== 0) {
            if (!binding.emptyLayout.isInflated()) {
                emptyView = binding.emptyLayout.getViewStub().inflate();
                EmptyLayoutBinding emptyLayoutBinding = DataBindingUtil.bind(emptyView);
                emptyLayoutBinding.setTip("暂无预约数据");
            } else {
                emptyView.setVisibility(View.VISIBLE);
            }
        } else {
            if (emptyView != null) {
                emptyView.setVisibility(View.GONE);
            }
        }
    }




    private List<AppointBean> data = new ArrayList<>();

    @Override
    public void initView() {
        UserBean userBean = UserOption.getInstance().querryUser();
        int userType = userBean.getUserType();
        appointAdapter = new AppointAdapter(getContext(), data, R.layout.list_myappoint_item, userType, new ClickSureListener() {
            @Override
            public void appointOption() {
                getAppointList(1);
                refreshAppointNum();
            }

            @Override
            public void deleteSuccess() {
                refreshAppointNum();

            }
        });
        binding.lv.setAdapter(appointAdapter);
    }

    private void refreshAppointNum() {
        GetReservationBody getReservationBody = new GetReservationBody();
        if (querryReservationBody.getConditionType() != 0) {
            getReservationBody.setCondition(querryReservationBody.getCondition());
            getReservationBody.setConditionType(querryReservationBody.getConditionType());
            getReservationBody.setEndRTime(querryReservationBody.getEndRTime());
            getReservationBody.setStartRTime(querryReservationBody.getStartRTime());
        }
        parent.getAppointNum(getReservationBody);
        binding.setCount(binding.getCount() - 1);
    }

    private void getAppointList(int page) {
        querryReservationBody.setPage(page);
        querryReservation(querryReservationBody);
    }

    private void querryReservation(QuerryReservationBody querryReservationBody) {
        HttpUtil.getInstance().querryReservation(querryReservationBody).subscribe(
                str -> {
                    if (querryReservationBody.getPage() == 1 && data.size() > 0) {
                        data.clear();
                        appointAdapter.clearView();
                    }
                    AppointList appointList = GsonUtil.fromJson(str, AppointList.class);
                    AppointList.DataBeanX datas = appointList.getData();
                    if (datas != null) {
                        List<AppointBean> list = datas.getData();
                        if (list != null && list.size() > 0) {
                            binding.lv.setLoading(true);
                            data.addAll(list);
                            appointAdapter.notifyDataSetChanged();
                        }
                        binding.setCount(data.size());
                        showEmptyLayout();
                    }
                }
        );
    }

    @Override
    public void initlisten() {
        binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyAppointDetail myAppointDetail = new MyAppointDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointBean", data.get(position));
                myAppointDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(myAppointDetail, "appointDetail");
            }
        });
    }


    @Override
    protected void loadData() {
        getAppointList(1);
        refreshAppointNum();
    }

    @Override
    public void refreshData(int page) {
        getAppointList(page);
    }
}
