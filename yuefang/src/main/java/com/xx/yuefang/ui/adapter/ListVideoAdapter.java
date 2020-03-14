package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danikula.videocache.HttpProxyCacheServer;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesAr;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.VideoItemBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.ui.fragment.house.HouseDetailFragment;
import com.xx.yuefang.ui.fragment.house.houseDetail.UserComment;
import com.xx.yuefang.ui.holder.VideoViewHolder;
import com.xx.yuefang.ui.widget.ShareDialog;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.ThridPartLogin;

import java.util.ArrayList;
import java.util.List;

public class ListVideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private Context context;
    private List<PremisesAr.DataBeanX.DataBean> list;
    private ClickSureListener clickSureListener;

    public ListVideoAdapter(Context context, List<PremisesAr.DataBeanX.DataBean> list, ClickSureListener clickSureListener) {
        this.context = context;
        this.list = list;
        this.clickSureListener = clickSureListener;
    }


    private void videoControl(VideoItemBinding binding) {
        if (binding.videoView.isPlaying()) {
            binding.imgPlay.animate().alpha(1f).start();
            binding.videoView.pause();
        } else {
            binding.imgPlay.animate().alpha(0f).start();
            binding.videoView.start();
        }
    }

    public void initListener(VideoItemBinding binding, PremisesAr.DataBeanX.DataBean data, ArrayList<String> arUrlList) {
        binding.rise.setOnClickListener(new View.OnClickListener() {
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

        binding.collect.setOnClickListener(new View.OnClickListener() {
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

        binding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareDialog shareDialog = new ShareDialog(context,data.getId(),data);
//                shareDialog.show();
            }
        });

        binding.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HouseDetailFragment houseDetailFragment = new HouseDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", data.getId());
                houseDetailFragment.setArguments(bundle);
                Presenter.getInstance().step2fragment(houseDetailFragment, "houseDetail");
            }
        });

        binding.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserComment userComment = new UserComment();
                userComment.setDatas(data.getId());
                Presenter.getInstance().step2fragment(userComment, "userComment");
            }
        });

        binding.vrkf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arUrlList.size() > 0) {
                    int visibility = binding.rlVr.getVisibility();
                    if (visibility == View.VISIBLE) {
                        binding.rlVr.setVisibility(View.INVISIBLE);
                    } else if (visibility == View.INVISIBLE) {
                        binding.rlVr.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "暂无vr", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.videoView.start();
            }
        });

        binding.rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoControl(binding);
            }
        });

        if (!TextUtils.isEmpty(data.getVideoUrl())) {
            binding.loadingView.startAnim();
            binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    binding.loadingView.stopAnim();
                    binding.loadingLayout.setVisibility(View.GONE);
                }
            });
        } else {
            binding.rlVideo.setVisibility(View.GONE);
        }

    }

    private void initVrData(VideoItemBinding binding, PremisesAr.DataBeanX.DataBean data, ArrayList<String> arUrlList) {
        UserBean userBean = UserOption.getInstance().querryUser();
        if(userBean!=null){
            binding.setUserType(userBean.getUserType());
        }
        if (data != null) {
            binding.setData(data);
            binding.setPresenter(Presenter.getInstance());
            String developerAvatar = data.getDeveloperAvatar();
            String url1 = ImagUtil.handleUrl(developerAvatar);
            if (!TextUtils.isEmpty(url1)) {
                RxImageLoader.with(context).getBitmap(url1).subscribe(
                        imageBean -> {
                            Drawable drawable = ImagUtil.circle(imageBean.getBitmap());
                            binding.logo.setBackground(drawable);
                        }
                );
            }

            List<String> characteristics = data.getCharacteristics();
            for (int i = 0; i < characteristics.size() + 1; i++) {
                TextView item;
                item = (TextView) View.inflate(context, R.layout.layout_character3, null);
                if (i == 0) {
                    item.setText(data.getState());
                } else {
                    item.setText(characteristics.get(i - 1));
                }
                binding.llexpend.addView(item, i);
            }

            List<PremisesAr.DataBeanX.DataBean.PremisesARsBean> premisesARs = data.getPremisesARs();
            if(premisesARs!=null){
                for (int i = 0; i < premisesARs.size(); i++) {
                    PremisesAr.DataBeanX.DataBean.PremisesARsBean premisesARsBean = premisesARs.get(i);
                    String picture = premisesARsBean.getPicture();
                    if (!TextUtils.isEmpty(picture)) {
                        if (picture.contains(",")) {
                            String[] urls = picture.split(",");
                            for (int j = 0; j < urls.length; j++) {
                                String url = urls[j];
                                if (!TextUtils.isEmpty(url)) {
                                    url = ImagUtil.handleUrl(url);
                                    arUrlList.add(url);
                                }
                            }
                        } else {
                            if (!TextUtils.isEmpty(picture)) {
                                picture = ImagUtil.handleUrl(picture);
                                arUrlList.add(picture);
                            }
                        }
                    }
                }
            }

            initVrItem(binding, arUrlList);
            String videoUrl = data.getVideoUrl();
            String url = ImagUtil.handleUrl(videoUrl);
            if (!TextUtils.isEmpty(url)) {
                HttpProxyCacheServer proxy = YueFangApplication.getProxy(context);
                String proxyUrl = proxy.getProxyUrl(url);
                binding.videoView.setVideoPath(proxyUrl);
            }
        }
    }


    private void initVrItem(VideoItemBinding binding, List<String> arUrlList) {

        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider3));
        binding.arRecycle.addItemDecoration(divider);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false);
//        VrItemAdapter vrItemAdapter = new VrItemAdapter(context, arUrlList, new ClickSureListener() {
//            @Override
//            public void clickVtItem(int position, Bitmap bitmap) {
//                if (binding.videoView.isPlaying()) {
//                    binding.imgPlay.animate().alpha(1f).start();
//                    binding.videoView.pause();
//                }
//                binding.rlVideo.setVisibility(View.INVISIBLE);
//                binding.videoView.setVisibility(View.INVISIBLE);
//                setFragmentModel(1);
//                binding.arRecycle.setTag(position);
//                binding.vr.setGLPanorama(bitmap);
//                binding.vr.start();
//            }
//        });
//        binding.arRecycle.setLayoutManager(linearLayoutManager);
//        binding.arRecycle.setAdapter(vrItemAdapter);
    }

    private int fragmentModel;

    public int getFragmentModel() {
        return fragmentModel;
    }

    public void setFragmentModel(int fragmentModel) {
        this.fragmentModel = fragmentModel;
        clickSureListener.click(fragmentModel);

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.video_item, parent, false);
        VideoItemBinding binding = DataBindingUtil.bind(itemView);
        return new VideoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItemBinding binding = holder.getBinding();
        if (list.size() != 0) {
            ArrayList<String> arUrlList = new ArrayList<>();
            initVrData(binding, list.get(position), arUrlList);
            initListener(binding, list.get(position), arUrlList);
            View root = binding.getRoot();
            ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            root.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
