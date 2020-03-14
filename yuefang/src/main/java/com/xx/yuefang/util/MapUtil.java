package com.xx.yuefang.util;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.view.LayoutInflater;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.MapMarkerBinding;
import com.xx.yuefang.strategy.MapStrategy;

import java.util.HashMap;
import java.util.List;

public class MapUtil {
    private LatLng cenpt;
    private Context context;
    private MapStrategy mapStrategy;
    private String pointName;
    private String[] typeName;
    private BaiduMap map;

    public MapUtil(MapStrategy mapStrategy) {
        this.mapStrategy = mapStrategy;
    }

    public void initMapView(String pointName, String[] split, MapView mapView, Context context, String[] typeName) {
        this.pointName = pointName;
        this.context = context;
        this.typeName = typeName;
        map = mapView.getMap();
        //设定中心点坐标
            cenpt = bd09_To_Gcj02(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
        serach(0);
    }

    public double pi = 3.141592653589793 * 3000.0 / 180.0;
    public LatLng bd09_To_Gcj02(double bd_lon, double bd_lat) {
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * pi);
        double gg_lon = z * Math.cos(theta);
        double gg_lat = z * Math.sin(theta);
        return new LatLng(gg_lon, gg_lat);
    }


    public void showMarker() {
        MapMarkerBinding markerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.map_marker, null, false);
        markerBinding.name.setText(pointName);
        Bitmap viewBitmap = ImagUtil.getViewBitmap(markerBinding.getRoot());
        BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(viewBitmap);
        OverlayOptions option = new MarkerOptions()
                .position(cenpt)
                .visible(true)
                .draggable(false)
                .icon(bd);
        Marker marker = (Marker) map.addOverlay(option);
        marker.setToTop();
    }


    private void serach(int position) {
        PoiSearch poiSearch = PoiSearch.newInstance();
        //创建poi监听者
        SearchListener poiListener = new SearchListener(position);
        poiSearch.setOnGetPoiSearchResultListener(poiListener);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .location(cenpt)//检索位置
                .keyword(typeName[position])
                .pageCapacity(20)//设置每页容量，默认10条
                .radius(2000);//附近检索半径
        poiSearch.searchNearby(nearbySearchOption);
        //释放检索对象
        poiSearch.destroy();
    }


    public void nearSerach(int position) {
        mapStrategy.onChangePosition(position);
        PoiResult poiResult = dataMap.get(position);
        mapStrategy.refreshMapView(poiResult, position);

    }

    private class SearchListener implements OnGetPoiSearchResultListener {

        private int index;

        public SearchListener(int index) {
            this.index = index;
        }

        @Override
        public void onGetPoiResult(PoiResult result) {
            //获取POI检索结果
            if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
                LogUtil.log("未找到结果");
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
                if (result != null) {
                    List<PoiInfo> allPoi = result.getAllPoi();
                    counts[index] = allPoi.size();
                    for (int i = 0; i < allPoi.size(); i++) {
                        PoiInfo poiInfo = allPoi.get(i);
                        double distance = DistanceUtil.getDistance(cenpt, poiInfo.getLocation());
                        poiInfo.setDistance((int) distance);
                    }
                    dataMap.put(index, result);

                }
            }
            if (index == 5) {
                mapStrategy.onSearchResult(counts);
                mapStrategy.refreshMapView(dataMap.get(0), 0);
            }
            if (index < 5) {
                serach(index + 1);
            }

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
        }
    }

    private HashMap<Integer, PoiResult> dataMap = new HashMap<>();
    private int[] counts = new int[6];


}
