package com.xx.yuefang.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.xx.yuefang.R;
import com.xx.yuefang.bean.PremisesDetail;
import com.xx.yuefang.catche.Loader.RxImageLoader;
import com.xx.yuefang.strategy.ClickSureListener;
import com.xx.yuefang.util.ImagUtil;
import com.xx.yuefang.util.LogUtil;
import com.xx.yuefang.util.RudenessScreenHelper;

import java.util.List;

public class VrItemAdapter extends RecyclerView.Adapter<VrItemAdapter.VrItemViewHolder> {
    private Context context;
    private List<PremisesDetail.DataBean.VRListBean> vrList;
    private ClickSureListener clickSureListener;
    private RecyclerView mRv;

    public VrItemAdapter(Context context, List<PremisesDetail.DataBean.VRListBean> vrList, ClickSureListener clickSureListener, RecyclerView recyclerView) {
        this.context = context;
        this.vrList = vrList;
        this.clickSureListener = clickSureListener;
        this.mRv = recyclerView;
    }

    @NonNull
    @Override
    public VrItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_ar, viewGroup, false);
        VrItemViewHolder vrItemViewHolder = new VrItemViewHolder(itemView);
        return vrItemViewHolder;
    }

    private int mSelectedPos;

    @Override
    public void onBindViewHolder(@NonNull VrItemViewHolder viewHolder, int i) {
        viewHolder.setPosition(i);
        PremisesDetail.DataBean.VRListBean vrListBean = vrList.get(i);
        String picture = vrListBean.getPicture();
        String premisesARName = vrListBean.getPremisesARName();
        viewHolder.vrName.setText(premisesARName);
        String url = ImagUtil.handleUrl(picture);
        if (!TextUtils.isEmpty(url)) {
            RxImageLoader.with(context).getBitmap(url).subscribe(
                    imageBean -> {
                        viewHolder.loading.setVisibility(View.GONE);
                        Bitmap bitmap = imageBean.getBitmap();
                        viewHolder.setBitmap(bitmap);
                        int size = (int) RudenessScreenHelper.pt2px(context, 100);
                        bitmap = ImagUtil.zoomImg(bitmap, size, size);
                        viewHolder.img.setImageDrawable(new BitmapDrawable(bitmap));
                    }
            );
        }
    }

    @Override
    public int getItemCount() {
        return vrList.size();
    }

    public class VrItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private int position;
        private TextView vrName;
        private Bitmap bitmap;
        private ProgressBar loading;

        public void setPosition(int position) {
            this.position = position;
        }

        public VrItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.img = itemView.findViewById(R.id.ivvr);
            this.vrName = itemView.findViewById(R.id.vrName);
            this.loading = itemView.findViewById(R.id.img_loading);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bitmap != null) {
                        clickSureListener.clickVtItem(position, bitmap);
                        handlerClickItem();
                    }
                }
            });

        }

        public void handlerClickItem() {
            VrItemViewHolder vh = (VrItemViewHolder) mRv.findViewHolderForLayoutPosition(mSelectedPos);
            if (vh != null) {//还在屏幕里
                vh.img.setSelected(false);
            } else {
                //add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                notifyItemChanged(mSelectedPos);
            }
            vrList.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
            //设置新Item的勾选状态
            mSelectedPos = position;
            vrList.get(mSelectedPos).setSelected(true);
            img.setSelected(true);
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }
}