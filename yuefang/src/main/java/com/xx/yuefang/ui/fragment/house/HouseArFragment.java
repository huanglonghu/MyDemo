package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesAr;
import com.xx.yuefang.databinding.FragmentPremisesVrBinding;
import com.xx.yuefang.glpanorama.GLPanorama;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.ListVideoAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.viewpager.OnViewPagerListener;
import com.xx.yuefang.ui.viewpager.ViewPagerLayoutManager;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.LogUtil;
import java.util.ArrayList;
import java.util.List;

public class HouseArFragment extends BaseFragment {
    private FragmentPremisesVrBinding binding;
    private int id;
    private List<PremisesAr.DataBeanX.DataBean> list;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_premises_vr, container, false);
            id = getArguments().getInt("id");
            initData();
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    private static String TAG = "ViewPagerActivity";
    private ListVideoAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;

    @Override
    public void initView() {
        list = new ArrayList<>();
        mAdapter = new ListVideoAdapter(getContext(), list, new ClickSureListener() {
            @Override
            public void click(int model) {
                mLayoutManager.setCanVertically(model == 0 ? true : false);
            }
        });
        mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL, false);
        mLayoutManager.setCanVertically(true);
        binding.pageRecycle.setLayoutManager(mLayoutManager);
        binding.pageRecycle.setAdapter(mAdapter);
    }

    private int position = -1;

    @Override
    public void initlisten() {
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
            }
            @Override
            public void onPageRelease(boolean isNext, int position) {
                LogUtil.log(TAG + "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int pos, boolean isBottom) {
                LogUtil.log(TAG + "选中位置:" + pos + "  是否是滑动到底部:" + isBottom);
                if (pos != position) {
                    playVideo();
                }
                if (isBottom && refreshData) {
                    initData();
                }
                position = pos;
            }
        });
        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
//                if (data > 14) {
//                    id = data - 14;
//                    page = 1;
//                    list.clear();
//                    initData();
//                }
            }
        };
        UserObservable.getInstance().register(userObserver);


    }

    private void playVideo() {
        View itemView = binding.pageRecycle.getChildAt(0);
        VideoView videoView = itemView.findViewById(R.id.videoView);
        ImageView imgPlay = itemView.findViewById(R.id.img_play);
        imgPlay.animate().alpha(0).setDuration(200).start();
        videoView.start();
    }

    private void releaseVideo(int index) {
        View itemView = binding.pageRecycle.getChildAt(index);
        VideoView videoView = itemView.findViewById(R.id.videoView);
        ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgPlay.animate().alpha(1).start();
    }


    private int page = 1;
    private boolean refreshData;

    @Override
    public void initData() {
        if (getArguments() != null) {
            HttpUtil.getInstance().querryPremisesList(id, page).subscribe(
                    str -> {
                        PremisesAr premisesAr = GsonUtil.fromJson(str, PremisesAr.class);
                        PremisesAr.DataBeanX premisesArData = premisesAr.getData();
                        List<PremisesAr.DataBeanX.DataBean> data = premisesArData.getData();
                        if (data != null && data.size() > 0) {
                            list.addAll(data);
                            mAdapter.notifyDataSetChanged();
                            page++;
                            refreshData = true;
                        } else {
                            refreshData = false;
                        }

                    }
            );


        }
    }


    @Override
    public void onKeyDown() {
        View itemView = binding.pageRecycle.getChildAt(0);
        if (mAdapter.getFragmentModel() == 1) {
            exitVr(itemView);
        } else if (mAdapter.getFragmentModel() == 0) {
            VideoView videoView = itemView.findViewById(R.id.videoView);
            boolean playing = videoView.isPlaying();
            if (playing) {
                releaseVideo(0);
            } else {
                Presenter.getInstance().back();
            }
        }
    }


    public void exitVr(View itemView) {
        GLPanorama glPanorama = itemView.findViewById(R.id.vr);
        RelativeLayout relativeLayout = itemView.findViewById(R.id.rl_video);
        RecyclerView recyclerView = itemView.findViewById(R.id.ar_recycle);
        VideoView videoView = itemView.findViewById(R.id.videoView);
        Integer position = (Integer) recyclerView.getTag();
        View item = recyclerView.getChildAt(position);
        item.setSelected(false);
        glPanorama.stop();
        relativeLayout.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.VISIBLE);
        mAdapter.setFragmentModel(0);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (list != null && list.size() > 0) {
            playVideo();
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (list != null && list.size() > 0) {
            releaseVideo(0);
        }
    }
}
