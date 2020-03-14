package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AgentList;
import com.xx.yuefang.bean.AppointItemView;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.AppointItemBinding;
import com.xx.yuefang.databinding.ItemAgentProfileBinding;
import com.xx.yuefang.databinding.ListAgentItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.ui.widget.TipDialog2;
import com.xx.yuefang.ui.widget.UnBindAgentDialog;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.List;

public class AgentListAdapter extends BaseListAdapter {

    private ClickSureListener clickSureListener;

    public AgentListAdapter(Context context, List datas, int res, ClickSureListener clickSureListener) {
        super(context, datas, res);
        this.clickSureListener = clickSureListener;
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {

        ListAgentItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_agent_item, parent, false);
        AgentList.DataBean dataBean = (AgentList.DataBean) datas.get(position);
        binding.setDataBean(dataBean);
        int leadSee = dataBean.getLeadSee();
        int turnover = dataBean.getTurnover();
        String str1 = "本盘带看" + leadSee + "次|";
        String str2 = "总成交" + turnover + "套";
        binding.dk.setText(str1);
        binding.cj.setText(str2);
        String avatar = dataBean.getAvatar();
        String gradeType = dataBean.getGradeType();
        binding.gradeType.setText(gradeType);

        switch (gradeType) {
            case "金牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_f);
                break;
            case "银牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_g);
                break;
            case "铜牌":
                binding.gradeType.setBackgroundResource(R.drawable.shape_e);
                break;
            default:
                binding.gradeType.setVisibility(View.INVISIBLE);
                break;
        }
        String url = ImagUtil.handleUrl(avatar);
        Glide.with(context).load(url).centerCrop().apply(RequestOptions.bitmapTransform(new CircleCrop())).apply(new RequestOptions().error(R.drawable.default_chat_head)).into(binding.ivHead);
        if (dataBean.getUserType() == 6) {
            if (dataBean.getCompanyId() != 0) {
                if (dataBean.getExamineState() == 0 || dataBean.getExamineState() == 3) {
                    binding.cancle.setText("申请解绑");
                }
            } else {
                binding.cancle.setText("取消绑定");
            }
        } else if (dataBean.getUserType() == 3) {
            binding.cancle.setText("申请解绑");
        }

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dataBean.getPhoneNumber()));//跳转到拨号界面
                context.startActivity(intent);
            }
        });

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat();
                Bundle bundle = new Bundle();
                bundle.putString("converationId", dataBean.getUserType() + "__" + dataBean.getSalespersonId());
                bundle.putString("converstaionName", dataBean.getBusinessCardName());
                bundle.putInt("premieseId", dataBean.getId());
                chat.setArguments(bundle);
                Presenter.getInstance().step2fragment(chat, "chat");
            }
        });
        int userType = dataBean.getUserType();
        int companyId = dataBean.getCompanyId();
        int examineState = dataBean.getExamineState();
        if (examineState == 1) {
            binding.cancle.setText("解绑审核中");
        } else if (examineState == 4) {
            binding.cancle.setText("禁止解绑");
            binding.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new TipDialog2(context, "拒绝理由：" + dataBean.getRefuseReason()).show();
                }
            });
        } else {
            binding.cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  0,3 申请解绑 1 解绑审核中 4 禁止解绑
                    String tip;
                    if (userType == 6 && companyId == 0) {
                        tip = "您确定要解除绑定?";
                    } else {
                        tip = "您确定要申请解绑?";
                    }
                    new TipDialog(context, new ClickSureListener() {
                        @Override
                        public void clickSure() {
                            if (userType == 6 && companyId == 0) {
                                deleteParter(position, null, dataBean.getSalespersonId());
                            } else {
                                new UnBindAgentDialog(context, new ClickSureListener() {
                                    @Override
                                    public void click(String reason) {
                                        int userType = dataBean.getUserType();
                                        if (userType == 3) {
                                            int myBrokerId = dataBean.getMyBrokerId();
                                            HttpUtil.getInstance().deleteAgent(myBrokerId, reason).subscribe(
                                                    str -> {
                                                        clickSureListener.click(datas.size());
                                                    }
                                            );
                                        } else if (userType == 6) {
                                            int salespersonId = dataBean.getSalespersonId();
                                            deleteParter(position, reason, salespersonId);
                                        }

                                    }
                                }).show();
                            }
                        }
                    }, tip).show();
                }
            });
        }


        if (companyId == 0) {
            AppointItemBinding appointItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.appoint_item, binding.container, false);
            initAppointItem(binding, dataBean, appointItemBinding);
        } else {
            ItemAgentProfileBinding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_agent_profile, binding.container, false);
            String companyAvatar = dataBean.getCompanyAvatar();
            String url1 = ImagUtil.handleUrl(companyAvatar);
            if (!TextUtils.isEmpty(url1)) {
                RxImageLoader.with(context).getBitmap(url1).subscribe(
                        imageBean -> {
                            Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                            itemBinding.iv.setBackground(drawable);
                        }
                );
            }
            itemBinding.profile.setText(dataBean.getIntroduce());
            if (TextUtils.isEmpty(dataBean.getIntroduce())) {
                itemBinding.profile.setTextSize(20);
            }
            binding.container.addView(itemBinding.getRoot());
        }
        View view = binding.getRoot();
        return view;
    }


    private void deleteParter(int position, String reason, int salepersonId) {
        HttpUtil.getInstance().deleteAgentofPart(salepersonId, 6, reason).subscribe(
                str -> {
                    EventData eventData = new EventData(UserObservable.TYPE_DELETE_AGENT);
                    Bundle bundle = new Bundle();
                    bundle.putString("conversationName", 6 + "__" + salepersonId);
                    eventData.setData(bundle);
                    UserObservable.getInstance().notifyObservers(eventData);
                    datas.remove(position);
                    getViewMap().remove(position);
                    clickSureListener.click(datas.size());
                    notifyDataSetChanged();
                }
        );
    }


    private void initAppointItem(ListAgentItemBinding binding, AgentList.DataBean bean, AppointItemBinding itemBinding) {
        AppointItemView appointItemView = new AppointItemView();
        appointItemView.setAddress(bean.getAddress());
        appointItemView.setAveragePrice(bean.getAveragePrice() + "");
        appointItemView.setConstructionArea(bean.getConstructionArea());
        appointItemView.setPremisesName(bean.getPremisesName());
        appointItemView.setPropertyType(bean.getPropertyType());
        appointItemView.setRegion(bean.getRegion());
        appointItemView.setId(bean.getId());
        itemBinding.setBean(appointItemView);
        String picture = bean.getPicture();
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                        itemBinding.iv.setBackground(drawable);
                    }
            );
        }
        List<String> characteristics = bean.getCharacteristics();
        if (characteristics != null && characteristics.size() > 0) {
            for (int i = 0; i < characteristics.size() + 1; i++) {
                if (i < 3) {
                    TextView item;
                    if (i == 0) {
                        item = (TextView) View.inflate(context, R.layout.layout_character1, null);
                        item.setText(bean.getState());
                    } else {
                        item = (TextView) View.inflate(context, R.layout.layout_character2, null);
                        item.setText(characteristics.get(i - 1));
                    }
                    itemBinding.llexpend.addView(item, i);
                }
            }
        }
        binding.container.addView(itemBinding.getRoot());

    }


}
