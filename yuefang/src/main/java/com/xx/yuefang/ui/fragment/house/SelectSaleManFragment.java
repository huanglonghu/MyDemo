package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.Appoint;
import com.xx.yuefang.bean.SalePresonList;
import com.xx.yuefang.databinding.FragmentSelectSalemanBinding;
import com.xx.yuefang.databinding.SalemanListItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.SaleManListAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.AppointSuccessDialog;
import com.xx.yuefang.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectSaleManFragment extends BaseFragment {

    private FragmentSelectSalemanBinding binding;
    private List<SalePresonList.DataBean> data;
    private SaleManListAdapter saleManListAdapter;
    private int premisesId;
    private int type;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_saleman, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        premisesId = bundle.getInt("premisesId");
        type = bundle.getInt("type");
        binding.setType(type);
        HttpUtil.getInstance().getSalepresonListById(premisesId).subscribe(
                str -> {
                    SalePresonList salePresonList = GsonUtil.fromJson(str, SalePresonList.class);
                    if (salePresonList != null && salePresonList.getData() != null) {
                        List<SalePresonList.DataBean> list = salePresonList.getData();
                        if (list.size() > 0) {
                            data.clear();
                            saleManListAdapter.clearView();
                            data.addAll(list);
                            saleManListAdapter.notifyDataSetChanged();
                        } else {
                            TextView textView = new TextView(getContext());
                            textView.setText("暂无可咨询销售人员,先看看其他楼盘吧");
                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                            binding.container.addView(textView, layoutParams);
                        }
                    }
                }
        );
    }

    @Override
    public void initView() {
        data = new ArrayList<>();
        saleManListAdapter = new SaleManListAdapter(getActivity(), data, R.layout.saleman_list_item, new ClickSureListener() {
            @Override
            public void click(int position) {
                addAgent2Chat(position);
            }
        });
        binding.saleManLv.setAdapter(saleManListAdapter);
    }

    private int selectPosition = -1;

    private void addAgent2Chat(int position) {
        SalePresonList.DataBean dataBean = data.get(position);
        HttpUtil.getInstance().addAgent(3, premisesId, dataBean.getSalespersonId()).subscribe(
                str -> {
                    Chat chat = new Chat();
                    Bundle bundle = new Bundle();
                    bundle.putString("converationId", "3" + "__" + dataBean.getSalespersonId());
                    bundle.putString("converstaionName", dataBean.getBusinessCardName());
                    //删除当前界面
                    bundle.putBoolean("isSelectSalePerson", true);
                    bundle.putInt("premieseId", premisesId);
                    chat.setArguments(bundle);
                    Presenter.getInstance().step2fragment(chat, "chat");
                }
        );
    }

    @Override
    public void initlisten() {

        binding.saleManLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type == 1) {
                    if (selectPosition != -1) {
                        View lastSelectView = saleManListAdapter.getView(selectPosition, null, null);
                        SalemanListItemBinding itemBinding = DataBindingUtil.findBinding(lastSelectView);
                        itemBinding.item.setSelected(false);
                    }
                    SalemanListItemBinding itemBinding = DataBindingUtil.getBinding(view);
                    itemBinding.item.setSelected(true);
                    selectPosition = position;
                } else if (type == 2) {
                    addAgent2Chat(position);
                    SalePresonList.DataBean dataBean = data.get(position);
                    int salespersonId = dataBean.getSalespersonId();
                    EventData eventData = new EventData(UserObservable.TYPE_Bind_AGENT);
                    Bundle bundle = new Bundle();
                    bundle.putInt("salespersonId", salespersonId);
                    eventData.setData(bundle);
                    UserObservable.getInstance().notifyObservers(eventData);
                }
            }
        });


        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPosition == -1) {
                    Toast.makeText(getContext(), "请选择销售人员", Toast.LENGTH_SHORT).show();
                    return;
                }
                int salespersonId = data.get(selectPosition).getSalespersonId();
                HttpUtil.getInstance().addAgent(3, premisesId, salespersonId).subscribe(
                        str -> {
                            String appointStr = getArguments().getString("appoint");
                            Appoint appoint = GsonUtil.fromJson(appointStr, Appoint.class);
                            appoint.setSalespersonId(salespersonId);
                            HttpUtil.getInstance().appoint(appoint).subscribe(
                                    str2 -> {
                                        Presenter.getInstance().back();
                                        AppointSuccessDialog appointSuccessDialog = new AppointSuccessDialog(getContext());
                                        appointSuccessDialog.show();
                                    }
                            );
                        }
                );
            }
        });
    }

}
