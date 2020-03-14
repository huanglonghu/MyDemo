package com.xx.yuefang.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.tencent.connect.share.QQShare;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.constant.HttpParam;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.LayoutSharedialogBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.YueFangApplication;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.ThridPartLogin;

import java.util.List;

public class ShareDialog extends Dialog {
    private PremisesDetail.DataBean dataBean;
    private Context context;
    private int type;
    private Bundle datas;

    public ShareDialog(Context context, int type, PremisesDetail.DataBean dataBean, Bundle datas) {
        super(context, R.style.dialog3);
        this.context = context;
        this.type = type;
        this.dataBean = dataBean;
        this.datas = datas;
        LayoutSharedialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_sharedialog, null, false);
        setContentView(binding.getRoot());
        binding.setShareDialog(this);
        setCanceledOnTouchOutside(true);
        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public void share(int type, int scene) {
        UserBean userBean = UserOption.getInstance().querryUser();
        if (userBean == null && this.type == 1) {
            dismiss();
            Presenter.getInstance().step2Fragment("login");
            return;
        }
        Bundle bundle;
        if (this.type == 1) {
            bundle = initData(scene);
        } else {
            datas.putInt("scene", scene);
            bundle = datas;
            bundle.putInt("type", 2);
        }
        switch (type) {
            case 1:
                ThridPartLogin.getInstance().wxShare(context, bundle);
                break;
            case 2:
                ThridPartLogin.getInstance().wxShare(context, bundle);
                break;
            case 3:
                ThridPartLogin.getInstance().qqShare(bundle);
                break;
            case 4://
                ThridPartLogin.getInstance().qqShare(bundle);
                break;
            case 5://facebook
                ThridPartLogin.getInstance().facebookShare(bundle);
                break;
            case 6://微信收藏
                ThridPartLogin.getInstance().wxShare(context, bundle);
                break;
        }
    }

    private Bundle initData(int scene) {
        Bundle params = new Bundle();
        params.putString("title", dataBean.getPremisesName());//标题
        List<String> characteristics = dataBean.getCharacteristics();
        StringBuffer sb = new StringBuffer();
        if (characteristics != null && characteristics.size() > 0) {
            for (int i = 0; i < characteristics.size(); i++) {
                sb.append(characteristics.get(i) + " ");
            }
        }
        String describe = sb.toString() + "\n" + dataBean.getAddress() + "\n" + dataBean.getUnitPrice() + "元/㎡" + "\n";
        params.putString("description", describe);//描述
        UserBean userBean = UserOption.getInstance().querryUser();
        String url = HttpParam.baseUrl + "/house/index/" + dataBean.getId() + "-" + userBean.getId() + "-" + userBean.getUserType();
        params.putString("webUrl", url);//连接
        String picture = ImagUtil.handleUrl(dataBean.getListPicture());
        params.putString("picture", picture);
        params.putInt("scene", scene);
        params.putInt("type", 1);
        return params;
    }


    public void toShow() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = YueFangApplication.getApplication().getWindownWidth();
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.dimAmount = 0.6f;
        getWindow().setWindowAnimations(R.style.popupStyle);
        getWindow().setAttributes(layoutParams);
        show();
    }
}
