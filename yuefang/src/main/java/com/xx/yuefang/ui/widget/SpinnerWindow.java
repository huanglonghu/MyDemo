package com.xx.yuefang.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.xx.yuefang.R;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.RudenessScreenHelper;

public class SpinnerWindow extends PopupWindow {

    public SpinnerWindow(Context context, String[] datas, ClickSureListener clickSureListener) {
        int width = (int) RudenessScreenHelper.pt2px(context, 165);
        setWidth(width);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        ListView listView = new ListView(context);
        Drawable divider=context.getDrawable(R.drawable.divider_c);
        listView.setDivider(divider);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.item_spinner, datas);
        listView.setAdapter(adapter);
        setContentView(listView);
        setOutsideTouchable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                clickSureListener.click(position);
            }
        });
    }


}
