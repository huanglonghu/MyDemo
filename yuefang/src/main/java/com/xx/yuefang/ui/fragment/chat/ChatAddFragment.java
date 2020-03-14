package com.xx.yuefang.ui.fragment.chat;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.ChatBottomAddBinding;
import com.xx.yuefang.handler.ActivityResultHandler;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.strategy.HandlerStrategy;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.Chat;
import com.xx.yuefang.ui.fragment.selectPhoto.ImageSelectorFragment;
import com.xx.yuefang.ui.widget.SendLocationDialog;
import com.xx.yuefang.util.LogUtil;

import java.io.File;

public class ChatAddFragment extends BaseFragment {

    private ChatBottomAddBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.chat_bottom_add, container, false);
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
    }

    @Override
    public void initlisten() {
        binding.showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendLocationDialog sendLocationDialog = new SendLocationDialog(getContext(), new ClickSureListener() {
                    @Override
                    public void clickSure() {
                        ChatLocationMsgFragment chatLocationMsgFragment = new ChatLocationMsgFragment();
                        Presenter.getInstance().step2fragment(chatLocationMsgFragment, "chatLocation");
                    }
                });
                sendLocationDialog.show();
            }
        });

        binding.photoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelectorFragment imageSelectorFragment = new ImageSelectorFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("urlType",UserObservable.TYPE_SELECT_PHOTO_CHAT);
                imageSelectorFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(imageSelectorFragment, "imageSelect");
            }
        });


        binding.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCamera();
            }
        });



    }

    //激活相机操作
    private void goCamera() {
        File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //第二个参数为 包名.fileprovider
            uri = FileProvider.getUriForFile(getActivity(), "com.xx.yuefang.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        new ActivityResultHandler.Builder().requestCode(ActivityResultHandler.REQUEST_TAKE_PHOTO).hadlerStrategy(new HandlerStrategy() {
            @Override
            public void onActivityResult() {
                String photoPath;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    photoPath = String.valueOf(cameraSavePath);
                } else {
                    photoPath = uri.getEncodedPath();
                }
                if (chat != null) {
                    chat.sendImgMessage(photoPath);
                }
            }


        }).intent(intent).activity((AppCompatActivity) getActivity()).build().startActivityForResult();

    }

    private Chat chat;

    public void chat(Chat chat) {
        this.chat = chat;
    }



}
