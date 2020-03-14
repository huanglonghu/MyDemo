package com.xx.yuefang.ui.fragment.house;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.FragmentSelectCityBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class SelectCity extends BaseFragment {

    private FragmentSelectCityBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_city, container, false);
        binding.setPresenter(Presenter.getInstance());
        initView();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        String[] strings = {"珠海", "+即将开放"};
        GridListAdapter gridListAdapter = new GridListAdapter(getActivity(), strings);
        binding.cityGrid.setAdapter(gridListAdapter);
    }

    @Override
    public void initlisten() {
        binding.cityGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Presenter.getInstance().back();
                }
            }
        });
    }

    private class GridListAdapter extends BaseAdapter {

        private String[] array;
        private Context context;

        public GridListAdapter(Context context, String[] array) {
            this.array = array;
            this.context = context;
        }


        @Override
        public int getCount() {
            return array == null ? 0 : array.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CheckedTextView t = (CheckedTextView) LayoutInflater.from(context).inflate(R.layout.grid_tv3, parent, false);
            t.setText(array[position]);
            if (position == 0) {
                t.setChecked(true);
            }
            convertView = t;
            return convertView;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),true);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatusBarUtil.setStatusBarDarkTheme(getActivity(),false);
    }

}
