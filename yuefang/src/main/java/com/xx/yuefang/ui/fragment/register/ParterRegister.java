package com.xx.yuefang.ui.fragment.register;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.ParterRegisterBody;
import com.xx.yuefang.bean.UploadPicture;
import com.xx.yuefang.databinding.RegisterParterBinding;
import com.xx.yuefang.handler.ActivityResultHandler;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.strategy.HandlerStrategy;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.widget.RegiserSuccessDialog;
import com.xx.yuefang.util.GsonUtil;
import java.util.concurrent.TimeUnit;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MultipartBody;

public class ParterRegister extends BaseFragment {

    private RegisterParterBinding binding;
    private ParterRegisterBody parterRegisterBody;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.register_parter, container, false);
            binding.setRegister(this);
            parterRegisterBody = new ParterRegisterBody();
            binding.setParterRegisterBody(parterRegisterBody);
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

    @Override
    public void initlisten() {

        binding.zm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @Override
                    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        binding.zm.setBackground(bitmapDrawable);
                        HttpUtil.getInstance().upload(filePart, null).subscribe(
                                str -> {
                                    UploadPicture uploadPicture = GsonUtil.fromJson(str, UploadPicture.class);
                                    String url = uploadPicture.getData();
                                    parterRegisterBody.setIDCardPositive(url);
                                }
                        );
                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();
            }
        });

        binding.fm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                new ActivityResultHandler.Builder().hadlerStrategy(new HandlerStrategy() {
                    @Override
                    public void onActivityResult(MultipartBody.Part filePart, Bitmap bitmap) {
                        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                        binding.fm.setBackground(bitmapDrawable);
                        HttpUtil.getInstance().upload(filePart, null).subscribe(
                                str -> {
                                    UploadPicture uploadPicture = GsonUtil.fromJson(str, UploadPicture.class);
                                    String url = uploadPicture.getData();
                                    parterRegisterBody.setIDCardReverseSide(url);
                                }
                        );
                    }
                }).requestCode(ActivityResultHandler.REQUEST_SELECT_PHOTO).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();


            }
        });


        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.etPhone.getText().toString();
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getContext(), "请输入真实姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (binding.getArea() != 0) {
                    if (binding.getArea() == 1) {
                        phoneNumber = "852" + phoneNumber;
                    } else if (binding.getArea() == 2) {
                        phoneNumber = "853" + phoneNumber;
                    }
                }
                parterRegisterBody.setPhoneNumber(phoneNumber);
                if (TextUtils.isEmpty(parterRegisterBody.getPhoneNumber())) {
                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(parterRegisterBody.getCode())) {
                    Toast.makeText(getContext(), "请输入短信验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(parterRegisterBody.getIDCardPositive())) {
                    Toast.makeText(getContext(), "请上传身份证人像面照", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(parterRegisterBody.getIDCardReverseSide())) {
                    Toast.makeText(getContext(), "请上传身份证国徽面照", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!binding.cb.isChecked()) {
                    Toast.makeText(getContext(), "请勾选用户协议", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpUtil.getInstance().registerPartner(parterRegisterBody).subscribe(
                        str -> {
                            parterRegisterBody = new ParterRegisterBody();
                            binding.setParterRegisterBody(parterRegisterBody);
                            RegiserSuccessDialog regiserSuccessDialog = new RegiserSuccessDialog(getContext());
                            regiserSuccessDialog.show();
                        }
                );


            }
        });

        binding.sendYzm.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   String phoneNumber = binding.etPhone.getText().toString();
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
                                                   parterRegisterBody.setPhoneNumber(phoneNumber);
                                                   HttpUtil.getInstance().getYzm(parterRegisterBody.getPhoneNumber(), 1).subscribe(
                                                           str -> {
                                                               countDownTime();
                                                           }
                                                   );
                                               }
                                           }
        );

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
