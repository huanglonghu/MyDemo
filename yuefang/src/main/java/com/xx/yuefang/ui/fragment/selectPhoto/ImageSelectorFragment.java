package com.xx.yuefang.ui.fragment.selectPhoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xx.yuefang.observable.EventData;
import com.xx.yuefang.observable.UserObservable;
import com.xx.yuefang.ui.adapter.SelectorAdapter;
import com.xx.yuefang.bean.ISelectImageItem;
import com.xx.yuefang.bean.Img;
import com.xx.yuefang.ui.customview.selectphoto.GridSpacingItemDecoration;
import com.xx.yuefang.R;
import com.xx.yuefang.databinding.SelectPhotoBinding;
import com.xx.yuefang.presenter.Presenter;
import com.xx.yuefang.ui.base.BaseFragment;
import com.xx.yuefang.util.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageSelectorFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int REQUEST_CODE_PREVIEW = 1;
    private static final int READ_EXTERNAL_STORAGE_CODE = 2;
    private static final String EXTRA_KEY_MAX = "max";
    private static final String EXTRA_KEY_DATA = "data";
    private SelectorAdapter mAdapter;
    private List<Integer> mSelectSortPosList;
    private SelectPhotoBinding binding;
    private int urlType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.select_photo, container, false);
            initView();
            initData();
            initlisten();
        }
        return binding.getRoot();
    }

    public void initView() {
        urlType = getArguments().getInt("urlType");
        mSelectSortPosList = new ArrayList<>();
        mAdapter = new SelectorAdapter(onItemClickListener, binding.recyclePhoto);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        binding.recyclePhoto.setHasFixedSize(true);
        binding.recyclePhoto.addItemDecoration(new GridSpacingItemDecoration(3, 10, false, 3, 3));
        binding.recyclePhoto.setLayoutManager(layoutManager);
        binding.recyclePhoto.setAdapter(mAdapter);
    }

    @Override
    public void initlisten() {

        binding.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Presenter.getInstance().back();
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectPosition = mAdapter.getSelectPosition();
                if (selectPosition == -1) {
                    Toast.makeText(getContext(), "请选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                String path = mAdapter.getPathByPosition(selectPosition);
                EventData eventData = new EventData(urlType);
                Bundle bundle = new Bundle();
                bundle.putString("path", path);
                eventData.setData(bundle);
                UserObservable.getInstance().notifyObservers(eventData);
                Presenter.getInstance().back();
            }
        });

    }

    private SelectorAdapter.OnItemClickListener onItemClickListener = new SelectorAdapter.OnItemClickListener() {
        @Override
        public int onItemClick(ISelectImageItem item, int pos) {
            ImagePreviewFragment imagePreviewFragment = new ImagePreviewFragment();
            String[] paths = mAdapter.getPathByPosArray();
            Bundle bundle = new Bundle();
            bundle.putStringArray("paths", paths);
            bundle.putInt("index", pos);
            bundle.putInt("urlType",urlType);
            imagePreviewFragment.setArguments(bundle);
            Presenter.getInstance().step2fragment(imagePreviewFragment, "imagePreview");
            return mSelectSortPosList.size();
        }
    };


    public void initData() {
        if (permissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE_CODE)) {
            LogUtil.log("=================initData=================");
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_EXTERNAL_STORAGE_CODE:
                onRequestReadExternalStorageResult(permissions, grantResults);
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void onRequestReadExternalStorageResult(String[] permissions, int[] grantResults) {
        if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initData();
        } else {
            Toast.makeText(getContext(), R.string.image_selector_authorization_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean permissionGranted(String permission, int requestCode) {
        int permissionCode = ContextCompat.checkSelfPermission(getContext(), permission);
        if (permissionCode != PackageManager.PERMISSION_GRANTED) {
            try {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    private static final String[] IMAGE_PROJECTION = new String[]{
            MediaStore.MediaColumns.DATA
    };

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String select = MediaStore.Images.Media.SIZE + ">0";
        LogUtil.log("==============onCreateLoader==============");
        return new CursorLoader(getContext(), uri, IMAGE_PROJECTION, select, null, MediaStore.Images.Media.DISPLAY_NAME + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {//fragment可见时 调用
        if (mAdapter.getItemCount() == 0) {
            resetSelector();
            if (data == null || data.getCount() == 0) {
                return;
            }

            List<Img> imgs = new ArrayList<>();
            while (data.moveToNext()) {
                String path = data.getString(0);
                Img img = new Img().setPath(path);
                imgs.add(img);
            }
            LogUtil.log("=========imgs========" + imgs.size());
            mAdapter.replaceDatas(imgs);
        }

    }

    private void resetSelector() {
        mSelectSortPosList.clear();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSupportLoaderManager().destroyLoader(0);
    }
}
