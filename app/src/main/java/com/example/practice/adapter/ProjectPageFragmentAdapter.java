package com.example.practice.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.practice.R;
import com.example.practice.bean.MainArticleBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/9/21
 */
public class ProjectPageFragmentAdapter extends BaseQuickAdapter<MainArticleBean, BaseViewHolder>{

    public ProjectPageFragmentAdapter(int layoutResId, @Nullable List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MainArticleBean mainArticleBean){
        Log.d("ProjectPageFragmentAdap", mainArticleBean.getEnvelopePic());
        Log.d("ProjectPageFragmentAdap", mainArticleBean.getTitle());
        holder.setText(R.id.tv_title, mainArticleBean.getTitle());
        holder.setText(R.id.tv_shareuser, TextUtils.isEmpty(mainArticleBean.getShareUser()) ? "作者：" + mainArticleBean.getAuthor() : "分享人：" + mainArticleBean.getShareUser());
        holder.setText(R.id.tv_time, "时间：" + mainArticleBean.getNiceDate());
        Glide.with(getContext()).load(mainArticleBean.getEnvelopePic()).centerCrop().into((ImageView) holder.getView(R.id.ima_show));
    }
}
