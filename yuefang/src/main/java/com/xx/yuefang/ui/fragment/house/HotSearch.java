package com.xx.yuefang.ui.fragment.house;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemSetup;
import com.xx.yuefang.database.SystemDataOption;
import com.xx.yuefang.database.entity.SystemData;
import com.xx.yuefang.databinding.FragmentHotSearchBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

public class HotSearch extends BaseFragment {

    private FragmentHotSearchBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hot_search, container, false);
        initView();
        initData();
        initlisten();
        return binding.getRoot();
    }

    private String[] hotKeyWordArray;

    @Override
    public void initData() {
        SystemData systemData = SystemDataOption.getInstance().querrySystemData();
        String hotKeywords = systemData.getHotKeywords();
        hotKeyWordArray = hotKeywords.split("\\|");
        GridListAdapter gridListAdapter = new GridListAdapter(getContext(), hotKeyWordArray);
        binding.gridView.setAdapter(gridListAdapter);
    }

    @Override
    public void initView() {
        String content = getArguments().getString("content");
        binding.searchEt.setText(content);
    }

    @Override
    public void initlisten() {

        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectKeyWordBack.selectKeyWord(hotKeyWordArray[position]);
                Presenter.getInstance().back();
            }
        });

        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = binding.searchEt.getText().toString();
                selectKeyWordBack.selectKeyWord(content);
                Presenter.getInstance().back();
            }
        });


        binding.searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = binding.searchEt.getText().toString();
                    selectKeyWordBack.selectKeyWord(content);
                    Presenter.getInstance().back();
                    return true;
                }
                return false;
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
            TextView t = (TextView) LayoutInflater.from(context).inflate(R.layout.grid_tv, parent, false);
            t.setText(array[position]);
            convertView = t;
            return convertView;
        }
    }


    private SelectKeyWordBack selectKeyWordBack;

    public interface SelectKeyWordBack {
        void selectKeyWord(String keyWord);
    }

    public void setSelectKeyWordBack(SelectKeyWordBack selectKeyWordBack) {
        this.selectKeyWordBack = selectKeyWordBack;
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


    @Override
    public void onKeyDown() {
        super.onKeyDown();
        String content = binding.searchEt.getText().toString();
        selectKeyWordBack.selectKeyWord(content);
        Presenter.getInstance().back();
    }
}
