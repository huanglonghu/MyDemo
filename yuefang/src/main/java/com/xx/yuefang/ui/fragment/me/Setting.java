package com.xx.yuefang.ui.fragment.me;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.BindCompanyQuerry;
import com.xx.yuefang.bean.VersionMsg;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.BindCompanyBinding;
import com.xx.yuefang.databinding.FragmentSettingBinding;
import com.xx.yuefang.databinding.SettingItemCompanyBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.news.Link2;
import com.xx.yuefang.ui.widget.BindCompanyDialog;
import com.xx.yuefang.ui.widget.TipDialog;
import com.xx.yuefang.ui.widget.TipDialog2;
import com.xx.yuefang.ui.widget.TipDialog3;
import com.xx.yuefang.util.DataCleanManager;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;

import cn.jpush.im.android.api.JMessageClient;

public class Setting extends BaseFragment {

    private FragmentSettingBinding binding;
    private UserBean userBean;
    private double currentVersion;
    private String commanyName;
    private BindCompanyQuerry.DataBean data;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public void initData() {
        String cacheSize = getCacheSize();
        binding.cacheSize.setText(cacheSize);
        String avatar = userBean.getAvatar();
        String url = ImagUtil.handleUrl(avatar);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                    imageBean -> {
                        Bitmap bitmap = imageBean.getBitmap();
                        Drawable circle = ImagUtil.circle(bitmap);
                        binding.ivHead.setImageDrawable(circle);
                    }
            );
        }

        try {
            String versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            binding.version.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    //获取缓存大小
    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    //清空缓存
    private void cleanCache() {
        DataCleanManager.clearAllCache(getContext());
    }

    @Override
    public void initView() {
        userBean = UserOption.getInstance().querryUser();
        if (this.userBean != null) {
            binding.setUserType(userBean.getUserType());
            if (this.userBean.getUserType() == 6) {
                HttpUtil.getInstance().isBindCompany().subscribe(
                        str -> {
                            BindCompanyQuerry bindCompanyQuerry = GsonUtil.fromJson(str, BindCompanyQuerry.class);
                            data = bindCompanyQuerry.getData();
                            commanyName = data.getCompanyName();
                            if (TextUtils.isEmpty(commanyName)) {
                                BindCompanyBinding bindCompanyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.bind_company, binding.ll, false);
                                bindCompanyBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (data.getExamineState() == 0) {
                                            new BindCompanyDialog(getContext(), new ClickSureListener() {
                                                @Override
                                                public void click(int id) {
                                                    HttpUtil.getInstance().bindCompany(id).subscribe(
                                                            str -> {
                                                                Toast.makeText(getContext(), "申请绑定成功,请等待审核", Toast.LENGTH_SHORT).show();
                                                            }
                                                    );
                                                }
                                            }).show();
                                        } else if (data.getExamineState() == 1) {
                                            new TipDialog2(getContext(), "您已申请绑定公司(" + commanyName + ")目前审核中，请耐心等待").show();
                                        } else {
                                            new TipDialog3(getContext(), "您的绑定申请已被" + commanyName + "拒绝", new ClickSureListener() {
                                                @Override
                                                public void clickSure() {
                                                    new BindCompanyDialog(getContext(), new ClickSureListener() {
                                                        @Override
                                                        public void click(int id) {
                                                            HttpUtil.getInstance().bindCompany(id).subscribe(
                                                                    str -> {
                                                                        Toast.makeText(getContext(), "申请绑定成功,请等待审核", Toast.LENGTH_SHORT).show();
                                                                    }
                                                            );
                                                        }
                                                    }).show();
                                                }
                                            }).show();
                                        }
                                    }
                                });
                                binding.ll.addView(bindCompanyBinding.getRoot());
                            } else {
                                SettingItemCompanyBinding settingItemCompanyBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.setting_item_company, binding.ll, false);
                                settingItemCompanyBinding.companyName.setText(this.userBean.getCommanyName());
                                binding.ll.addView(settingItemCompanyBinding.getRoot());
                            }
                        }
                );
            } else if (this.userBean.getUserType() == 5) {
                binding.companyId.setText(this.userBean.getId() + "");
            }
        }
    }

    @Override
    public void initlisten() {

        binding.account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountMsg accountMsg = new AccountMsg();
                accountMsg.setUserBean(userBean);
                Presenter.getInstance().step2fragment(accountMsg, "accountMsg");
            }
        });

        try {
            String packageName = getActivity().getPackageName();
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(packageName, 0);
            currentVersion = Double.parseDouble(packageInfo.versionName);
        } catch (Exception e) {
        }
        binding.checkVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().checkVersion().subscribe(
                        str -> {
                            VersionMsg versionMsg = GsonUtil.fromJson(str, VersionMsg.class);
                            double versionName = versionMsg.getVersionName();
                            if (currentVersion < versionName) {
                                Toast.makeText(getContext(), "有新版本" + versionName + "可更新", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "当前版本是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


        binding.cleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TipDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        cleanCache();
                        binding.cacheSize.setText("0");
                    }
                }, "你确定要清除缓存吗？").show();
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().exit().subscribe(
                        str -> {
                            UserOption.getInstance().delteUser();
                            JMessageClient.logout();
                            UserObservable.getInstance().notifyObservers(new EventData(UserObservable.TYPE_LOGINOUT));
                            Presenter.getInstance().back();
                        }
                );
            }
        });

        binding.yszc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房隐私政策");
                bundle.putString("url", HttpParam.baseUrl + "/protocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
            }
        });


        binding.fwxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link2 link = new Link2();
                Bundle bundle = new Bundle();
                bundle.putString("title", "约房用户服务协议");
                bundle.putString("url", HttpParam.baseUrl + "/serviceprotocol.html");
                link.setArguments(bundle);
                Presenter.getInstance().step2fragment(link, "link2");
            }
        });


    }
}
