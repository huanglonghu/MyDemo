package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.QuerryReviewBody;
import com.xx.yuefang.bean.ReViewResponse;
import com.xx.yuefang.databinding.LayoutMyappointBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.ReViewListAdapter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.customview.ScrollRefreshListView;
import com.xx.yuefang.util.GsonUtil;
import java.util.ArrayList;
import java.util.List;

public class ReviewPage extends LazyLoadFragment implements ScrollRefreshListView.RefreshData, Review.SwitchChangeListener {
    private LayoutMyappointBinding binding;
    private Review parent;
    private QuerryReviewBody querryReviewBody;
    private ReViewListAdapter reViewListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.layout_myappoint, container, false);
            parent = (Review) getParentFragment();
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
        Bundle arguments = getArguments();
        querryReviewBody = new QuerryReviewBody();
        querryReviewBody.setLimit(10);
        querryReviewBody.setPage(1);
        ArrayList<Integer> state = arguments.getIntegerArrayList("state");
        ArrayList<Integer> applyTypes = arguments.getIntegerArrayList("applyTypes");
        querryReviewBody.setApplyType(applyTypes);
        querryReviewBody.setStates(state);
    }

    private List<ReViewResponse.DataBeanX.DataBean> datas;

    @Override
    public void initView() {
        datas = new ArrayList<>();
        reViewListAdapter = new ReViewListAdapter(getContext(), datas, 0);
        binding.lv.setAdapter(reViewListAdapter);
    }

    private void getReviewList(int page) {
        querryReviewBody.setPage(page);
        querryReservation(querryReviewBody);
    }

    private void querryReservation(QuerryReviewBody querryReviewBody) {

        HttpUtil.getInstance().querryReviews(querryReviewBody).subscribe(
                str -> {
                    ReViewResponse reViewResponse = GsonUtil.fromJson(str, ReViewResponse.class);
                    ReViewResponse.DataBeanX data = reViewResponse.getData();
                    if (querryReviewBody.getPage() == 1 && datas.size() > 0) {
                        datas.clear();
                        reViewListAdapter.clearView();
                    }
                    if (data != null) {
                        List<ReViewResponse.DataBeanX.DataBean> list = data.getData();
                        if (list != null && list.size() > 0) {
                            binding.lv.setLoading(true);
                            datas.addAll(list);
                            binding.setCount(datas.size());
                        } else {
                            binding.setCount(datas.size());
                        }
                        reViewListAdapter.notifyDataSetChanged();
                    }
                }

        );
    }

    @Override
    public void initlisten() {
        binding.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                ReViewDetail reViewDetail = new ReViewDetail();
                ReViewResponse.DataBeanX.DataBean dataBean = datas.get(position);
                int id = dataBean.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                reViewDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(reViewDetail, "reviewDetail");
            }
        });
    }


    @Override
    protected void loadData() {
        getReviewList(1);
    }

    @Override
    public void refreshData(int page) {
        getReviewList(page);
    }

    @Override
    public void switchChange(boolean isSwitched,boolean refreshData) {
        //1.解绑经纪人2.绑定公司3解绑公司
        if (isSwitched) {
            ArrayList<Integer> applyTypes = new ArrayList<>();
            applyTypes.add(1);
            applyTypes.add(3);
            querryReviewBody.setApplyType(applyTypes);
        } else {
            ArrayList<Integer> applyTypes = new ArrayList<>();
            applyTypes.add(2);
            querryReviewBody.setApplyType(applyTypes);
        }
        if(refreshData){
            querryReviewBody.setPage(1);
            parent.getReviewNum();
            querryReservation(querryReviewBody);
        }
    }


}
