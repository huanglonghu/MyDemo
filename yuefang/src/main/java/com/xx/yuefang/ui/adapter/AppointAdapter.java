package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.AppointOptionItem1Binding;
import com.xx.yuefang.databinding.AppointOptionItem2Binding;
import com.xx.yuefang.databinding.ListMyappointItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.me.AppointCommentFirst;
import com.xx.yuefang.ui.widget.CancleAppointDialog;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.ImagUtil;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

public class AppointAdapter extends BaseListAdapter {
    private int userType;
    private ClickSureListener clickSureListener;

    public AppointAdapter(Context context, List<AppointBean> datas, int res, int userType, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.userType = userType;
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        AppointBean bean = (AppointBean) datas.get(position);
        ListMyappointItemBinding binding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        binding.setUserType(userType);
        binding.setAdapter(this);
        initView(position, binding);
        initAppointItem(binding, bean);
        return binding.getRoot();
    }

    private void initAppointItem(ListMyappointItemBinding binding, AppointBean appointBean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(appointBean.getAddress());
        appointItemView.setAveragePrice(appointBean.getAveragePrice() + "");
        appointItemView.setConstructionArea(appointBean.getConstructionArea());
        appointItemView.setPremisesName(appointBean.getPremisesName());
        appointItemView.setPropertyType(appointBean.getPropertyType());
        appointItemView.setRegion(appointBean.getRegion());
        appointItemView.setReservationState(appointBean.getReservationState());
        appointItemView.setDate(appointBean.getReservationTime());
        binding.setBean(appointItemView);
        String picture = appointBean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = appointBean.getCharacteristics();
        for (int i = 0; i < characteristics.size() + 1; i++) {
            if (i < 3) {
                TextView item;
                if (i == 0) {
                    item = (TextView) View.inflate(context, R.layout.layout_character1, null);
                    item.setText(appointBean.getState());
                } else {
                    item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                    item.setText(characteristics.get(i - 1));
                }
                binding.appointItem.llexpend.addView(item, i);
            }
        }
    }


    public void initView(int position, ListMyappointItemBinding binding) {
        binding.optionContainer.removeAllViews();
        AppointBean bean = (AppointBean) datas.get(position);
        // 1待接单 3已接单 6已完成 2用户取消 4销售取消 5用户爽约 6已接待 7成交8确认成交
        if (userType != 2) {
            switch (bean.getReservationType()) {
                case 1://待接单
                    if (userType == 3||userType==6) {
                        initOptionView1(binding, 4, "拒绝接单", position);
                        initOptionView2(binding, 3, "立即接单", position);
                    } else if (userType == 4) {
                        initOptionView1(binding, 2, "取消订单", position);
                    }
                    break;
                case 2:
                    initOptionView1(binding, 0, "删除订单", position);
                    break;
                case 3://已接单
                    if (userType == 3||userType==6) {
                        initOptionView1(binding, 5, "用户爽约", position);
                        initOptionView2(binding, 6, "已接待", position);
                    } else if (userType == 4) {
                        initOptionView1(binding, 2, "取消订单", position);
                    }
                    break;
                case 4:
                    initOptionView1(binding, 0, "删除订单", position);
                    break;
                case 5:
                    initOptionView1(binding, 0, "删除订单", position);
                    break;
                case 6:
                    if (userType == 3||userType==6) {
                        initOptionView2(binding, 7, "成交", position);
                    }
                    initOptionView1(binding, 0, "删除订单", position);
                    initOptionView3(binding, bean);
                    break;
                case 7:
                    if (userType == 4) {
                        initOptionView2(binding, 8, "确认成交", position);
                    }
                    initOptionView1(binding, 0, "删除订单", position);
                    initOptionView3(binding, bean);
                    break;
                case 8:
                    initOptionView1(binding, 0, "删除订单", position);
                    initOptionView3(binding, bean);
                    break;
            }
        }
    }


    private void initOptionView1(ListMyappointItemBinding binding, int option, String type, int position) {
        AppointOptionItem1Binding item1Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.appoint_option_item1, binding.optionContainer, false);
        item1Binding.setType(type);
        View view = item1Binding.getRoot();
        binding.optionContainer.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointOption(position, option);
            }
        });
    }

    private void initOptionView2(ListMyappointItemBinding binding, int option, String type, int position) {
        AppointOptionItem2Binding item2Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.appoint_option_item2, binding.optionContainer, false);
        item2Binding.setType(type);
        View view = item2Binding.getRoot();
        binding.optionContainer.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointOption(position, option);
            }
        });
    }

    private void initOptionView3(ListMyappointItemBinding binding, AppointBean bean) {
        AppointOptionItem2Binding item2Binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.appoint_option_item2, binding.optionContainer, false);
        item2Binding.setType("评价");
        View view = item2Binding.getRoot();
        binding.optionContainer.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointCommentFirst commentDetailFragment = new AppointCommentFirst();
                Bundle bundle = new Bundle();
                bundle.putInt("id", bean.getId());
                commentDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(commentDetailFragment, "commentDetail");
            }
        });
    }


    public void appointOption(int position, int option) {
        AppointBean bean = (AppointBean) datas.get(position);
        if (option == 2 || option == 4) {
            CancleAppointDialog cancleAppointDialog = new CancleAppointDialog(context, new ClickSureListener() {
                @Override
                public void click(String reason) {
                    HttpUtil.getInstance().appointOption2(bean.getId(), option, reason).subscribe(
                            str -> {
                                //刷新ui
                                datas.remove(position);
                                getViewMap().remove(position);
                                clickSureListener.deleteSuccess();
                                Toast.makeText(context, "预约取消成功", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                    );
                }
            });
            cancleAppointDialog.show();
        } else if (option == 0) {
            new TipDialog(context, new ClickSureListener() {
                @Override
                public void clickSure() {
                    int id = bean.getId();
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(id);
                    HttpUtil.getInstance().deleteAppoint(ids).subscribe(
                            str -> {
                                datas.remove(position);
                                getViewMap().remove(position);
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                clickSureListener.deleteSuccess();
                                notifyDataSetChanged();
                            }
                    );
                }
            }, "您确定要删除订单?").show();

        } else if (option == 6) {
            new TipDialog(context, new ClickSureListener() {
                @Override
                public void clickSure() {
                    HttpUtil.getInstance().appointOption(bean.getId(), option).subscribe(
                            str -> {
                                clickSureListener.appointOption();
                            }
                    );
                }
            }, "请确认已接待该房源").show();
        } else if (option == 7) {
            new TipDialog(context, new ClickSureListener() {
                @Override
                public void clickSure() {
                    HttpUtil.getInstance().appointOption(bean.getId(), option).subscribe(
                            str -> {
                                clickSureListener.appointOption();
                            }
                    );
                }
            }, "请确认已成交该房源").show();

        } else {
            HttpUtil.getInstance().appointOption(bean.getId(), option).subscribe(
                    str -> {
                        if (option == 3) {
                            if(userType == 3||userType==6){
                                Conversation conversation = Conversation.createSingleConversation(4 + "__" + bean.getUserId());
                                Message message = conversation.createSendTextMessage("你好，你的预约已接单 ");
                                JMessageClient.sendMessage(message);
                            }
                        }
                        clickSureListener.appointOption();
                    }
            );
        }
    }
}
