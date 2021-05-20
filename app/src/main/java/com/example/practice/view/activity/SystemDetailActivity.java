package com.example.practice.view.activity;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.bean.SystemListBean;
import com.example.practice.config.Constants;
import com.example.practice.databinding.ActivityWeChatDetailBinding;
import com.example.practice.viewmodel.MainViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SystemDetailActivity extends BaseActivity<ActivityWeChatDetailBinding>{
    Toolbar toolbar;
    RecyclerView recycleview;
    SmartRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;
    private int id;
    List<MainArticleBean> mainArticleBeanList = new ArrayList<>();
    private MainArticleAdapter adapter;
    private int page = 0;
    private PageList<MainArticleBean> pageList;
    private String name;
    private SystemListBean.ChildrenBean childrenBean;

    @Override
    public boolean useImmersionBar(){
        return false;
    }

    @Override
    public void initStatusBar(){
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(com.wljy.mvvmlibrary.R.color.icon_enabled).statusBarDarkFont(true).init();
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        toolbar = binding.toolbar.toolbar;
        recycleview = binding.recycleview;
        refreshLayout = binding.refreshLayout;
        getInitData();
        recycleview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList);
        recycleview.setAdapter(adapter);
    }

    private void getInitData(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            childrenBean = (SystemListBean.ChildrenBean) extras.getSerializable("childrenBean");
            id = childrenBean.getId();
            name = childrenBean.getName();
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
    }

    @Override
    protected void initListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
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
        adapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                goWebActivity(mainArticleBeanList.get(position));
            }
        });
    }

    private void goWebActivity(MainArticleBean mainArticleBean){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITILE, mainArticleBean.getTitle());
        bundle.putString(Constants.AUTHOR, mainArticleBean.getAuthor());
        bundle.putInt(Constants.ID, mainArticleBean.getId());
        bundle.putString(Constants.URL, mainArticleBean.getLink());
        activity(WebViewActivity.class, bundle);
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getSystemDetail(page, id);
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Event(key = {Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR})
    public void onEvent(String key, Object value){
        if(key.equals(Constants.GET_MAIN_ARTICLE_REFRESH)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList.getDatas() != null && pageList.getDatas().size() != 0){
                mainArticleBeanList.clear();
                mainArticleBeanList.addAll(pageList.getDatas());
                adapter.notifyDataSetChanged();
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示
                if(page == pageList.getPageCount() - 1){
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
                    mainArticleBeanList.addAll(pageList.getDatas());
                    adapter.notifyDataSetChanged();
                }
                //判断是否到了底页,得提示,告知用户
                if(page == pageList.getPageCount() - 1){
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