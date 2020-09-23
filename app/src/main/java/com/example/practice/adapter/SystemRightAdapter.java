package com.example.practice.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.practice.R;
import com.example.practice.bean.SystemListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/9/23
 */
public class SystemRightAdapter extends BaseQuickAdapter<SystemListBean.ChildrenBean,BaseViewHolder>{

    public SystemRightAdapter(int layoutResId, @Nullable List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, SystemListBean.ChildrenBean childrenBean){
        holder.setText(R.id.tv_right,childrenBean.getName());
    }
}
