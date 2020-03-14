package com.xx.yuefang.ui.fragment.chat;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.ChatLocationMsg2Binding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.ImagUtil;

public class ChatLocationMsg2Fragment extends BaseFragment {

    private ChatLocationMsg2Binding binding;
    private LatLng latLng;
    private String adress;
    private BaiduMap map;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_location_msg2, container, false);
        initView();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        binding.mapView.showZoomControls(false);
        map = binding.mapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        map.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        Bundle bundle = getArguments();
        double[] positions = bundle.getDoubleArray("position");
        adress = bundle.getString("adress");
        latLng = new LatLng(positions[0], positions[1]);
        MapStatusUpdate status2 = MapStatusUpdateFactory.newLatLng(latLng);
        map.setMapStatus(status2);
        binding.adress.setText(adress);
        showMarker();
    }

    public void showMarker() {
        Bitmap viewBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.locationpoint);
        Bitmap bitmap = ImagUtil.zoomImg(viewBitmap, 60, 100);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
        OverlayOptions option = new MarkerOptions()
                .position(latLng)
                .visible(true)
                .draggable(false)
                .icon(bd);
        Marker marker = (Marker) map.addOverlay(option);
        marker.setToTop();
    }


    @Override
    public void initlisten() {

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

        binding.lcoation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lcoation.setSelected(true);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                binding.mapView.getMap().animateMapStatus(update);
            }
        });

        map.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        LatLng target = map.getMapStatus().target;
                        //如果地图中心坐标发生改变 则重新搜寻
                        if (target.latitude != latLng.latitude || target.longitude != latLng.longitude) {
                            //重新搜索该坐标附近内容
                            binding.lcoation.setSelected(false);
                        }
                        break;
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            binding.mapView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.mapView.onDestroy();
        }
    }


}
