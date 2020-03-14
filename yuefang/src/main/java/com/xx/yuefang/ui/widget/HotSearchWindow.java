package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.SystemSetup;
import com.xx.yuefang.databinding.LayoutHotSearchBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.GsonUtil;

public class HotSearchWindow extends PopupWindow {


    private String[] hotKeyWordArray;

    public HotSearchWindow(Context context, ClickSureListener clickSureListener, ViewGroup parent) {
        super(context);
        setOutsideTouchable(true);
        setFocusable(false);
        setBackgroundDrawable(new BitmapDrawable());
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        LayoutHotSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_hot_search, parent, false);
        setContentView(binding.getRoot());
        binding.gridView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        binding.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSureListener.click(hotKeyWordArray[position]);
                dismiss();
            }
        });
        HttpUtil.getInstance().getSystemSetup().subscribe(
                str -> {
                    SystemSetup systemSetup = GsonUtil.fromJson(str, SystemSetup.class);
                    String hotKeywords = systemSetup.getData().getHotKeywords();
                    hotKeyWordArray = hotKeywords.split("\\|");
                    GridListAdapter gridListAdapter = new GridListAdapter(context, hotKeyWordArray);
                    binding.gridView.setAdapter(gridListAdapter);

                }
        );
    }


    public void show(View view) {
        showAsDropDown(view);

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

}
