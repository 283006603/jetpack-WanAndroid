package com.example.practice.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.practice.R;
import com.example.practice.bean.GankMeiziBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/11/3
 */
public class GankMeiziAdapter extends BaseQuickAdapter<GankMeiziBean, BaseViewHolder>{
    public GankMeiziAdapter(int layoutResId, @Nullable List<GankMeiziBean> data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, GankMeiziBean gankMeiziBean){
        ImageView imageView=helper.getView(R.id.iv_meizi_image);
        Glide.with(getContext())
                .load(gankMeiziBean.getUrl())
                .into(imageView);
    }
}
