package com.xx.yuefang.ui.fragment.house;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.FragmentArBinding;
import com.xx.yuefang.databinding.LayoutVrItemBinding;
import com.xx.yuefang.glpanorama.GLPanorama;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.VrItemAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Ar extends BaseFragment {
    private FragmentArBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ar, container, false);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        binding.selectScene.post(new Runnable() {
            @Override
            public void run() {
                initVrWindow(vrList, 1);
            }
        });
    }

    private void initVrWindow(List<PremisesDetail.DataBean.VRListBean> vrList, int level) {
        VrSelectWindow vrSelectWindow = new VrSelectWindow(getContext(), vrList, level, 0);
        vrSelectWindow.show(binding.selectScene);
        vrMap.put(level, vrSelectWindow);
        vrSelectWindow.select();
        PremisesDetail.DataBean.VRListBean vrListBean = vrList.get(0);
        List<PremisesDetail.DataBean.VRListBean> lists = vrListBean.getLists();
        if (lists!=null&&lists.size() > 0) {
            initVrWindow(lists, level + 1);
        } else {
            String picture = vrListBean.getPicture();
            String url = ImagUtil.handleUrl(picture);
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(getContext()).getBitmap(url).subscribe(
                        imageBean -> {
                            Bitmap bitmap = imageBean.getBitmap();
                            if(getContext()!=null){
                                addVr(getContext(),bitmap);
                            }

                        }
                );
            }
        }
    }


    @Override
    public void initlisten() {

        binding.selectScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vrMap.size() == 0) {
                    VrSelectWindow vrSelectWindow = new VrSelectWindow(getContext(), vrList, 1, 0);
                    vrSelectWindow.show(binding.selectScene);
                    vrMap.put(1, vrSelectWindow);
                } else {
                    for (Integer key : vrMap.keySet()) {
                        VrSelectWindow vrSelectWindow = vrMap.get(key);
                        vrSelectWindow.dismiss();
                    }
                    vrMap.clear();
                }

            }
        });
    }

    private List<PremisesDetail.DataBean.VRListBean> vrList;

    public void setData(List<PremisesDetail.DataBean.VRListBean> vrList) {
        this.vrList = vrList;
    }

    private HashMap<Integer, VrSelectWindow> vrMap = new HashMap<>();


    public class VrSelectWindow extends PopupWindow {

        private float windowHeight;
        private int level;
        private List<PremisesDetail.DataBean.VRListBean> vrBeans;
        private int position;
        private LayoutVrItemBinding binding;

        public VrSelectWindow(Context context, List<PremisesDetail.DataBean.VRListBean> vrBeans, int level, int position) {
            this.vrBeans = vrBeans;
            this.level = level;
            this.position = position;
            setFocusable(false);
            setOutsideTouchable(false);
            setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            windowHeight = RudenessScreenHelper.pt2px(context, 150);
            setHeight((int) windowHeight);
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_vr_item, null, false);
            initVrItem(vrBeans, binding.arRecycle, level);
            setContentView(binding.getRoot());
        }

        public void select() {
            VrItemAdapter.VrItemViewHolder vh = (VrItemAdapter.VrItemViewHolder) binding.arRecycle.findViewHolderForLayoutPosition(0);
            vh.handlerClickItem();
        }

        public void show(View v) {
            View root = binding.getRoot();
            root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int[] pos = new int[2];
            v.getLocationOnScreen(pos);
            showAtLocation(v, Gravity.NO_GRAVITY, 0, (int) (pos[1] - windowHeight * level - level * 20));
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }


    private void initVrItem(List<PremisesDetail.DataBean.VRListBean> vrBeans, RecyclerView recyclerView, int level) {
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider3));
        recyclerView.addItemDecoration(divider);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false);
        VrItemAdapter vrItemAdapter = new VrItemAdapter(getContext(), vrBeans, new ClickSureListener() {
            @Override
            public void clickVtItem(int position, Bitmap bitmap) {
                PremisesDetail.DataBean.VRListBean vrListBean = vrBeans.get(position);
                List<PremisesDetail.DataBean.VRListBean> lists = vrListBean.getLists();
                if (!vrMap.containsKey(level + 1)) {
                    if (lists != null) {
                        if (lists.size() > 0) {
                            VrSelectWindow vrSelectWindow = new VrSelectWindow(getContext(), lists, level + 1, position);
                            vrSelectWindow.show(binding.selectScene);
                            vrMap.put(level + 1, vrSelectWindow);
                        } else {
                            LogUtil.log("1============AAAAAAAAAAAAAxx==============");
                            addVr(getContext(),bitmap);
                        }
                    }
                } else {
                    VrSelectWindow vrSelectWindow = vrMap.get(level + 1);
                    int pos = vrSelectWindow.getPosition();
                    Iterator<Map.Entry<Integer, VrSelectWindow>> iterator = vrMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, VrSelectWindow> entry = iterator.next();
                        Integer key = entry.getKey();
                        if (key >= level + 1) {
                            VrSelectWindow vw = vrMap.get(key);
                            vw.dismiss();
                            iterator.remove();
                        }
                    }
                    if (pos != position) {
                        if (lists.size() > 0) {
                            VrSelectWindow newVrSelectWindow = new VrSelectWindow(getContext(), lists, level + 1, position);
                            newVrSelectWindow.show(binding.selectScene);
                            vrMap.put(level + 1, newVrSelectWindow);
                        } else {
                            LogUtil.log("2============AAAAAAAAAAAAAxx==============");
                            addVr(getContext(),bitmap);
                        }
                    }
                }

            }
        }, recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(vrItemAdapter);
    }

    private void addVr(Context context,Bitmap bitmap) {
        int childCount = binding.vr.getChildCount();
        if (childCount > 1) {
            binding.vr.removeViewAt(0);
        }
        GLPanorama glPanorama = new GLPanorama(context);
        binding.vr.addView(glPanorama, 0);
        glPanorama.setGLPanorama(bitmap);
        glPanorama.start();
    }

    @Override
    public void onKeyDown() {
        for (Integer key : vrMap.keySet()) {
            VrSelectWindow vrSelectWindow = vrMap.get(key);
            vrSelectWindow.dismiss();
        }
        vrMap.clear();
        Presenter.getInstance().back();
    }
}
