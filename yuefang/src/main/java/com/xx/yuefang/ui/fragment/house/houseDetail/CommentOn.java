package com.xx.yuefang.ui.fragment.house.houseDetail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.bean.Comment;
import com.xx.yuefang.bean.UserCommentResponse;
import com.xx.yuefang.databinding.FragmentCommentOnBinding;
import com.xx.yuefang.net.HttpUtil;
import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.GsonUtil;
import com.xx.yuefang.util.statusBarHandler.StatusBarUtil;

import org.json.JSONObject;

public class CommentOn extends BaseFragment {

    private FragmentCommentOnBinding binding;
    private int id;
    private String[] strs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comment_on, container, false);
        binding.setPresenter(Presenter.getInstance());
        binding.setFragment(this);
        initData();
        initlisten();
        return binding.getRoot();
    }

    @Override
    public void initData() {

        strs = new String[]{"未实地看过", "实地看过"};

        Bundle bundle = getArguments();
        id = bundle.getInt("id");
        String name = bundle.getString("name");
        binding.name.setText(name);

    }

    @Override
    public void initView() {

    }

    @Override
    public void initlisten() {
        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = binding.score.ratingBar1.getRating();
                if (rating == 0) {
                    Toast.makeText(getContext(), "请先给楼盘打分", Toast.LENGTH_SHORT).show();
                    return;
                }
                String commentContent = binding.commetContent.getText().toString();
                if (TextUtils.isEmpty(commentContent)) {
                    Toast.makeText(getContext(), "请填写评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                int selectPosition = binding.getSelectPosition();
                commentContent = "#" + strs[selectPosition] + "#" + commentContent;
                HttpUtil.getInstance().commentOn(id, rating, commentContent).subscribe(
                        str -> {
                            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                            EventData eventData = new EventData(UserObservable.TYPE_USER_COMMENT_SUCCESS);
                            Bundle bundle = new Bundle();
                            bundle.putString("commentResponse",str);
                            eventData.setData(bundle);
                            UserObservable.getInstance().notifyObservers(eventData);
                            Presenter.getInstance().back();
                        }
                );
            }
        });
    }


    public void select(int position) {
        binding.setSelectPosition(position);
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
