package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.MapItemBean;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.databinding.MapLineItemBinding;
import com.xx.yuefang.databinding.WrapperLocationEnvironmenBinding;
import com.xx.yuefang.overlayutil.PoiOverlay;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.strategy.MapStrategy;
import com.xx.yuefang.ui.adapter.MapItemAdapter2;
import com.xx.yuefang.ui.fragment.house.houseDetail.MapDetail;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.MapUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationEnvironmenWrapper extends Wrapper {

    private WrapperLocationEnvironmenBinding binding;
    private MapUtil mapUtil;
    private ArrayList<MapItemBean> datas = new ArrayList<>();
    private MapItemAdapter2 mapItemAdapter;


    public LocationEnvironmenWrapper(Context context){
        super(context);
    }


    public LocationEnvironmenWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    View initView() {
        if (binding == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_location_environmen, this, false);
            binding.mapView.removeViewAt(1);
            binding.mapView.setMapCustomEnable(false);
            binding.mapView.showScaleControl(false);
        }
        return binding.getRoot();
    }

    public void onResume() {
        binding.mapView.onResume();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        String[] typeName = {"公交站", "学校", "银行", "餐饮", "医院", "超市"};
        if (mapUtil == null) {
            mapUtil = new MapUtil(new MapStrategy() {
                @Override
                public void onChangePosition(int position){}
                @Override
                public void onSearchResult(int[] counts) {
                    for (int i = 0; i < counts.length; i++) {
                        MapItemBean mapItemBean = datas.get(i);
                        mapItemBean.setCount(counts[i]);
                    }
                    mapItemAdapter.notifyDataSetChanged();
                }
                @Override
                public void refreshMapView(PoiResult result, int type) {
                    show(result, type);
                }
            });
            for (int i = 0; i < typeName.length; i++) {
                MapItemBean mapItemBean = new MapItemBean();
                mapItemBean.setName(typeName[i]);
                datas.add(mapItemBean);
            }
            DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
            divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_a));
            binding.rv.addItemDecoration(divider);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), OrientationHelper.HORIZONTAL, false);
            binding.rv.setLayoutManager(linearLayoutManager);
            mapItemAdapter = new MapItemAdapter2(getContext(), datas, new ClickSureListener() {
                @Override
                public void click(int position) {
                    mapUtil.nearSerach(position);
                }
            }, binding.rv);
            binding.rv.setAdapter(mapItemAdapter);
            BaiduMap map = binding.mapView.getMap();
            UiSettings uiSettings = map.getUiSettings();
            uiSettings.setScrollGesturesEnabled(false);
            binding.setMapUtil(mapUtil);
            String[] split = data.getRegionalLocation().split(",");
            mapUtil.initMapView(data.getPremisesName(), split, binding.mapView, context, typeName);
        }

        binding.lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapDetail mapDetail = new MapDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataBean", data);
                mapDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(mapDetail, "mapDetail");
            }
        });
        binding.mapView.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MapDetail mapDetail = new MapDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataBean", data);
                mapDetail.setArguments(bundle);
                Presenter.getInstance().step2fragment(mapDetail, "mapDetail");
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    private void show(PoiResult result, int type) {
        BaiduMap map = binding.mapView.getMap();
        map.clear();
        binding.llContain.removeAllViews();
        mapUtil.showMarker();
        if (result != null) {
            PoiOverlay poiOverlay = new PoiOverlay(map, type, getContext());
            poiOverlay.setData(result);
            poiOverlay.addToMap();
            poiOverlay.zoomToSpan();
            List<PoiInfo> allPoi = result.getAllPoi();
            if (allPoi != null) {
                for (int i = 0; i < allPoi.size(); i++) {
                    if (i < 3) {
                        MapLineItemBinding binding1 = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.map_line_item, binding.llContain, false);
                        PoiInfo poiInfo = allPoi.get(i);
                        binding1.setPoint(poiInfo);
                        binding.llContain.addView(binding1.getRoot());
                    } else {
                        break;
                    }
                }
            }
        }
    }


}
