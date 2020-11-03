package com.example.practice.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.GankMeiziAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.GankLzyBaseResponse;
import com.example.practice.bean.GankMeiziBean;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MeiZiActivity extends BaseActivity{

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;

    int page = 1;
    int pagecount = 10;
    private GankLzyBaseResponse gankLzyBaseResponse;
    private List<GankMeiziBean> gankMeiziList;
    private List<GankMeiziBean> list = new ArrayList<>();
    private GankMeiziAdapter adapter;

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new GankMeiziAdapter(R.layout.item_meizi, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener(){
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout){
                page = 1;
                mainViewModel.getMeizi(page, pagecount);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                mainViewModel.getMeizi(++page, pagecount);
            }
        });
        adapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                if(adapter != null && list.size() != 0){
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("meizis", (ArrayList<? extends Parcelable>) list);
                    bundle.putInt("position", position);
                    activity(MeiZiDetailActivity.class, bundle);
                }
            }
        });
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_mei_zi;
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getMeizi(page, pagecount);
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Event(key = {Constants.GET_MEIZI_RESULT_REFRESH, Constants.GET_MEIZI_RESULT_LOADMORE, Constants.REQUEST_ERROR})
    public void event(String key, Object value){
        if(key.equals(Constants.GET_MEIZI_RESULT_REFRESH)){
            gankLzyBaseResponse = (GankLzyBaseResponse) value;
            gankMeiziList = (List<GankMeiziBean>) gankLzyBaseResponse.getData();
            if(gankMeiziList != null && gankMeiziList.size() != 0){
                list.clear();
                list.addAll(gankMeiziList);
                adapter.notifyDataSetChanged();
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if(list.size() >= gankLzyBaseResponse.getTotal_counts()){
                    refreshLayout.setEnableLoadMore(false);
                }else{
                    refreshLayout.setEnableLoadMore(true);
                }
            }else{
                //第一页都没有数据,显示空视图
            }
            refreshLayout.finishRefresh();
        }else if(key.equals(Constants.GET_MEIZI_RESULT_LOADMORE)){
            gankLzyBaseResponse = (GankLzyBaseResponse) value;
            gankMeiziList = (List<GankMeiziBean>) gankLzyBaseResponse.getData();
            if(gankMeiziList == null || gankMeiziList.size() == 0){
                Log.d("MeiZiActivity", "aaa");
                //加载页面没有数据，显示空视图
            }else{
                Log.d("MeiZiActivity", "bbb");
                if(gankMeiziList != null){
                    list.addAll(gankMeiziList);
                    adapter.notifyDataSetChanged();
                }
                //判断是否到了底页,得提示,告知用户
                if(list.size() >= gankLzyBaseResponse.getTotal_counts()){
                    Log.d("MeiZiActivity", "ccc");
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }else{
                    Log.d("MeiZiActivity", "ddd");
                    refreshLayout.finishLoadMore();
                }
            }
        }else if(key.equals(Constants.REQUEST_ERROR)){
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            //显示网络错误视图
        }
    }
}