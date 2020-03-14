package com.xx.yuefang.ui.fragment.chat;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.LocationSearchBinding;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.LocationMsgAdapter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationSearch extends BaseFragment {

    private LocationSearchBinding binding;
    private ArrayList<PoiInfo> datas;
    private LocationMsgAdapter locationMsgAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.location_search, container, false);
        initlisten();
        return binding.getRoot();
    }


    @Override
    public void initData() {}

    @Override
    public void initView() {}

    @Override
    public void initlisten() {
        datas = new ArrayList<>();
        locationMsgAdapter = new LocationMsgAdapter(getContext(), datas, 0);
        binding.lvLocationSearch.setAdapter(locationMsgAdapter);
        Bundle arguments = getArguments();
        String city = arguments.getString("city");
        binding.lvLocationSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiInfo poiInfo = datas.get(position);
                Bundle bundle = new Bundle();
                LatLng location = poiInfo.getLocation();
                bundle.putParcelable("point",poiInfo);
                EventData eventData = new EventData(UserObservable.TYPE_LOCATION_SEARCH);
                eventData.setData(bundle);
                UserObservable.getInstance().notifyObservers(eventData);
                Presenter.getInstance().back();
            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                datas.clear();
                locationMsgAdapter.clearView();
                locationMsgAdapter.notifyDataSetChanged();
                if (!TextUtils.isEmpty(s)) {
                    search(s.toString(), city);
                }
            }
        });
    }


    public void search(String keyWord, String city) {
        PoiSearch poiSearch = PoiSearch.newInstance();
        OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<PoiInfo> allPoi = poiResult.getAllPoi();
                    if (allPoi != null && allPoi.size() > 0) {
                        datas.addAll(allPoi);
                        locationMsgAdapter.notifyDataSetChanged();
                    }
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
        };
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        poiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(keyWord).scope(2).cityLimit(true));
        poiSearch.destroy();
    }

}
