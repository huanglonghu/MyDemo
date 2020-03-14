package com.xx.yuefang.overlayutil;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiResult;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.MapPoioverlayBinding;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示poi的overly
 */
public class PoiOverlay extends OverlayManager {

    private static final int MAX_POI_SIZE = 10;

    private PoiResult mPoiResult = null;
    private int type;
    int[] iocnArray = {R.drawable.icon_bus, R.drawable.icon_school, R.drawable.icon_bank,
            R.drawable.icon_food, R.drawable.icon_hospital, R.drawable.icon_mall};
    /**
     * 构造函数
     *
     * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
     */
    private Context context;

    public PoiOverlay(BaiduMap baiduMap, int type, Context context) {
        super(baiduMap);
        this.type = type;
        this.context = context;
    }

    /**
     * 设置POI数据
     *
     * @param poiResult 设置POI数据
     */
    public void setData(PoiResult poiResult) {
        this.mPoiResult = poiResult;
    }

    @Override
    public final List<OverlayOptions> getOverlayOptions() {
        if (mPoiResult == null || mPoiResult.getAllPoi() == null) {
            return null;
        }

        List<OverlayOptions> markerList = new ArrayList<>();
        int markerSize = 0;
        List<PoiInfo> allPoi = mPoiResult.getAllPoi();
        for (int i = 0; i < allPoi.size() && markerSize < MAX_POI_SIZE; i++) {
            if (mPoiResult.getAllPoi().get(i).location == null) {
                continue;
            }
            markerSize++;
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), iocnArray[type]);
            MapPoioverlayBinding mapPoioverlayBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.map_poioverlay, null, false);
            mapPoioverlayBinding.ivPoi.setImageBitmap(bitmap);
            Bitmap b = ImagUtil.getViewBitmap(mapPoioverlayBinding.getRoot());
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(b);
            markerList.add(new MarkerOptions()
                    .icon(bitmapDescriptor)
                    .extraInfo(bundle)
                    .position(mPoiResult.getAllPoi().get(i).location));

        }

        return markerList;
    }

    /**
     * 获取该PoiOverlay的poi数据
     *
     * @return POI数据
     */
    public PoiResult getPoiResult() {
        return mPoiResult;
    }

    /**
     * 覆写此方法以改变默认点击行为
     *
     * @param i 被点击的poi在
     *          {@link PoiResult#getAllPoi()} 中的索引
     * @return true--事件已经处理，false--事件未处理
     */
    public boolean onPoiClick(int i) {
//        if (mPoiResult.getAllPoi() != null
//                && mPoiResult.getAllPoi().get(i) != null) {
//            Toast.makeText(BMapManager.getInstance().getContext(),
//                    mPoiResult.getAllPoi().get(i).name, Toast.LENGTH_LONG)
//                    .show();
//        }
        return false;
    }

    @Override
    public final boolean onMarkerClick(Marker marker) {
        if (!mOverlayList.contains(marker)) {
            return false;
        }

        if (marker.getExtraInfo() != null) {
            return onPoiClick(marker.getExtraInfo().getInt("index"));
        }

        return false;
    }

    @Override
    public boolean onPolylineClick(Polyline polyline) {
        return false;
    }
}
