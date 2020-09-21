package com.example.practice.adapter;

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
public class MainArticleAdapter extends BaseQuickAdapter<MainArticleBean, BaseViewHolder>{

    public MainArticleAdapter(int layoutResId, @Nullable List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, MainArticleBean mainArticleBean){
        holder.setText(R.id.tv_title, mainArticleBean.getTitle());
        holder.setText(R.id.tv_chaptername, "分类：" + mainArticleBean.getChapterName());
        holder.setText(R.id.tv_shareuser, "分享人：" + mainArticleBean.getShareUser());
        holder.setText(R.id.tv_time, "时间：" + mainArticleBean.getNiceDate());
    }
}
