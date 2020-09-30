package com.example.practice.adapter;

import android.view.View;

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
public class SystemLeftAdapter extends BaseQuickAdapter<SystemListBean,BaseViewHolder>{

    public int select;
    public SystemLeftAdapter(int layoutResId, @Nullable List data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, SystemListBean systemListBean){
        holder.itemView.setSelected(select==holder.getLayoutPosition());
        holder.setText(R.id.tv_left,systemListBean.getName());

        holder.getView(R.id.ima_lable).setVisibility(select==holder.getLayoutPosition()? View.VISIBLE:View.GONE);
    }


    public void setSelect(int select){
        this.select=select;
    }
}
