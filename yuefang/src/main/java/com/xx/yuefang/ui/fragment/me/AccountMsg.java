package com.xx.yuefang.ui.fragment.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AccountBean;
import com.xx.yuefang.bean.UploadPicture;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentAccountBinding;
import com.xx.yuefang.databinding.LayoutAccountmsgBinding;
import com.xx.yuefang.handler.ActivityResultHandler;
import com.xx.yuefang.handler.UpdateAccountMsg;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.AdClickListener;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.strategy.HandlerStrategy;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.login.BackPswFragment;
import com.xx.yuefang.ui.widget.QrcodeWindow;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.ui.widget.TypeSelect;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.ThridPartLogin;
import org.json.JSONObject;
import java.util.HashMap;
import okhttp3.MultipartBody;

public class AccountMsg extends BaseFragment implements EditAccountMsg.EditAccountCallBack {

    private FragmentAccountBinding binding;
    private UserBean userBean;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
            binding.setPresenter(Presenter.getInstance());
            binding.setFragment(this);
            initData();
            initView();
            initlisten();
            ThridPartLogin.getInstance().initParam(this, new ClickSureListener() {
                @Override
                public void openIdCallBack(int loginType, String opendId) {
                    HttpUtil.getInstance().bindThird(loginType, opendId).subscribe(
                            str -> {
                                Toast.makeText(getContext(), "绑定成功", Toast.LENGTH_SHORT).show();
                                switch (loginType) {
                                    case 1:
                                        userBean.setIsbindWeChat(true);
                                        break;
                                    case 2:
                                        userBean.setIsbindQQ(true);
                                        break;
                                    case 3:
                                        userBean.setIsbindFacebook(true);
                                        break;
                                }
                                UserOption.getInstance().updateUser(userBean);
                                binding.setUser(userBean);
                            }
                    );
                }
            });
            binding.setUser(userBean);
        }
        return binding.getRoot();
    }


    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public void initData() {
    }

    private HashMap<AccountBean, LayoutAccountmsgBinding> accountMap = new HashMap<>();

    private void initModule() {
        String[] keyArray = null;
        String[] nameArray = null;
        String[] valueArray = null;
        switch (userBean.getUserType()) {
            case 2:
                nameArray = new String[]{"公司名称", "手机号码", "物业介绍", "预约天数"};
                keyArray = new String[]{"Name", "PhoneNumber", "Introduce", "ReservationDays"};
                valueArray = new String[]{userBean.getCommanyName(), userBean.getPhoneNumber(), userBean.getIntroduce(), userBean.getReservationDays() + ""};
                break;
            case 5:
                nameArray = new String[]{"公司名称", "手机号码", "物业介绍"};
                keyArray = new String[]{"Name", "PhoneNumber", "Introduce"};
                valueArray = new String[]{userBean.getCommanyName(), userBean.getPhoneNumber(), userBean.getIntroduce()};
                break;
            case 3:
            case 6:
                nameArray = new String[]{"名片名称", "性别", "手机号码", "微信", "地址", "简介","电话"};
                keyArray = new String[]{"Name", "Sex", "PhoneNumber", "Email", "Address", "Profile","Telephone"};
                valueArray = new String[]{userBean.getBusinessCardName(), userBean.getSex(), userBean.getPhoneNumber(), userBean.getEmail(), userBean.getAddress(), userBean.getProfile(),userBean.getTelephone()};
                break;
            case 4:
                nameArray = new String[]{"昵称", "手机号码", "性别"};
                keyArray = new String[]{"Name", "PhoneNumber", "Sex"};
                valueArray = new String[]{userBean.getNickName(), userBean.getPhoneNumber(), userBean.getSex()};
                break;
        }
        for (int i = 0; i < keyArray.length; i++) {
            LayoutAccountmsgBinding accountmsgBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_accountmsg, binding.llContainer, false);
            accountmsgBinding.setFragment(this);
            AccountBean accountBean = new AccountBean();
            accountBean.setKey(keyArray[i]);
            accountBean.setName(nameArray[i]);
            accountBean.setValue(valueArray[i]);
            accountmsgBinding.setAccountBean(accountBean);
            accountMap.put(accountBean, accountmsgBinding);
            binding.llContainer.addView(accountmsgBinding.getRoot());
        }
    }


    @Override
    public void initView() {
        String avatar = userBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(getContext()).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Drawable drawable = ImagUtil.circle(resource);
                    binding.ivHead.setBackground(drawable);
                }
            });
        }
        initModule();
    }

    @Override
    public void initlisten() {
        binding.changePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().isPwdExit().subscribe(
                        str -> {
                            JSONObject jb = new JSONObject(str);
                            boolean isPwdExit = jb.getBoolean("Data");
                            if (isPwdExit) {
                                ChangePswFragment changePswFragment = new ChangePswFragment();
                                Presenter.getInstance().step2fragment(changePswFragment,"changePsw");
                            } else {
                                BackPswFragment backPswFragment = new BackPswFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("type",2);
                                backPswFragment.setArguments(bundle);
                                Presenter.getInstance().step2fragment(backPswFragment, "backPwd");
                            }
                        }
                );
            }
        });

        binding.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @Override
                    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
                        Drawable drawable = ImagUtil.circle(bitmap);
                        binding.ivHead.setImageDrawable(drawable);
                        HttpUtil.getInstance().upload(filePart, userBean.getAvatar()).subscribe(
                                str -> {
                                    UploadPicture uploadPicture = GsonUtil.fromJson(str, UploadPicture.class);
                                    String url = uploadPicture.getData();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("Id",userBean.getId());
                                    map.put("Avatar",url);
                                    new UpdateAccountMsg.Builder().map(map).key("Avatar").value(url).completeStrategy(new ClickSureListener() {
                                        @Override
                                        public void click(String str) {
                                            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_PHOTO));
                                        }
                                    }).build().update();
                                }
                        );
                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO_CROP).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();

            }
        });
        binding.wxgzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userBean.getIsbindWeChat()) {
                    HttpUtil.getInstance().isConcernWxgzh().subscribe(
                            str -> {
                                JSONObject jsonObject = new JSONObject(str);
                                boolean data = jsonObject.getBoolean("Data");
                                if (data) {
                                    Toast.makeText(getContext(), "您已绑定了公众号", Toast.LENGTH_SHORT).show();
                                } else {
                                    //展示二维码
                                    QrcodeWindow qrcodeWindow = new QrcodeWindow(getContext(), (ViewGroup) binding.getRoot());
                                    qrcodeWindow.show();
                                }
                            }
                    );
                }
            }
        });
    }


    public void editAccount(AccountBean accountBean) {
        if (accountBean.getName().equals("性别")) {
            String[] sexArray = {"男", "女"};
            TypeSelect typeSelect = new TypeSelect(getContext(), sexArray, new AdClickListener() {
                @Override
                public void click(int position) {
                    String sex = sexArray[position];
                    if (!sex.equals(binding.getSex())) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("Id",userBean.getId());
                        map.put("Sex",sex.equals("男"));
                        new UpdateAccountMsg.Builder().map(map).completeStrategy(new ClickSureListener() {
                            @Override
                            public void click(String str) {
                                accountBean.setValue(sex);
                                LayoutAccountmsgBinding layoutAccountmsgBinding = accountMap.get(accountBean);
                                layoutAccountmsgBinding.setAccountBean(accountBean);
                            }
                        }).key("Sex").value(sex).build().update();
                    }
                }
            });
            typeSelect.show();
        } else {
            EditAccountMsg editAccountMsg = new EditAccountMsg();
            Bundle bundle = new Bundle();
            bundle.putSerializable("accountBean", accountBean);
            editAccountMsg.setEditAccountCallBack(this);
            editAccountMsg.setArguments(bundle);
            Presenter.getInstance().step2fragment(editAccountMsg, "editAccount");
        }
    }


    public void bindOption(int type, boolean isBind) {
        if (isBind) {
            new TipDialog(getContext(), new ClickSureListener() {
                @Override
                public void clickSure() {
                    HttpUtil.getInstance().unBindThird(type).subscribe(
                            str -> {
                                //更新ui和数据
                                Toast.makeText(getContext(), "解绑成功", Toast.LENGTH_SHORT).show();
                                switch (type) {
                                    case 1:
                                        userBean.setIsbindWeChat(false);
                                        break;
                                    case 2:
                                        userBean.setIsbindQQ(false);
                                        break;
                                    case 3:
                                        userBean.setIsbindFacebook(false);
                                        break;
                                }
                                UserOption.getInstance().updateUser(userBean);
                                binding.setUser(userBean);
                            }
                    );
                }
            }, "您确定要解除绑定吗?").show();

        } else {
            //去获取openId
            switch (type) {
                case 1:
                    ThridPartLogin.getInstance().wxdl("getOpenId");
                    break;
                case 2:
                    ThridPartLogin.getInstance().qqLogin();
                    break;
                case 3:
                    ThridPartLogin.getInstance().facebookLogin();
                    break;
            }

        }

    }


    @Override
    public void editCompleted(AccountBean accountBean) {
        LayoutAccountmsgBinding layoutAccountmsgBinding = accountMap.get(accountBean);
        layoutAccountmsgBinding.setAccountBean(accountBean);
    }
}
