package com.xx.yuefang.ui.fragment.main;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.AppointBean;
import com.xx.yuefang.bean.Company;
import com.xx.yuefang.bean.Developer;
import com.xx.yuefang.bean.Member;
import com.xx.yuefang.bean.Parter;
import com.xx.yuefang.bean.Salesperson;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.EmptyLayout2Binding;
import com.xx.yuefang.databinding.FragmentMeBinding;
import com.xx.yuefang.databinding.LayoutDeveloperMsgBinding;
import com.xx.yuefang.databinding.LayoutFirstAppointBinding;
import com.xx.yuefang.databinding.LlContainerItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.LazyLoadFragment;
import com.xx.yuefang.ui.fragment.MainFragment;
import com.xx.yuefang.ui.fragment.me.AccountMsg;
import com.xx.yuefang.ui.fragment.me.MyAppoint;
import com.xx.yuefang.ui.fragment.me.Setting;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.ResUtils;

import java.util.List;

public class MeFragment extends LazyLoadFragment {
    private FragmentMeBinding binding;
    private AppointBean data;
    private MainFragment main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            setNeedReloadData(true);
            main = (MainFragment) getParentFragment();
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, null, false);
            binding.setPresenter(Presenter.getInstance());
            initlisten();
        }
        return binding.getRoot();
    }


    @Override
    public void initData() {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean == null) {
            Presenter.getInstance().step2Fragment("login");
        } else {
            getFirstYuyue();
            switch (userBean.getUserType()) {
                case 2://发展商
                    HttpUtil.getInstance().getDeveloperMsgById(userBean.getId()).subscribe(
                            str -> {
                                Developer developer = GsonUtil.fromJson(str, Developer.class);
                                Developer.DataBean data = developer.getData();
                                userBean.setDeveloperId(data.getId());
                                userBean.setCommanyName(data.getCompanyName());
                                userBean.setRoleName(data.getRoleName());
                                userBean.setAvatar(data.getAvatar());
                                userBean.setPhoneNumber(data.getPhoneNumber());
                                userBean.setRoleId(data.getRoleId());
                                userBean.setIntroduce(data.getIntroduce());
                                userBean.setReservationDays(data.getReservationDays());
                                userBean.setIsbindWeChat(data.isIsbindWeChat());
                                userBean.setIsbindQQ(data.isIsbindQQ());
                                userBean.setIsbindFacebook(data.isIsbindFacebook());
                                initLoginView(userBean);
                            }
                    );
                    break;
                case 3://销售
                    HttpUtil.getInstance().getSellerMsgById(userBean.getId()).subscribe(
                            str -> {
                                Salesperson salesperson = GsonUtil.fromJson(str, Salesperson.class);
                                Salesperson.DataBean data = salesperson.getData();
                                userBean.setCommanyName(data.getCompanyName());
                                userBean.setDeveloperId(data.getDeveloperId());
                                userBean.setPhoneNumber(data.getPhoneNumber());
                                userBean.setAvatar(data.getAvatar());
                                userBean.setScore(data.getScore());
                                userBean.setSex(data.isSex() ? "男" : "女");
                                userBean.setBusinessCardName(data.getBusinessCardName());
                                userBean.setEmail(data.getEmail());
                                userBean.setAddress(data.getAddress());
                                userBean.setIsbindWeChat(data.isIsbindWeChat());
                                userBean.setIsbindQQ(data.isIsbindQQ());
                                userBean.setIsbindFacebook(data.isIsbindFacebook());
                                initLoginView(userBean);
                            }
                    );
                    break;
                case 4://个人会员
                    HttpUtil.getInstance().getUserMsg(userBean.getId()).subscribe(
                            str -> {
                                Member member = GsonUtil.fromJson(str, Member.class);
                                Member.DataBean data = member.getData();
                                userBean.setPhoneNumber(data.getPhoneNumber());
                                userBean.setAvatar(data.getAvatar());
                                userBean.setCredit(data.getCredit());
                                userBean.setScore(data.getScore());
                                userBean.setSex(data.isSex() ? "男" : "女");
                                userBean.setIsbindWeChat(data.isIsbindWeChat());
                                userBean.setIsbindQQ(data.isIsbindQQ());
                                userBean.setIsbindFacebook(data.isIsbindFacebook());
                                initLoginView(userBean);
                            }
                    );
                    break;
                case 5:
                    HttpUtil.getInstance().getCompanyById().subscribe(
                            str -> {
                                Company company = GsonUtil.fromJson(str, Company.class);
                                Company.DataBean data = company.getData();
                                userBean.setDeveloperId(data.getId());
                                userBean.setAvatar(data.getAvatar());
                                userBean.setPhoneNumber(data.getPhoneNumber());
                                userBean.setCommanyName(data.getCompanyName());
                                userBean.setIntroduce(data.getIntroduce());
                                userBean.setIsbindWeChat(data.isIsbindWeChat());
                                userBean.setIsbindQQ(data.isIsbindQQ());
                                userBean.setIsbindFacebook(data.isIsbindFacebook());
                                initLoginView(userBean);
                            }
                    );


                    break;
                case 6:
                    HttpUtil.getInstance().getParterById().subscribe(
                            str -> {
                                Parter parter = GsonUtil.fromJson(str, Parter.class);
                                Parter.DataBean data = parter.getData();
                                userBean.setCommanyName(data.getCompanyName());
                                userBean.setDeveloperId(data.getDeveloperId());
                                userBean.setPhoneNumber(data.getPhoneNumber());
                                userBean.setAvatar(data.getAvatar());
                                userBean.setScore(data.getScore());
                                userBean.setBusinessCardName(data.getBusinessCardName());
                                userBean.setSex(data.isSex() ? "男" : "女");
                                userBean.setEmail(data.getEmail());
                                userBean.setAddress(data.getAddress());
                                userBean.setIsbindWeChat(data.isIsbindWeChat());
                                userBean.setIsbindQQ(data.isIsbindQQ());
                                userBean.setProfile(data.getProfile());
                                userBean.setIsbindFacebook(data.isIsbindFacebook());
                                userBean.setTelephone(data.getTelephone());
                                initLoginView(userBean);
                            }
                    );
                    break;
            }
        }
    }

    private void getFirstYuyue() {
        HttpUtil.getInstance().getFirstYuyue().subscribe(
                str -> {
                    if (binding.firstAppointCotainer.getChildCount() > 0) {
                        binding.firstAppointCotainer.removeAllViews();
                    }
                    com.xx.yuefang.bean.MyAppoint myAppoint = GsonUtil.fromJson(str, com.xx.yuefang.bean.MyAppoint.class);
                    data = myAppoint.getData();
                    if (data != null) {
                        LayoutFirstAppointBinding firstAppointBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_first_appoint, binding.firstAppointCotainer, false);
                        firstAppointBinding.setMyAppoint(data);
                        String picture = data.getPicture();
                        List<String> characteristics = data.getCharacteristics();
                        firstAppointBinding.llexpend.removeAllViews();
                        for (int i = 0; i < characteristics.size() + 1; i++) {
                            if (i < 3) {
                                TextView item;
                                if (i == 0) {
                                    item = (TextView) View.inflate(getContext(), R.layout.layout_character1, null);
                                    item.setText(data.getState());
                                } else {
                                    item = (TextView) View.inflate(getContext(), R.layout.layout_character2, null);
                                    item.setText(characteristics.get(i - 1));
                                }
                                firstAppointBinding.llexpend.addView(item, i);
                            }
                        }
                        String url = ImagUtil.handleUrl(picture);
                        if (!TextUtils.isEmpty(url)) {
                            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                                    imageBean -> {
                                        Drawable drawable = ImagUtil.conner(imageBean.getBitmap());
                                        firstAppointBinding.ivAppoint.setBackground(drawable);
                                    }
                            );
                        }
                        View view = firstAppointBinding.getRoot();
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int reservationType = data.getReservationType();
                                MyAppoint myAppointFragment = new MyAppoint();
                                Bundle bundle = new Bundle();
                                bundle.putInt("reservationType", reservationType);
                                myAppointFragment.setArguments(bundle);
                                UserBean userBean = UserOption.getInstance().querryUser();
                                if (userBean == null) {
                                    Presenter.getInstance().step2Fragment("login");
                                } else {
                                    Presenter.getInstance().step2fragment(myAppointFragment, "myAppoint");
                                }
                            }
                        });
                        binding.firstAppointCotainer.addView(view);
                    } else {
                        EmptyLayout2Binding emptyLayout2Binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.empty_layout2, binding.firstAppointCotainer, false);
                        emptyLayout2Binding.setTip("楼盘的预约");
                        binding.firstAppointCotainer.addView(emptyLayout2Binding.getRoot());
                    }
                }
        );
    }

    @Override
    public void initView() {
    }

    @Override
    public void initlisten() {
        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                UserBean userBean = UserOption.getInstance().querryUser();
                switch (data.getEventType()) {
                    case UserObservable.TYPE_PHOTO:
                        String headUrl = userBean.getAvatar();
                        String url = ImagUtil.handleUrl(headUrl);
                        if (!TextUtils.isEmpty(url)) {
                            Glide.with(getContext()).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Drawable drawable = ImagUtil.circle(resource);
                                    binding.ivHead.setImageDrawable(drawable);
                                }
                            });
                        }
                        break;
                    case UserObservable.TYPE_ACCOUNTMSG_CHANGE:
                        binding.setUser(userBean);
                        break;
                    case UserObservable.TYPE_LOGINOUT:
                        //清除登录信息
                        cleanLoginView();
                        main.togglePager(0);
                        break;
                    case UserObservable.TYPE_REFRESH_APPOINT:
                        initData();
                        break;
                    case UserObservable.TYPE_LOGIN_SUCCESS:
                        initData();
                        break;
                }
            }
        };
        UserObservable.getInstance().register(userObserver);
        binding.editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMsg accountMsgFragment = new AccountMsg();
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean == null) {
                    Presenter.getInstance().step2Fragment("login");
                } else {
                    accountMsgFragment.setUserBean(userBean);
                    Presenter.getInstance().step2fragment(accountMsgFragment, "accountMsg");
                }
            }
        });

        binding.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting setting = new Setting();
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean == null) {
                    Presenter.getInstance().step2Fragment("login");
                } else {
                    setting.setUserBean(userBean);
                    Presenter.getInstance().step2fragment(setting, "setting");
                }
            }
        });

        binding.ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMsg accountMsgFragment = new AccountMsg();
                UserBean userBean = UserOption.getInstance().querryUser();
                if (userBean == null) {
                    Presenter.getInstance().step2Fragment("login");
                } else {
                    accountMsgFragment.setUserBean(userBean);
                    Presenter.getInstance().step2fragment(accountMsgFragment, "accountMsg");
                }
            }
        });

    }


    @Override
    protected void loadData() {
        initData();
    }

    private void initLoginView(UserBean userBean) {
        String[] moduleNames = null;
        switch (userBean.getUserType()) {
            case 2:
            case 3:
                if (userBean.getUserType() == 2) {
                    moduleNames = new String[]{"wdlp", "my_employee", "lpyy", "review","myfans"};
                } else {
                    if (binding.llUserMsg.getChildCount() > 0) {
                        binding.llUserMsg.removeAllViews();
                    }
                    LayoutDeveloperMsgBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_developer_msg, binding.llUserMsg, false);
                    binding1.ratingBar.setRating((float) userBean.getScore());
                    binding.llUserMsg.addView(binding1.getRoot());
                    moduleNames = new String[]{"my_house_resource", "my_comment", "myappoint", "sell_record", "myfans"};
                }
                break;
            case 4:
                moduleNames = new String[]{"myfootprint", "my_comment", "myappoint", "wdsc", "myagent", "myfans"};
                if (binding.llUserMsg.getChildCount() > 0) {
                    binding.llUserMsg.removeAllViews();
                }
                int credit = userBean.getCredit();
                Presenter.getInstance().initCreditLayout(binding.llUserMsg, credit);
                break;
            case 5:
            case 6:
                if (userBean.getUserType() == 5) {
                    moduleNames = new String[]{"my_employee", "lpyy", "review","myfans"};
                } else {
                    if (binding.llUserMsg.getChildCount() > 0) {
                        binding.llUserMsg.removeAllViews();
                    }
                    LayoutDeveloperMsgBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_developer_msg, binding.llUserMsg, false);
                    binding1.ratingBar.setRating((float) userBean.getScore());
                    binding.llUserMsg.addView(binding1.getRoot());
                    moduleNames = new String[]{"my_comment", "myappoint", "sell_record", "myfans"};
                }
                break;
        }

        if (binding.getUser() == null) {
            initUserBaseMsg(userBean, moduleNames);
        }
    }

    private void initUserBaseMsg(UserBean userBean, String[] moduleNames) {
        initModule(moduleNames);
        String headUrl = userBean.getAvatar();
        String url = ImagUtil.handleUrl(headUrl);
        if (!TextUtils.isEmpty(url)) {
            Glide.with(getContext()).asBitmap().load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Drawable drawable = ImagUtil.circle( resource);
                    binding.ivHead.setImageDrawable(drawable);
                }
            });
        }
        binding.setUser(userBean);
        UserOption.getInstance().addUser(userBean);
    }

    private void cleanLoginView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.llUserMsg.removeAllViews();
                binding.ivHead.setImageDrawable(null);
                binding.llCotainer1.removeAllViews();
                binding.llCotainer2.removeAllViews();
                binding.firstAppointCotainer.removeAllViews();
                UserOption.getInstance().delteUser();
                binding.setUser(null);
            }
        });
    }

    private void initModule(String[] moduleNames) {
        for (int i = 0; i < moduleNames.length; i++) {
            LlContainerItemBinding itemBinding;
            if (i < 3) {
                itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.ll_container_item, binding.llCotainer1, false);
                binding.llCotainer1.addView(itemBinding.getRoot());
            } else {
                itemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.ll_container_item, binding.llCotainer2, false);
                binding.llCotainer2.addView(itemBinding.getRoot());
            }
            itemBinding.setPresenter(Presenter.getInstance());
            String fragmentName = moduleNames[i];
            int drawableRes = ResUtils.getInstance().getResource(getContext(), fragmentName, "drawable");
            int moduleNameRes = ResUtils.getInstance().getResource(getContext(), fragmentName, "string");
            itemBinding.setFragmentName(fragmentName);
            itemBinding.iv.setImageResource(drawableRes);
            itemBinding.tv.setText(moduleNameRes);
        }
    }


}
