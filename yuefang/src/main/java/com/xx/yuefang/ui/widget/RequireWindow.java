package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.GetPremisesList;
import com.xx.yuefang.bean.Search;
import com.xx.yuefang.bean.SearchBean;
import com.xx.yuefang.database.SearchStrOption;
import com.xx.yuefang.database.entity.SearchStr;
import com.xx.yuefang.databinding.ItemSelectPriceBinding;
import com.xx.yuefang.databinding.LayoutRequireBinding;
import com.xx.yuefang.databinding.LayoutRequireItemBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.ui.adapter.GridListAdapter;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequireWindow extends PopupWindow {

    private Context context;
    private LayoutRequireBinding binding;
    private ClickSureListener clickSureListener;

    public RequireWindow(Context context, ClickSureListener clickSureListener) {
        super(context);
        this.context = context;
        this.clickSureListener = clickSureListener;
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_require, null, false);
        setContentView(binding.getRoot());
        initView();
    }

    public void togglePrice(View view, int position) {
        ItemSelectPriceBinding itemBinding = DataBindingUtil.findBinding(view);
        if (itemBinding.getPosition() != position) {
            if (itemBinding.getPosition() == 5 || itemBinding.getPosition() == 0) {
                itemBinding.minPrice.setText("");
                itemBinding.maxPrice.setText("");
            }
            itemBinding.setPosition(position);
        }
    }


    private void initView() {
        List<List<SearchBean>> datas = initData();
        String[] titles = {"区域", "价格", "户型", "面积", "特色", "售卖状态", "开盘时间", "装修状态", "物业类型"};
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < titles.length; i++) {
            if (i == 1) {
                ItemSelectPriceBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.item_select_price, binding.llCotainer, false);
                itemBinding.setWindow(this);
                binding.llCotainer.addView(itemBinding.getRoot());
            } else {
                LayoutRequireItemBinding itemBinding = DataBindingUtil.inflate(inflater, R.layout.layout_require_item, binding.llCotainer, false);
                itemBinding.setTitle(titles[i]);
                List<SearchBean> searchBeans = datas.get(i);
                GridListAdapter gridListAdapter = new GridListAdapter(context, searchBeans, R.layout.grid_tv2);
                if (i == 0 || i == 3 || i == 5 || i == 6 || i == 7) {
                    itemBinding.grid.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                    itemBinding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            itemBinding.grid.setTag(position);
                        }
                    });
                } else {
                    HashMap<Integer, Boolean> checkMap = new HashMap<>();
                    itemBinding.grid.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                    itemBinding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            CheckedTextView ct = (CheckedTextView) view;
                            boolean checked = ct.isChecked();
                            if (checked) {
                                checkMap.put(position, true);
                            } else {
                                if (checkMap.containsKey(position)) {
                                    checkMap.remove(position);
                                }
                            }
                        }
                    });
                    itemBinding.grid.setTag(checkMap);
                }
                itemBinding.grid.setAdapter(gridListAdapter);
                binding.llCotainer.addView(itemBinding.getRoot());
            }
        }
        int childCount = binding.llCotainer.getChildCount();
        binding.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < childCount; i++) {
                    View view = binding.llCotainer.getChildAt(i);
                    if (i == 1) {
                        ItemSelectPriceBinding itemBinding = DataBindingUtil.findBinding(view);
                        int position = itemBinding.getPosition();
                        if (position != 0) {
                            if (position != 5) {
                                List<SearchBean> searchBeans = datas.get(1);
                                SearchBean searchBean = searchBeans.get(position - 1);
                                getPremisesList.setStartPrice((int) searchBean.getMinValue() * 10000);
                                getPremisesList.setEndPrice((int) searchBean.getMaxValue() * 10000);
                            } else {
                                int max = 0, min = 0;
                                String maxStr = itemBinding.maxPrice.getText().toString();
                                String minStr = itemBinding.minPrice.getText().toString();
                                if (!TextUtils.isEmpty(maxStr)) {
                                    max = Integer.parseInt(maxStr);
                                }
                                if (!TextUtils.isEmpty(minStr)) {
                                    min = Integer.parseInt(minStr);
                                }
                                getPremisesList.setStartPrice(min);
                                getPremisesList.setEndPrice(max);
                            }

                        }
                    } else {
                        GridView gridView = view.findViewById(R.id.grid);
                        if (i == 0 || i == 3 || i == 5 || i == 6 || i == 7) {
                            Integer position = (Integer) gridView.getTag();
                            if (position != null) {
                                List<SearchBean> searchBeans = datas.get(i);
                                SearchBean searchBean = searchBeans.get(position);
                                switch (i) {
                                    case 0:
                                        getPremisesList.setRegion(searchBean.getText());
                                        break;
                                    case 3:
                                        getPremisesList.setStartArea((int) searchBean.getMaxValue());
                                        getPremisesList.setEndArea((int) searchBean.getMinValue());
                                        break;
                                    case 5:
                                        getPremisesList.setState(searchBean.getValue());
                                        break;
                                    case 6:
                                        getPremisesList.setOpeningStartTime(searchBean.getStartTime());
                                        getPremisesList.setOpeningEndTime(searchBean.getEndTime());
                                        break;
                                    case 7:
                                        getPremisesList.setRenovationType(searchBean.getValue());
                                        break;
                                }
                            }
                        } else {
                            List<SearchBean> searchBeans = datas.get(i);
                            HashMap<Integer, Boolean> checkMap = (HashMap<Integer, Boolean>) gridView.getTag();
                            if (checkMap != null) {
                                ArrayList<Integer> ids = new ArrayList<>();
                                for (int position : checkMap.keySet()) {
                                    SearchBean searchBean = searchBeans.get(position);
                                    int value = searchBean.getValue();
                                    ids.add(value);
                                }
                                switch (i) {
                                    case 2:
                                        getPremisesList.setHouseTypes(ids);
                                        break;
                                    case 4:
                                        getPremisesList.setCharacteristicTypes(ids);
                                        break;
                                    case 8:
                                        getPremisesList.setPropertyTypes(ids);
                                        break;
                                }
                            }

                        }
                    }

                }
                dismiss();
                clickSureListener.clickSure();
            }
        });
        binding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int childCount = binding.llCotainer.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i == 1) {
                        View view = binding.llCotainer.getChildAt(i);
                        ItemSelectPriceBinding itemBinding = DataBindingUtil.findBinding(view);
                        itemBinding.setPosition(0);
                    } else {
                        LinearLayout item = (LinearLayout) binding.llCotainer.getChildAt(i);
                        GridView gridView = (GridView) item.getChildAt(1);
                        if (i == 0 || i == 3 || i == 5 || i == 6 || i == 7) {
                            gridView.setTag(null);
                        } else {
                            HashMap<Integer, Boolean> checkMap = (HashMap<Integer, Boolean>) gridView.getTag();
                            checkMap.clear();
                            gridView.setTag(checkMap);
                        }
                        int childCount1 = gridView.getChildCount();
                        for (int j = 0; j < childCount1; j++) {
                            gridView.setItemChecked(j, false);
                        }
                    }
                }
            }
        });

    }

    private List<List<SearchBean>> initData() {
        SearchStr searchStr = SearchStrOption.getInstance().getSearchStr();
        Search search = GsonUtil.fromJson(searchStr.getStr(), Search.class);
        Search.DataBean data = search.getData();
        ArrayList<List<SearchBean>> datas = new ArrayList<>();
        List<SearchBean> areas = new ArrayList<>();
        String[] zones = {"香洲区", "金湾区", "斗门区", "高新区", "横琴"};
        for (int i = 0; i < zones.length; i++) {
            SearchBean searchBean = new SearchBean();
            searchBean.setText(zones[i]);
            areas.add(searchBean);
        }
        List<SearchBean> prices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SearchBean searchBean = new SearchBean();
            searchBean.setMaxValue((i + 1));
            searchBean.setMinValue(i);
            prices.add(searchBean);
        }
        String[] size = {"60㎡以下", "60-80㎡", "80-100㎡", "100-120㎡", "120-140㎡","140㎡以上"};
        List<SearchBean> sizes = getSearchBeanList(size);
        String[] openTime = {"本月开盘", "下月开盘", "三个月内开盘", "六个月内开盘"};
        ArrayList<SearchBean> openTimes = getOpenTimes(openTime);
        List<SearchBean> propertyTypes = data.getPropertyTypes();
        List<SearchBean> characteristicTypes = data.getCharacteristicTypes();//特色
        List<SearchBean> houseTypes = data.getHouseTypes();//户型
        List<SearchBean> premisesStates = data.getPremisesStates();//售卖状态
        List<SearchBean> renovationTypes = data.getRenovationTypes();//装修
        datas.add(areas);
        datas.add(prices);
        datas.add(houseTypes);
        datas.add(sizes);
        datas.add(characteristicTypes);
        datas.add(premisesStates);//售卖状态
        datas.add(openTimes);
        datas.add(renovationTypes);//装修状态
        datas.add(propertyTypes);//物业类型
        return datas;
    }

    private ArrayList<SearchBean> getOpenTimes(String[] openTime) {
        ArrayList<SearchBean> searchBeans = new ArrayList<>();
        for (int i = 0; i < openTime.length; i++) {
            String date[] = null;
            if (i == 0) {
                date = TimeUtil.getDate2(0);
            } else if (i == 1) {
                date = TimeUtil.getDate2(1);
            } else if (i == 2) {
                date = TimeUtil.getDate1(3);
            } else if (i == 3) {
                date = TimeUtil.getDate1(6);
            } else if (i == 4) {
                date = TimeUtil.getDate2(-3);
            } else if (i == 5) {
                date = TimeUtil.getDate2(-6);
            }
            SearchBean searchBean = new SearchBean();
            searchBean.setText(openTime[i]);
            searchBean.setStartTime(date[0]);
            searchBean.setEndTime(date[1]);
            searchBeans.add(searchBean);
        }
        return searchBeans;
    }

    private List<SearchBean> getSearchBeanList(String[] array) {
        ArrayList<SearchBean> searchBeans = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            double MaxValue = 0, MinValue = 0;
            if (s.contains("以下")) {
                MaxValue = Double.parseDouble(getNumber(s));
                MinValue = 0;
            } else if (s.contains("以上")) {
                MinValue = Double.parseDouble(getNumber(s));
                MaxValue = 100000;
            } else if (s.contains("-")) {
                String[] split = s.split("-");
                MaxValue = Double.parseDouble(split[0]);
                MinValue = Double.parseDouble(getNumber(split[1]));
            }
            SearchBean searchBean = new SearchBean();
            searchBean.setText(s);
            searchBean.setMaxValue(MaxValue);
            searchBean.setMinValue(MinValue);
            searchBeans.add(searchBean);
        }

        return searchBeans;
    }

    private String getNumber(String s) {
        Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(s);
        String number = matcher.replaceAll("");
        return number;
    }


    private GetPremisesList getPremisesList;

    public void show(View view, GetPremisesList getPremisesList) {
        this.getPremisesList = getPremisesList;
        showAsDropDown(view);
    }


}
