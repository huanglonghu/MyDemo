package com.xx.yuefang.ui.fragment.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.xx.yuefang.R;
import com.xx.yuefang.bean.CompanyRegisterBody;
import com.xx.yuefang.bean.UploadPicture;
import com.xx.yuefang.databinding.RegisterCompanyBinding;
import com.xx.yuefang.handler.ActivityResultHandler;
import com.xx.yuefang.handler.UpdateAccountMsg;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.strategy.HandlerStrategy;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.selectPhoto.ImageSelectorFragment;
import com.xx.yuefang.ui.widget.RegiserSuccessDialog;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompanyRegister extends BaseFragment {

    private RegisterCompanyBinding binding;
    private CompanyRegisterBody companyRegisterBody;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.register_company, container, false);
            binding.setRegister(this);
            companyRegisterBody = new CompanyRegisterBody();
            binding.setCompanyRegisterBody(companyRegisterBody);
            initlisten();
        }

        return binding.getRoot();
    }

    public void toggleArea(int area) {
        binding.setArea(area);
    }




    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    private UserObserver userObserver;

    @Override
    public void initlisten() {

        binding.postLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @Override
                    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        binding.postLicense.setBackground(bitmapDrawable);
                        HttpUtil.getInstance().upload(filePart, null).subscribe(
                                str -> {
                                    UploadPicture uploadPicture = GsonUtil.fromJson(str, UploadPicture.class);
                                    String url = uploadPicture.getData();
                                    companyRegisterBody.setBusinessLicense(url);
                                }
                        );
                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();


            }
        });

        binding.sendYzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber =binding.etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                HttpUtil.getInstance().getYzm(phoneNumber, 1).subscribe(
                        str -> {
                            countDownTime();
                        }
                );
            }
        });


        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(companyRegisterBody.getCompanyName())) {
                    Toast.makeText(getContext(), "请输入公司名称", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(companyRegisterBody.getUserName())) {
                    Toast.makeText(getContext(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }

                String phoneNumber =binding.etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                companyRegisterBody.setPhoneNumber(phoneNumber);

                if (TextUtils.isEmpty(companyRegisterBody.getCode())) {
                    Toast.makeText(getContext(), "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(companyRegisterBody.getBusinessLicense())) {
                    Toast.makeText(getContext(), "请上传营业执照图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.cb.isChecked()) {
                    Toast.makeText(getContext(), "请勾选用户协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().registerCompany(companyRegisterBody).subscribe(
                        str -> {
                            companyRegisterBody=new CompanyRegisterBody();
                            binding.setCompanyRegisterBody(companyRegisterBody);
                            RegiserSuccessDialog regiserSuccessDialog = new RegiserSuccessDialog(getContext());
                            regiserSuccessDialog.show();
                        }
                );

            }
        });


    }


    public void countDownTime() {
        binding.sendYzm.setEnabled(false);
        Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        binding.sendYzm.setText("重新发送 " + String.valueOf(60 - aLong) + " 秒");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        binding.sendYzm.setText("获取验证码");
                        binding.sendYzm.setEnabled(true);

                    }
                })
                .subscribe();
    }
}
