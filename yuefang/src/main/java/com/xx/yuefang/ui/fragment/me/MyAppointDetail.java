package com.xx.yuefang.ui.fragment.me;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.AppointDetail;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.AppointOptionItem1Binding;
import com.xx.yuefang.databinding.AppointOptionItem2Binding;
import com.xx.yuefang.databinding.FragmentAppointDetailBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.HouseDetailContainer;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.widget.CancleAppointDialog;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class MyAppointDetail extends BaseFragment {
    private FragmentAppointDetailBinding binding;
    private AppointBean bean;
    private int userType;
    private AppointDetail.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserBean userBean = UserOption.getInstance().querryUser();
        userType = userBean.getUserType();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_appoint_detail, container, false);
        binding.setPresenter(Presenter.getInstance());
        initData();
        initView();
        initlisten();
        return binding.getRoot();
    }

    private void initAppointItem(AppointBean bean) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(bean.getAddress());
        appointItemView.setAveragePrice(bean.getAveragePrice() + "");
        appointItemView.setConstructionArea(bean.getConstructionArea());
        appointItemView.setPremisesName(bean.getPremisesName());
        appointItemView.setPropertyType(bean.getPropertyType());
        appointItemView.setRegion(bean.getRegion());
        appointItemView.setActive(bean.isIsActive());
        binding.setBean(appointItemView);
        String picture = bean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        binding.appointItem.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = bean.getCharacteristics();
        for (int i = 0; i < characteristics.size() + 1; i++) {
            if (i < 3) {
                TextView item;
                if (i == 0) {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character1, null);
                    item.setText(bean.getState());
                } else {
                    item = (TextView) View.inflate(getContext(), R.layout.layout_character2, null);
                    item.setText(characteristics.get(i - 1));
                }
                binding.appointItem.llexpend.addView(item, i);
            }
        }
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (AppointBean) bundle.getSerializable("appointBean");

            initAppointItem(bean);
            int id = bean.getId();
            HttpUtil.getInstance().getReservationDetailById(id).subscribe(
                    str -> {
                        AppointDetail appointDetail = GsonUtil.fromJson(str, AppointDetail.class);
                        data = appointDetail.getData();
                        if (data != null) {
                            binding.setAppointDetail(data);
                            int userCredit = data.getUserCredit();
                            Presenter.getInstance().initCreditLayout(binding.ll, userCredit);
                        }
                    }
            );
            String picture = bean.getPicture();
            String url = ImagUtil.handleUrl(picture);
            if (url != null) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                            binding.appointItem.iv.setBackground(drawable);
                        }
                );
            }
        }
    }

    @Override
    public void initView() {


        if (userType == 3 || userType == 4) {
            switch (bean.getReservationType()) {
                case 1://待接单
                    if (userType == 3) {
                        initOptionView1(4, "拒绝接单");
                        initOptionView2(3, "立即接单");
                    } else if (userType == 4) {
                        initOptionView1(2, "取消订单");
                    }
                    break;
                case 3://已接单
                    if (userType == 3) {
                        initOptionView1(5, "用户爽约");
                        initOptionView2(6, "完成订单");
                    } else if (userType == 4) {
                        initOptionView1(2, "取消订单");
                    }
                    break;
                case 2://用户已取消
                case 4://销售员已取消
                case 5://用户爽约
                    initOptionView2(0, "删除订单");
                    break;
                case 6://已完成
                case 7://已成交
                    initOptionView1(0, "删除订单");
                    initOptionView2(-1, "评价");
                    break;
            }
        }

    }


    private void initOptionView1(int option, String type) {
        AppointOptionItem1Binding item1Binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.appoint_option_item1, binding.optionContainer, false);
        item1Binding.setType(type);
        View view = item1Binding.getRoot();
        binding.optionContainer.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointOption(option);
            }
        });

    }

    private void initOptionView2(int option, String type) {
        AppointOptionItem2Binding item2Binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.appoint_option_item2, binding.optionContainer, false);
        item2Binding.setType(type);
        View view = item2Binding.getRoot();
        binding.optionContainer.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointOption(option);
            }
        });
    }


    public void appointOption(int option) {
        if (option == 0) {
            new TipDialog(getContext(), new ClickSureListener() {
                @Override
                public void clickSure() {
                    ArrayList<Integer> ids = new ArrayList<>();
                    ids.add(bean.getId());
                    HttpUtil.getInstance().deleteAppoint(ids).subscribe(
                            str -> {
                                Toast.makeText(getContext(), "预约删除成功", Toast.LENGTH_SHORT).show();
                                Presenter.getInstance().back();
                            }
                    );
                }
            }, "你确定要删除预约?").show();
        } else if (option == -1) {
            AppointCommentFirst commentDetailFragment = new AppointCommentFirst();
            Bundle bundle = new Bundle();
            bundle.putInt("id", bean.getId());
            commentDetailFragment.setArguments(bundle);
            Presenter.getInstance().step2fragment(commentDetailFragment, "commentDetail");
        } else {
            if (option == 2 || option == 4) {

                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        CancleAppointDialog cancleAppointDialog = new CancleAppointDialog(getContext(), new ClickSureListener() {
                            @Override
                            public void click(String reason) {
                                HttpUtil.getInstance().appointOption2(bean.getId(), option, reason).subscribe(
                                        str -> {
                                            //刷新ui
                                            binding.optionContainer.removeAllViews();
                                            bean.setReservationType(option);
                                            initView();
                                        }
                                );
                            }

                        });
                        cancleAppointDialog.show();
                    }
                }, "你确定要取消订单吗").show();
            } else {
                HttpUtil.getInstance().appointOption(bean.getId(), option).subscribe(
                        str -> {
                            if (option == 3 && userType == 3) {
                                LogUtil.log("============销售人员发消息==========");
                                Conversation conversation = Conversation.createSingleConversation(4 + "__" + bean.getUserId());
                                Message message = conversation.createSendTextMessage("你好，你的预约已接单 ");
                                message.setOnSendCompleteCallback(new BasicCallback() {
                                    @Override
                                    public void gotResult(int status, String desc) {
                                        if (status == 0) {
                                            LogUtil.log("=======AA===success===========");
                                        } else {
                                            // 消息发送失败
                                            LogUtil.log(status + "AAA============发送失败==============" + desc);
                                        }
                                    }
                                });
                                JMessageClient.sendMessage(message);
                            }
                            //刷新ui
                            binding.optionContainer.removeAllViews();
                            bean.setReservationType(option);
                            initView();
                        }
                );
            }


        }

    }


    @Override
    public void initlisten() {

        binding.appointItem.rlAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id",data.getPremisesBaseId());
                bundle.putBoolean("isFirst",true);
                bundle.putBoolean("initData",true);
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetailFragment");
            }
        });

    }











}
