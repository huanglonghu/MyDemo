package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.MapItemBean;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.databinding.FragmentMapdetailBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.strategy.MapStrategy;
import com.xx.yuefang.ui.adapter.MapItemAdapter;
import com.xx.yuefang.ui.adapter.MapLineAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.MapUtil;
import com.xx.yuefang.util.RudenessScreenHelper;

import java.util.ArrayList;
import java.util.List;

public class MapDetail extends BaseFragment {


    private FragmentMapdetailBinding binding;
    private MapUtil mapUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mapdetail, container, false);
            binding.mapView.removeViewAt(1);
            binding.mapView.showScaleControl(false);
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    private float lastY;
    private float curY;
    private float lastX;
    private float curX;

    @Override
    public void initView() {
        PremisesDetail.DataBean data = (PremisesDetail.DataBean) getArguments().getSerializable("dataBean");
        String[] split = data.getRegionalLocation().split(",");
        mapUtil = new MapUtil(new MapStrategy() {
            @Override
            public void onChangePosition(int position) {

            }
            @Override
            public void onSearchResult(int[] counts) {

            }

            @Override
            public void refreshMapView(PoiResult result, int type) {
                show(result, type);
            }
        });
        MapStatus.Builder builder = new MapStatus.Builder();
        float zoom = 17f; // 地图缩放级别
        LatLng latLng = new LatLng(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
        builder.target(latLng).zoom(zoom);
        binding.mapView.showZoomControls(false);
        binding.mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        String[] typeName = {"公交站", "学校", "银行", "餐饮", "医院", "超市"};
        mapUtil.initMapView(data.getPremisesName(), split, binding.mapView, getContext(), typeName);
        ArrayList<MapItemBean> datas = new ArrayList<>();
        for (int i = 0; i < resArray.length; i++) {
            MapItemBean mapItemBean = new MapItemBean(typeName[i], resArray[i]);
            datas.add(mapItemBean);
        }
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_a));
        binding.rv.addItemDecoration(divider);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false);
        binding.rv.setLayoutManager(linearLayoutManager);

        MapItemAdapter mapItemAdapter = new MapItemAdapter(getContext(), datas, new ClickSureListener() {
            @Override
            public void click(int position) {
                mapUtil.nearSerach(position);
            }
        }, binding.rv);
        binding.rv.setAdapter(mapItemAdapter);


    }

    private int resArray[] = {R.drawable.bg_map_icon1, R.drawable.bg_map_icon2, R.drawable.bg_map_icon3,
            R.drawable.bg_map_icon4, R.drawable.bg_map_icon5, R.drawable.bg_map_icon6
    };

    int[] iocnArray = {R.drawable.icon_bus, R.drawable.icon_school, R.drawable.icon_bank,
            R.drawable.icon_food, R.drawable.icon_hospital, R.drawable.icon_mall};


    @Override
    public void initlisten() {

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

        binding.rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                curY = event.getRawY();
                curX = event.getRawX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastY = event.getRawY();
                        lastX = event.getRawX();
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_UP:
                        float d = curY - lastY;
                        float d1 = Math.abs(d);
                        float d2 = Math.abs(curX - lastX);
                        if (d1 > d2) {
                            if (d1 > 5) {
                                translet(d);
                                lastY = curY;
                            }
                            return true;
                        }

                }
                return false;
            }
        });
        BaiduMap map = binding.mapView.getMap();

        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                TextView textView = new TextView(getContext());
                textView.setPadding(20, 10, 20, 10);
                textView.setText(marker.getTitle());
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(R.drawable.shape_k);
                textView.setGravity(Gravity.CENTER);
                InfoWindow infoWindow = new InfoWindow(textView, marker.getPosition(), -100);
                map.showInfoWindow(infoWindow);
                return true;
            }
        });
    }


    private void show(PoiResult result, int type) {
        BaiduMap map = binding.mapView.getMap();
        map.clear();
        mapUtil.showMarker();
        if (result != null) {
            List<PoiInfo> allPoi = result.getAllPoi();
            if (allPoi != null && allPoi.size() > 0) {
                if (getContext() != null) {
                    MapLineAdapter mapLineAdapter = new MapLineAdapter(getActivity(), allPoi, R.layout.list_line_item);
                    binding.lvMapLine.setAdapter(mapLineAdapter);
                    for (int i = 0; i < allPoi.size(); i++) {
                        PoiInfo poiInfo = allPoi.get(i);
                        LatLng location = poiInfo.getLocation();
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), iocnArray[type]);
                        Bitmap bitmap1 = ImagUtil.zoomImg(bitmap, 100, 100);
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap1);
                        OverlayOptions option = new MarkerOptions()
                                .position(location)
                                .visible(true)
                                .draggable(false)
                                .icon(bitmapDescriptor);
                        Marker marker = (Marker) map.addOverlay(option);
                        marker.setTitle(poiInfo.getName());
                    }
                }
            }
        }
    }

    public boolean isOpen;


    public void translet(double d) {
        if (isOpen) {
            if (d > 0) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ll, "translationY", 0);
                AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
                animator.setInterpolator(accelerateInterpolator);
                animator.setDuration(500);
                animator.start();
                isOpen = !isOpen;
            }
        } else {
            if (d < 0) {
                float v = RudenessScreenHelper.pt2px(getContext(), 353);
                ObjectAnimator animator = ObjectAnimator.ofFloat(binding.ll, "translationY", -v);
                AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator();
                animator.setInterpolator(accelerateInterpolator);
                animator.setDuration(500);
                animator.start();
                isOpen = !isOpen;
            }
        }
    }


}
