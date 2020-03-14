package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.QABean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.databinding.LayoutReplayBinding;
import com.xx.yuefang.databinding.ListAskEveryoneItemBinding;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class AskEveryoneListAdapter extends BaseListAdapter {
    public AskEveryoneListAdapter(Context context, List<QABean> datas, int res) {
        super(context, datas, res);
    }

    @Override
    protected View initView(LayoutInflater layoutInflater, int res, List datas, int position, ViewGroup parent) {
        ListAskEveryoneItemBinding itemBinding = DataBindingUtil.inflate(layoutInflater, res, parent, false);
        QABean bean = (QABean) datas.get(position);
        itemBinding.setData(bean);
        List<QABean.QuestionsAnswersDtosBean> questionsAnswersDtos = bean.getQuestionsAnswersDtos();
        if (questionsAnswersDtos.size() != 0) {
            QABean.QuestionsAnswersDtosBean questionsAnswersDtosBean = questionsAnswersDtos.get(0);
            LayoutReplayBinding replay = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_replay, itemBinding.replayContainer, false);
            replay.setBean(questionsAnswersDtosBean);
            String avatar = questionsAnswersDtosBean.getAvatar();
            String url = ImagUtil.handleUrl(avatar);
            if (!TextUtils.isEmpty(url)) {
                RxImageLoader.with(context).getBitmap(url).subscribe(
                        imageBean -> {
                            Bitmap bitmap = imageBean.getBitmap();
                            Drawable drawable = ImagUtil.circle(bitmap);
                            replay.answer.setDrawable(drawable);
                        }
                );
            }else {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_chat_head);
                Drawable drawable = ImagUtil.circle(bitmap);
                replay.answer.setDrawable(drawable);
            }
            itemBinding.replayContainer.addView(replay.getRoot());
        }
        return itemBinding.getRoot();
    }

}
