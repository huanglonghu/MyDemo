package com.xx.yuefang.strategy;

import com.baidu.mapapi.search.poi.PoiResult;

public interface MapStrategy {

    public abstract void onChangePosition(int position);

    public abstract void onSearchResult(int[] counts);

    public abstract void refreshMapView(PoiResult result, int type);


}
