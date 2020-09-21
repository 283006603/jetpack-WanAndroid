package com.example.practice.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.practice.R;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment{

    @BindView(R.id.main_recycleview)
    RecyclerView mainRecycleview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.main_banner)
    Banner mainBanner;
    private View headView;
    private MainViewModel mainViewModel;
    public int page = 0;
    public List<MainArticleBean> list = new ArrayList<>();
    private MainArticleAdapter adapter;
    private PageList<MainArticleBean> pageList;

    @Override
    public void initView(Bundle state){
        super.initView(state);
        mainRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new MainArticleAdapter(list, getContext());
        mainRecycleview.setAdapter(adapter);
        headView = LayoutInflater.from(getContext()).inflate(R.layout.item_main_header, null);
        initListener();
    }

    private void initListener(){
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout){
                page = 0;
                mainViewModel.getMainArticle(page);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                mainViewModel.getMainArticle(++page);
            }
        });
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_main;
    }

    @Override
    public void getRemoteData(){
        //获取首页Banner
        mainViewModel.getBanner();
        //获取首页文章列表
        mainViewModel.getMainArticle(page);
    }

    @Event(key = {Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR})
    public void onEvent(String key, Object value){
        if(key.equals(Constants.GET_MAIN_ARTICLE_REFRESH)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList.getDatas() != null && pageList.getDatas().size() != 0){
                list.clear();
                list.addAll(pageList.getDatas());
                adapter.notifyDataSetChanged();
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if(page == pageList.getTotal() - 1){
                    refreshLayout.setEnableLoadMore(false);
                }else{
                    refreshLayout.setEnableLoadMore(true);
                }
            }else{
                //第一页都没有数据,显示空视图
            }
            refreshLayout.finishRefresh();
        }else if(key.equals(Constants.GET_MAIN_ARTICLE_LOADMORE)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList == null || pageList.getDatas().size() == 0){
                //加载页面没有数据，显示空视图
            }else{
                if(pageList.getDatas() != null){
                    list.addAll(pageList.getDatas());
                    adapter.notifyDataSetChanged();
                }
                //判断是否到了底页,得提示,告知用户
                if(page == pageList.getTotal() - 1){
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }else{
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