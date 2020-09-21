package com.example.practice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.bean.MainArticleBean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created By 大苏打
 * on 2020/9/21
 */
public class MainArticleAdapter extends RecyclerView.Adapter<MainArticleAdapter.MyViewHolder>{

    List<MainArticleBean> list;
    Context context;

    public MainArticleAdapter(List<MainArticleBean> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_article, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_chaptername.setText("分类："+list.get(position).getSuperChapterName()+"/"+list.get(position).getChapterName());
        holder.tv_shareuser.setText("分享人："+list.get(position).getShareUser());
        holder.tv_time.setText("时间："+list.get(position).getNiceDate());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_shareuser;
        TextView tv_chaptername;
        TextView tv_time;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_shareuser=itemView.findViewById(R.id.tv_shareuser);
            tv_chaptername=itemView.findViewById(R.id.tv_chaptername);
            tv_time=itemView.findViewById(R.id.tv_time);
        }
    }
}
