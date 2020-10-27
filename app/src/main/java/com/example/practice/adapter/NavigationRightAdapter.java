package com.example.practice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.practice.R;
import com.example.practice.bean.NavigationListBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created By 大苏打
 * on 2020/10/23
 */
public class NavigationRightAdapter extends BaseQuickAdapter<NavigationListBean, BaseViewHolder>{
    public NavigationRightAdapter(int layoutResId, @Nullable List<NavigationListBean> data){
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, NavigationListBean navigationListBean){
        holder.setText(R.id.tv_name, navigationListBean.getName());
        TagFlowLayout tagFlow = holder.getView(R.id.tag_flow);


        tagFlow.setAdapter(new TagAdapter<NavigationListBean.ArticlesBean>(navigationListBean.getArticles()){
            @Override
            public View getView(FlowLayout parent, int position, NavigationListBean.ArticlesBean articlesBean){
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_tag_flow2, tagFlow, false);
                textView.setText(articlesBean.getTitle());
                return textView;
            }
        });

        tagFlow.setOnTagClickListener(new TagFlowLayout.OnTagClickListener(){
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                mtagSelectListener.onTagSelect(holder.getLayoutPosition(),position);
                return true;
            }
        });

    }


    TagSelectListener mtagSelectListener;
    public void setOnTagSelectListener(TagSelectListener tagSelectListener){
        mtagSelectListener=tagSelectListener;
    }

   public  interface TagSelectListener {
        void onTagSelect(int positon,int tagPosition);
    }
}
