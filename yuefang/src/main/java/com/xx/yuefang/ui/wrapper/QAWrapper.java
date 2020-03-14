package com.xx.yuefang.ui.wrapper;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.bean.QABean;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.database.UserOption;
import com.xx.yuefang.database.entity.UserBean;
import com.xx.yuefang.databinding.LayoutAskBinding;
import com.xx.yuefang.databinding.LayoutReplayBinding;
import com.xx.yuefang.databinding.WrapperQaBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.fragment.house.houseDetail.AskEveryoneList;
import com.xx.yuefang.ui.fragment.house.houseDetail.AskQuestion;
import com.xx.yuefang.ui.fragment.house.houseDetail.QADetail;
import com.xx.yuefang.util.ImagUtil;

import java.util.List;

public class QAWrapper extends Wrapper {

    private WrapperQaBinding binding;


    public QAWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    View initView() {
        UserBean userBean = UserOption.getInstance().querryUser();
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wrapper_qa, this, false);
        if (userBean != null) {
            binding.setUserType(userBean.getUserType());
        }
        return binding.getRoot();
    }

    @Override
    public void initData(PremisesDetail.DataBean data) {
        List<QABean> questionsAnswers = data.getQuestionsAnswers();
        binding.setData(data);
        if (questionsAnswers != null && questionsAnswers.size() > 0) {
            binding.llQaContainer.removeAllViews();
            for (int i = 0; i < questionsAnswers.size(); i++) {
                if (i == 0) {
                    QABean qa = questionsAnswers.get(i);
                    LayoutAskBinding ask = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_ask, binding.llQaContainer, false);
                    ask.question.setText(qa.getContent());
                    View askView = ask.getRoot();
                    binding.llQaContainer.addView(askView);
                    binding.llQaContainer.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            QADetail replayQuestion = new QADetail();
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", qa.getId());
                            replayQuestion.setArguments(bundle);
                            Presenter.getInstance().step2fragment(replayQuestion, "replay");
                        }
                    });
                    List<QABean.QuestionsAnswersDtosBean> questionsAnswersDtos = qa.getQuestionsAnswersDtos();
                    if (questionsAnswersDtos != null && questionsAnswersDtos.size() != 0) {
                        for (int j = 0; j < questionsAnswersDtos.size(); j++) {
                            if (j == 0) {
                                QABean.QuestionsAnswersDtosBean questionsAnswersDtosBean = questionsAnswersDtos.get(j);
                                LayoutReplayBinding replay = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_replay, binding.llQaContainer, false);
                                replay.setCount(questionsAnswersDtos.size());
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
                                } else {
                                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_chat_head);
                                    Drawable drawable = ImagUtil.circle(bitmap);
                                    replay.answer.setDrawable(drawable);
                                }
                                binding.llQaContainer.addView(replay.getRoot());
                            }
                        }

                    }
                }
            }
        }

        binding.ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userType = binding.getUserType();
                if (userType == 4||userType==0) {
                    AskQuestion askQuestion = new AskQuestion();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", data.getId());
                    bundle.putString("name", data.getPremisesName());
                    askQuestion.setArguments(bundle);
                    Presenter.getInstance().step2fragment(askQuestion, "askQuestion");
                }else {
                    AskEveryoneList askEveryone = new AskEveryoneList();
                    askEveryone.setDatas(data.getId());
                    Presenter.getInstance().step2fragment(askEveryone, "askEveryone");
                }
            }
        });

        binding.lookAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskEveryoneList askEveryone = new AskEveryoneList();
                askEveryone.setDatas(data.getId());
                Presenter.getInstance().step2fragment(askEveryone, "askEveryone");
            }
        });


    }
}
