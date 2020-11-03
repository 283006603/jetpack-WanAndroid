package com.example.practice.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.practice.R;
import com.example.practice.bean.GankMeiziBean;
import com.example.practice.view.activity.MeiZiDetailActivity;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import androidx.annotation.Nullable;

public class ImagePreviewAdapter extends BaseQuickAdapter<GankMeiziBean, BaseViewHolder>{
    public ImagePreviewAdapter(int layoutResId, @Nullable List<GankMeiziBean> data){
        super(layoutResId, data);
    }




    @Override
    protected void convert(BaseViewHolder helper, GankMeiziBean item){
        PhotoView photoView=helper.getView(R.id.pv_photo);
        photoView.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener(){
            @Override
            public void onOutsidePhotoTap(ImageView imageView){
                finishActivity();
            }
        });
        photoView.setOnPhotoTapListener(new OnPhotoTapListener(){
            @Override
            public void onPhotoTap(ImageView view, float x, float y){
                finishActivity();
            }
        });
        Glide.with(getContext())
                .load(item.getUrl())
                .into(photoView);


    }

    private void finishActivity() {
        if (getContext() instanceof MeiZiDetailActivity) {
            ((MeiZiDetailActivity) getContext()).finish();
        }
    }

}
