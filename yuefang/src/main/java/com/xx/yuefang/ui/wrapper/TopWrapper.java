package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.databinding.PagerVideoBinding;
import com.xx.yuefang.databinding.PagerVrBinding;
import com.xx.yuefang.databinding.WrapperTopBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.PremisesDetailPageAdapter;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.fragment.house.houseDetail.FullScreen;
import com.xx.yuefang.ui.fragment.house.houseDetail.PictureDetail;
import com.xx.yuefang.ui.fragment.house.houseDetail.UserComment;
import com.xx.yuefang.ui.fragment.news.Link;
import com.xx.yuefang.ui.widget.ShareDialog;
import com.xx.yuefang.util.ImagUtil;

import java.util.ArrayList;

public class TopWrapper extends Wrapper {
    private WrapperTopBinding binding;
    private PagerVideoBinding pagerVideoBinding;
    private ArrayList<View> views;

    public TopWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View initView() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_top, this, false);
        binding.viewPager.setOffscreenPageLimit(5);
        binding.setTop(this);
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        if (views == null) {
            views = new ArrayList<>();
            LayoutInflater inflater = LayoutInflater.from(context);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            pagerVideoBinding = DataBindingUtil.inflate(inflater, R.layout.pager_video, binding.viewPager, false);
            initVideoPager(data, views, pagerVideoBinding);
            PagerVrBinding pagerVrBinding = DataBindingUtil.inflate(inflater, R.layout.pager_vr, binding.viewPager, false);
            String vrPicture = data.getVRPicture();
            String url1 = ImagUtil.handleUrl(vrPicture);
            if (!TextUtils.isEmpty(url1)) {
                Glide.with(context).load(url1).thumbnail(0.1f).centerCrop().into(pagerVrBinding.ivImg);
            } else {
                pagerVrBinding.ivImg.setBackgroundResource(R.drawable.default_ar);
            }
            View vr = pagerVrBinding.getRoot();
            vr.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vrUrl = data.getVRUrl();
                    if (!TextUtils.isEmpty(vrUrl)) {
                        Link link = new Link();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", vrUrl);
                        link.setArguments(bundle);
                        Presenter.getInstance().step2fragment(link, "link");
                    } else {
                        Toast.makeText(getContext(), "暂无VR", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            views.add(vr);
            String picture = data.getPicture();
            if (!TextUtils.isEmpty(picture)) {
                String[] pictures = picture.split(",");
                for (int i = 0; i < pictures.length; i++) {
                    String url = ImagUtil.handleUrl(pictures[i]);
                    ImageView imageView = new ImageView(context);
                    imageView.setLayoutParams(layoutParams);
                    views.add(imageView);
                    int finalI = i;
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PictureDetail pictureDetail = new PictureDetail();
                            Bundle bundle = new Bundle();
                            bundle.putStringArray("urls", pictures);
                            bundle.putInt("index", finalI);
                            pictureDetail.setArguments(bundle);
                            Presenter.getInstance().step2fragment(pictureDetail, "pictureDetail");
                        }
                    });
                    Glide.with(context).load(url).thumbnail(0.5f).centerCrop().into(imageView);
                }
                binding.setImgSize(pictures.length);
            } else {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(layoutParams);
                imageView.setBackgroundResource(R.drawable.default_ar);
                views.add(imageView);
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "暂无图片", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            PremisesDetailPageAdapter premisesDetailPageAdapter = new PremisesDetailPageAdapter(views);
            binding.viewPager.setAdapter(premisesDetailPageAdapter);
            binding.setData(data);
            initListen(data);
        }

    }

    private void initListen(PremisesDetail.DataBean data) {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (binding.getSelectPosition() == 0 && i != 0) {
                    if (pagerVideoBinding.videoView.isPlaying()) {
                        pagerVideoBinding.imgPlay.animate().alpha(1f).start();
                        pagerVideoBinding.videoView.pause();
                    }
                }
                switch (i) {
                    case 0:
                        binding.setSelectPosition(0);
                        pagerVideoBinding.imgPlay.animate().alpha(0f).start();
                        pagerVideoBinding.videoView.start();
                        break;
                    case 1:
                        binding.setSelectPosition(1);
                        break;
                    case 2:
                        binding.setSelectPosition(2);
                        binding.setImgIndex(1);
                        break;
                    default:
                        binding.setImgIndex(i - 1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        binding.rise.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().rise(data.getId()).subscribe(
                        str -> {
                            int rise = data.getRise();
                            if (data.isIsRise()) {
                                rise = rise - 1;
                                data.setIsRise(false);
                            } else {
                                rise = rise + 1;
                                data.setIsRise(true);
                            }
                            data.setRise(rise);
                            binding.setData(data);
                        }
                );
            }
        });

        binding.collect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.getInstance().userCollection(data.getId()).subscribe(
                        str -> {
                            if (data.isIsCollection()) {
                                data.setIsCollection(false);
                            } else {
                                data.setIsCollection(true);
                            }
                            binding.setData(data);
                        }
                );
            }
        });

        binding.comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                UserComment userComment = new UserComment();
                userComment.setDatas(data.getId());
                Presenter.getInstance().step2fragment(userComment, "userComment");
            }
        });

        binding.share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog shareDialog = new ShareDialog(context, 1, data, null);
                shareDialog.toShow();
            }
        });
    }

    private boolean isPrepared;

    private void initVideoPager(PremisesDetail.DataBean data, ArrayList<View> views, PagerVideoBinding pagerVideoBinding) {
        String videoUrl = data.getVideoUrl();
        videoUrl = ImagUtil.handleUrl(videoUrl);
        if (!TextUtils.isEmpty(videoUrl)) {
            HttpProxyCacheServer proxy = YueFangApplication.getProxy(context);
            String proxyUrl = proxy.getProxyUrl(videoUrl);
            pagerVideoBinding.videoView.setVideoPath(proxyUrl);
            pagerVideoBinding.fullScreen.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    FullScreen fullScreen = new FullScreen();
                    Bundle bundle = new Bundle();
                    bundle.putString("videoUrl", proxyUrl);
                    fullScreen.setArguments(bundle);
                    Presenter.getInstance().step2fragment(fullScreen, "fullScreen");
                }
            });
            pagerVideoBinding.loadingView.startAnim();
            toggleVideo();
            pagerVideoBinding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (!isPrepared) {
                        pagerVideoBinding.loadingView.stopAnim();
                        pagerVideoBinding.loadingLayout.setVisibility(View.GONE);
                        isPrepared = true;
                    }
                }
            });
            pagerVideoBinding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    pagerVideoBinding.imgPlay.animate().alpha(1f).start();
                }
            });


        } else {
            pagerVideoBinding.videoView.setVisibility(GONE);
            pagerVideoBinding.rlVideo.setBackgroundResource(R.drawable.default_ar);
            pagerVideoBinding.loadingLayout.setVisibility(GONE);
        }


        pagerVideoBinding.rlVideo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(data.getVideoUrl())) {
                    Toast.makeText(getContext(), "暂无视频", Toast.LENGTH_SHORT).show();
                    return;
                }
                toggleVideo();
            }
        });
        views.add(pagerVideoBinding.getRoot());
    }


    public void toggleVideo() {
        if (pagerVideoBinding != null) {
            if (pagerVideoBinding.videoView.isPlaying()) {
                pagerVideoBinding.imgPlay.animate().alpha(1f).start();
                pagerVideoBinding.videoView.pause();
            } else {
                int selectPosition = binding.getSelectPosition();
                pagerVideoBinding.imgPlay.animate().alpha(0f).start();
                if (selectPosition == 0) {
                    pagerVideoBinding.videoView.start();
                }
            }
        }
    }

    public void stopPlayInVisiable() {
        if (pagerVideoBinding != null) {
            pagerVideoBinding.imgPlay.animate().alpha(1f).start();
            pagerVideoBinding.videoView.pause();
        }
    }

    public void startPlayVisiable() {
        if (pagerVideoBinding != null) {
            int selectPosition = binding.getSelectPosition();
            pagerVideoBinding.imgPlay.animate().alpha(0f).start();
            if (selectPosition == 0) {
                pagerVideoBinding.videoView.start();
            }
        }
    }


    public void toggle(int index) {
        if (binding.getSelectPosition() != index) {
            if (binding.getSelectPosition() == 0) {
                if (pagerVideoBinding.videoView.isPlaying()) {
                    pagerVideoBinding.imgPlay.animate().alpha(1f).start();
                    pagerVideoBinding.videoView.pause();
                }
            }
            if (index == 0) {
                pagerVideoBinding.imgPlay.animate().alpha(0f).start();
                pagerVideoBinding.videoView.start();
            }
            binding.setSelectPosition(index);
            binding.viewPager.setCurrentItem(index);
        }
    }


}
