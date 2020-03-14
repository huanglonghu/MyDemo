package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.danikula.videocache.HttpProxyCacheServer;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FullScreenBinding;
import com.xx.yuefang.databinding.PagerVideoBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;

import java.lang.reflect.Array;

public class FullScreen extends BaseFragment {

    private FullScreenBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.full_screen, container, false);
        initData();
        return binding.getRoot();
    }

    @Override
    public void initData() {
        String videoUrl = getArguments().getString("videoUrl");
        binding.videoView.setVideoPath(videoUrl);
        binding.videoView.start();
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                binding.loadingView.stopAnim();
                binding.loadingLayout.setVisibility(View.GONE);
                binding.imgPlay.animate().alpha(0f).start();
            }
        });

        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.imgPlay.animate().alpha(1f).start();
            }
        });


        binding.rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.videoView.isPlaying()) {
                    binding.imgPlay.animate().alpha(1f).start();
                    binding.videoView.pause();
                } else {
                    binding.imgPlay.animate().alpha(0f).start();
                    binding.videoView.start();
                }
            }
        });

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
