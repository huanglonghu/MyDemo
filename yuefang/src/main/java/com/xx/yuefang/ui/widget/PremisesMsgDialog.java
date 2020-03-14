package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesMsg;
import com.xx.yuefang.databinding.DialogPremisesmsgBinding;
import com.xx.yuefang.databinding.ItemPremisesmsgBinding;
import com.xx.yuefang.strategy.ClickSureListener;
import java.util.List;

public class PremisesMsgDialog extends Dialog {
    private List<PremisesMsg.DataBean> data;
    private LayoutInflater layoutInflater;
    private ClickSureListener clickSureListener;

    public PremisesMsgDialog(Context context, List<PremisesMsg.DataBean> data, ClickSureListener clickSureListener) {
        super(context);
        this.data = data;
        this.clickSureListener = clickSureListener;
        layoutInflater = LayoutInflater.from(context);
        DialogPremisesmsgBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_premisesmsg, null, false);
        PremisesMsgAdapter adapter = new PremisesMsgAdapter();
        binding.lvPremises.setAdapter(adapter);
        setContentView(binding.getRoot());
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.6f;
        getWindow().setAttributes(layoutParams);
    }

    private class PremisesMsgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
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
            ItemPremisesmsgBinding itemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_premisesmsg, parent, false);
            PremisesMsg.DataBean dataBean = data.get(position);
            itemBinding.setData(dataBean);
            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickSureListener.click(position);
                    dismiss();
                }
            });
            return itemBinding.getRoot();
        }
    }
}
