package com.example.practice.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.ProjectPageFragmentAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.config.Constants;
import com.example.practice.databinding.FragmentProjectPageBinding;
import com.example.practice.view.activity.WebViewActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectPageFragment extends BaseFragment<FragmentProjectPageBinding>{

    public int id;
    RecyclerView recycleview;
    SmartRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;
    int page=1;
    public PageList<MainArticleBean> pageList;
    public List<MainArticleBean> mainArticleBeanList = new ArrayList<>();
    private ProjectPageFragmentAdapter adapter;

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProjectPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectPageFragment newInstance(int id){
        ProjectPageFragment fragment = new ProjectPageFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(Bundle bundle){
        super.initView(bundle);
         recycleview=binding.recycleview;
         refreshLayout=binding.refreshLayout;

        if(getArguments() != null){
            id = getArguments().getInt(Constants.ID);
        }
        recycleview.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter = new ProjectPageFragmentAdapter(R.layout.item_project,mainArticleBeanList);
        recycleview.setAdapter(adapter);
    }

    @Override
    public void initListener(){
        super.initListener();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout){
                page = 1;
                mainViewModel.getProjectFragemtList(page, id);
            }
        });
        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener(){
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout){
                mainViewModel.getProjectFragemtList(++page, id);
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
        mainViewModel.getProjectFragemtList(page, id);
    }

    @Event(key = {Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR})
    public void even(String key, Object value){
        if(key.equals(Constants.GET_MAIN_ARTICLE_REFRESH)){
            pageList = (PageList<MainArticleBean>) value;
            if(pageList.getDatas() != null && pageList.getDatas().size() != 0){
                mainArticleBeanList.clear();
                mainArticleBeanList.addAll(pageList.getDatas());
                adapter.notifyDataSetChanged();
                //判断是不是最后一页,如果是，关闭上滑加载,连底部没有数据都不提示，这里page是从1开始
                if(page == pageList.getPageCount()){
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
                //判断是否到了底页,得提示,告知用户,这里page是从1开始
                if(page == pageList.getPageCount() ){
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