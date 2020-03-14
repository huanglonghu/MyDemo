package com.xx.yuefang.ui.fragment.house;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.HouseMessage;
import com.xx.yuefang.bean.HouseTypeDescribe;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.FragmentHousetypeDetailBinding;
import com.xx.yuefang.databinding.LayoutDescribeBinding;
import com.xx.yuefang.databinding.LayoutDescribeExpendBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.observable.UserObserver;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.adapter.GridListAdapter2;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.ui.fragment.house.houseDetail.PictureDetail2;
import com.xx.yuefang.ui.fragment.news.Link;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HouseTypeDetailFragment extends BaseFragment {

    private FragmentHousetypeDetailBinding binding;
    private PremisesDetail.DataBean.HouseTypeInfosBean houseTypeInfo;
    private String url;
    private boolean isFromList;
    private int selectPosition;
    private GridListAdapter2 gridListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_housetype_detail, container, false);
            binding.setPresenter(Presenter.getInstance());
            UserBean userBean = UserOption.getInstance().querryUser();
            if (userBean != null) {
                binding.setUserType(userBean.getUserType());
            }
            initData();
            initView();
            initlisten();
        }
        return binding.getRoot();
    }

    private PremisesDetail.DataBean dataBean;

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        dataBean = (PremisesDetail.DataBean) bundle.getSerializable("data");
        isFromList = bundle.getBoolean("isFromList");
        binding.setData(dataBean);
        houseTypeInfo = (PremisesDetail.DataBean.HouseTypeInfosBean) bundle.getSerializable("houseTypeInfo");
        String describe = houseTypeInfo.getDescribe();
        String s = "{" + "\"Describe\": " + describe + "}";
        HouseTypeDescribe houseTypeDescribe = GsonUtil.fromJson(s, HouseTypeDescribe.class);
        List<HouseTypeDescribe.DescribeBean> describes = houseTypeDescribe.getDescribe();
        if (describes != null && describes.size() > 0) {
            for (int i = 0; i < describes.size(); i++) {
                HouseTypeDescribe.DescribeBean describeBean = describes.get(i);
                if (i % 2 == 0) {
                    LayoutDescribeBinding binding1 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_describe, binding.container1, false);
                    binding1.setDescribe(describeBean);
                    binding.container1.addView(binding1.getRoot());
                } else {
                    LayoutDescribeBinding binding2 = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_describe, binding.container2, false);
                    binding2.setDescribe(describeBean);
                    binding.container2.addView(binding2.getRoot());
                }
            }
        }

        HttpUtil.getInstance().getMoreHouseMessageById(dataBean.getId()).subscribe(
                str -> {
                    HouseMessage houseMessage = GsonUtil.fromJson(str, HouseMessage.class);
                    HouseMessage.DataBean data = houseMessage.getData();
                    binding.setBean(data);
                }
        );
        binding.setHouseTypeInfo(houseTypeInfo);
        String picture = houseTypeInfo.getPicture();
        url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(getContext()).load(url).into(binding.img, 1);
        }
        String characteristic = houseTypeInfo.getCharacteristic();
        if (!TextUtils.isEmpty(characteristic)) {
            String[] characteristics = characteristic.split(",");
            for (int i = 0; i < characteristics.length; i++) {
                TextView item;
                item = (TextView) View.inflate(getContext(), R.layout.layout_character2, null);
                item.setText(characteristics[i]);
                binding.llExpend.addView(item, i);
            }
        }

    }

    @Override
    public void initView() {
        ArrayList<String> typeArray = getArguments().getStringArrayList("houseTypes");
        selectPosition = getArguments().getInt("selectPosition");
        gridListAdapter = new GridListAdapter2(getContext(), typeArray, R.layout.layout_select_housetype, selectPosition);
        binding.grid.setAdapter(gridListAdapter);
    }

    @Override
    public void initlisten() {
        binding.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureDetail2 pictureDetail2 = new PictureDetail2();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                pictureDetail2.setArguments(bundle);
                Presenter.getInstance().step2fragment(pictureDetail2, "picture2");
            }
        });

        binding.ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vrUrl = houseTypeInfo.getVRUrl();
                if (!TextUtils.isEmpty(vrUrl)) {
                    Link link = new Link();
                    Bundle bundle = new Bundle();
                    bundle.putString("url", vrUrl);
                    link.setArguments(bundle);
                    Presenter.getInstance().step2fragment(link, "link");
                } else {
                    Toast.makeText(getContext(), "暂无VR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = position;
                gridListAdapter.refreshCheckPosition(position);
                if (isFromList) {
                    EventData eventData = new EventData(UserObservable.TYPE_HOUSETYPEDETAL_BACK);
                    Bundle bundle = new Bundle();
                    bundle.putInt("selectPosition", position);
                    eventData.setData(bundle);
                    UserObservable.getInstance().notifyObservers(eventData);
                    Presenter.getInstance().back();
                } else {
                    HouseTypeFragment houseTypeFragment = new HouseTypeFragment();
                    houseTypeFragment.setPremisesData(dataBean);
                    ArrayList<String> houseTypes = (ArrayList<String>) dataBean.getHouseTypes();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("houseTypes", houseTypes);
                    bundle.putInt("selectPosition", position);
                    houseTypeFragment.setArguments(bundle);
                    Presenter.getInstance().step2fragment(houseTypeFragment, "houseType");
                }

            }
        });


        binding.toConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().selectSalePerson(dataBean);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(), false);
    }

}
