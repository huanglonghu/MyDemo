package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.bean.EmployeeBean;
import com.xx.yuefang.databinding.ListMyemployeeItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.me.employee.EditParter;
import com.xx.yuefang.ui.fragment.me.employee.EditSalePerson;
import com.xx.yuefang.ui.widget.TipDialog;

import org.json.JSONObject;

import java.util.List;

public class MyEmployeeListAdapter extends BaseList2Adapter {

    private ClickSureListener clickSureListener;
    private int userType;

    public MyEmployeeListAdapter(Context context, List<EmployeeBean.DataBeanX.DataBean> datas, int res, int userType, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
        this.userType = userType;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent, View convertView) {
        ListMyemployeeItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), res, parent, false);
        convertView = binding.getRoot();
        EmployeeBean.DataBeanX.DataBean dataBean = (EmployeeBean.DataBeanX.DataBean) datas.get(position);
        binding.setBean(dataBean);
        if (userType == 5) {
            boolean del = dataBean.isDel();
            if (del) {
                binding.delete.setVisibility(View.VISIBLE);
            } else {
                binding.delete.setVisibility(View.GONE);
            }
        }
        binding.setUserType(userType);
        binding.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", dataBean);
                if (userType == 2) {
                    EditSalePerson editSalePerson = new EditSalePerson();
                    editSalePerson.setArguments(bundle);
                    Presenter.getInstance().step2fragment(editSalePerson, "editEmployee");
                } else if (userType == 5) {
                    EditParter editParter = new EditParter();
                    editParter.setArguments(bundle);
                    Presenter.getInstance().step2fragment(editParter, "editEmployee");
                }
            }
        });
        binding.setIsChecked(selectMap.get(position) != null);
        binding.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = binding.getIsChecked();
                binding.setIsChecked(!selected);
                if (!selected) {
                    selectMap.put(position, !selected);
                } else {
                    clickSureListener.select();
                    selectMap.remove(position);
                }
            }
        });
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TipDialog(context, new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        if (userType == 2) {
                              HttpUtil.getInstance().deleteSaleperson(dataBean.getId()).subscribe(
                                      str -> {
                                          Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                          datas.remove(position);
                                          removeByPosition(position);
                                          notifyDataSetChanged();
                                      }
                              );
                        }else if(userType==5){
                            HttpUtil.getInstance().deleteParter(dataBean.getId()).subscribe(
                                    str -> {
                                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                        datas.remove(position);
                                        removeByPosition(position);
                                        notifyDataSetChanged();
                                    }
                            );
                        }

                    }
                }, "您确定要删除该员工吗").show();

            }
        });
        binding.unBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(context, new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        HttpUtil.getInstance().unBindCompany(dataBean.getId()).subscribe(
                                str -> {
                                    JSONObject jsonObject = new JSONObject(str);
                                    String message = jsonObject.getString("Message");
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                    datas.remove(position);
                                    removeByPosition(position);
                                    notifyDataSetChanged();
                                }
                        );
                    }
                }, "您确定要解绑该员工吗").show();

            }
        });
        return convertView;
    }


}
