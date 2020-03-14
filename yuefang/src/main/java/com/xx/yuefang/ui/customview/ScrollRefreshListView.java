package com.xx.yuefang.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.xx.yuefang.R;
import com.xx.yuefang.databinding.ListFootBinding;
import com.xx.yuefang.databinding.ListSearchEmptyBinding;
import com.xx.yuefang.util.LogUtil;


public class ScrollRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private ListFootBinding listFootBinding;
    private int page;
    private boolean isLoading;
    private ListSearchEmptyBinding emptyBinding;

    public ScrollRefreshListView(Context context) {
        this(context, null);
    }

    public ScrollRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollRefreshListView);
        page = typedArray.getInteger(R.styleable.ScrollRefreshListView_page, 1);
        isLoading = typedArray.getBoolean(R.styleable.ScrollRefreshListView_isLoading, true);
        typedArray.recycle();
        listFootBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_foot, this, false);
        listFootBinding.setIsLoading(isLoading);
        emptyBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_search_empty, this, false);
        emptyBinding.back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (back != null) {
                    back.back();
                }
            }
        });
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            View lastVisibleItemView = getChildAt(getChildCount() - 1);
            if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == getHeight() && isLoading) {
                if (getFooterViewsCount() > 0) {
                    removefoot();
                }
                addFooterView(listFootBinding.getRoot());
                listFootBinding.setIsLoading(isLoading);
                LogUtil.log("===========滑到底了==============" + page);
                if (refreshData != null && isLoading) {
                    isLoading = false;
                    listFootBinding.setIsLoading(false);
                    page++;
                    refreshData.refreshData(page);
                }
                return;
            }
        }

    }

    public interface RefreshData {
        void refreshData(int page);

    }

    private RefreshData refreshData;

    public void setRefreshData(RefreshData refreshData) {
        this.refreshData = refreshData;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        listFootBinding.setIsLoading(isLoading);
    }


    public void showEmpty() {
        if (getFooterViewsCount() > 0) {
            removefoot();
        }
        addFooterView(emptyBinding.getRoot());
    }

    public void restart() {
        isLoading = false;
        page = 1;
    }

    public void removefoot() {
        if (getFooterViewsCount() != 0) {
            removeFooterView(listFootBinding.getRoot());
            removeFooterView(emptyBinding.getRoot());
        }
    }


    public interface Back {
        void back();
    }

    private Back back;

    public Back getBack() {
        return back;
    }

    public void setBack(Back back) {
        this.back = back;
    }


}
