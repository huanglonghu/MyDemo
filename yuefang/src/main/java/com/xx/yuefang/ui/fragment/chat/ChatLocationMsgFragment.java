package com.xx.yuefang.ui.fragment.chat;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.google.gson.Gson;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.ChatLocationMsgBinding;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.LocationMsgAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatLocationMsgFragment extends BaseFragment {

    private ChatLocationMsgBinding binding;
    private LocationMsgAdapter locationMsgAdapter;
    private List<PoiInfo> allPoi;
    private LatLng latLng;
    private String adress;
    private BaiduMap map;
    private LocationClient locationClient;
    private String city;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.chat_location_msg, container, false);
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
        binding.mapView.showZoomControls(false);
        map = binding.mapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        map.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        allPoi = new ArrayList<>();
        locationMsgAdapter = new LocationMsgAdapter(getContext(), allPoi, 0);
        binding.lvLocationMsg.setAdapter(locationMsgAdapter);
    }


    private void initGeoCoder(LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                }
                allPoi.clear();
                locationMsgAdapter.clearView();
                suggestSearch(latLng);
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).pageNum(0).pageSize(100));
        geoCoder.destroy();
    }


    private void suggestSearch(LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                } else {
                    List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
                    if (poiList != null && poiList.size() > 0) {
                        locationMsgAdapter.setSelectPosition(0);
                        allPoi.addAll(poiList);
                        locationMsgAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).radius(5000));
        geoCoder.destroy();
    }


    private void suggestSearch2(PoiInfo poi) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    return;
                } else {
                    List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
                    if (poiList != null && poiList.size() > 0) {
                        locationMsgAdapter.setSelectPosition(0);
                        String uid = poi.getUid();
                        for (int i = 0; i < poiList.size(); i++) {
                            PoiInfo poiInfo = poiList.get(i);
                            if(!poiInfo.getUid().equals(uid)){
                                allPoi.add(poiInfo);
                            }
                        }
                        locationMsgAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(poi.getLocation()).radius(5000));

        geoCoder.destroy();
    }


    @Override
    public void initlisten() {

        initLocationOption();

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng location = null;
                String adress = null;
                String name = null;
                if (locationMsgAdapter.getCount() != 0) {
                    int selectPosition = locationMsgAdapter.getSelectPosition();
                    PoiInfo poiInfo = allPoi.get(selectPosition);
                    adress = poiInfo.getAddress();
                    location = poiInfo.getLocation();
                    name = poiInfo.getName();
                } else {
                    adress = ChatLocationMsgFragment.this.adress;
                    location = latLng;
                    name = buildName;
                }
                EventData eventData = new EventData(UserObservable.TYPE_SELECT_LOCATION);
                Bundle bundle = new Bundle();
                LogUtil.log("==============location=============="+location);
                if (location != null) {
                    bundle.putDoubleArray("location", new double[]{location.latitude, latLng.longitude});
                    bundle.putString("name", name);
                    bundle.putString("address", adress);
                    eventData.setData(bundle);
                    UserObservable.getInstance().notifyObservers(eventData);
                    Presenter.getInstance().back();
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

        binding.lcoation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allPoi.clear();
                locationMsgAdapter.clearView();
                PoiInfo poiInfo = new PoiInfo();
                poiInfo.setLocation(latLng);
                poiInfo.setName(adress);
                allPoi.add(poiInfo);
                binding.lcoation.setSelected(true);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                binding.mapView.getMap().animateMapStatus(update);
                locationMsgAdapter.setSelectPosition(0);
                suggestSearch(latLng);
            }
        });

        binding.serch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationSearch locationSearch = new LocationSearch();
                Bundle bundle = new Bundle();
                bundle.putString("city", city);
                locationSearch.setArguments(bundle);
                Presenter.getInstance().step2fragment(locationSearch, "locationSearch");
            }
        });

        binding.lvLocationMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationMsgAdapter.refreshSelectPosition(position);
                binding.lcoation.setSelected(false);
                PoiInfo poiInfo = allPoi.get(position);
                LatLng location = poiInfo.getLocation();
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(location);
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
                            initGeoCoder(target);
                        }
                        break;
                }
            }
        });

        UserObserver<EventData> userObserver = new UserObserver<EventData>() {
            @Override
            public void onUpdate(UserObservable<EventData> observable, EventData data) {
                if (data.getEventType() == UserObservable.TYPE_LOCATION_SEARCH) {
                    Bundle bundle = data.getData();
                    PoiInfo point = bundle.getParcelable("point");
                    MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
                    map.setMapStatus(status);
                    allPoi.clear();
                    allPoi.add(point);
                    locationMsgAdapter.clearView();
                    suggestSearch2(point);
                }
            }
        };
        UserObservable.getInstance().register(userObserver);
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
        if (locationClient != null) {
            locationClient.stop();
        }
    }


    public void initLocationOption() {
        locationClient = new LocationClient(YueFangApplication.getApplication());
        LocationClientOption locationOption = new LocationClientOption();
        MyLocationListener myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);
        locationOption.setIgnoreKillProcess(true);
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationOption.setCoorType("gcj02");
        locationOption.setIsNeedAddress(true);
        locationOption.setOpenGps(true);
        locationClient.setLocOption(locationOption);
        locationClient.start();
    }


    private String buildName;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            city = location.getCity();
            latLng = new LatLng(latitude, longitude);
            adress = location.getAddrStr();
            buildName = location.getBuildingName();
            MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(latLng);
            map.setMapStatus(status);
            suggestSearch(latLng);
        }
    }


}
