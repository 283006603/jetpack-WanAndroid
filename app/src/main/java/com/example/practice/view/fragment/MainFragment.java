package com.example.practice.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.ImageBannerAdapter;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.adapter.MyGridViewAdpter;
import com.example.practice.adapter.MyViewPagerAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.BannerBean;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.bean.WeChatArticle;
import com.example.practice.config.Constants;
import com.example.practice.databinding.FragmentMainBinding;
import com.example.practice.view.activity.WeChatDetailActivity;
import com.example.practice.view.activity.WebViewActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.example.practice.widge.GridViewPager;
import com.example.practice.widge.MyGridView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment<FragmentMainBinding>{

    RecyclerView mainRecycleview;
    SmartRefreshLayout refreshLayout;
    private View headView;
    private MainViewModel mainViewModel;
    public int page = 0;
    public List<BannerBean> bannerBeanList = new ArrayList<>();
    public List<MainArticleBean> mainArticleBeanList = new ArrayList<>();
    public List<WeChatArticle> weChatArticleList = new ArrayList<>();
    private MainArticleAdapter adapter;
    private PageList<MainArticleBean> pageList;
    private Banner mainBanner;
    private ImageBannerAdapter imageAdapter;
    private GridViewPager viewpager;

    private int totalPage; //总的页数
    private int mPageSize = 10; //每页显示的最大的数量
    private List<View> viewPagerList;//GridView作为一个View对象添加到ViewPager集合中

    @Override
    public void initView(Bundle bundle){
        super.initView(bundle);
        mainRecycleview = binding.mainRecycleview;
        refreshLayout = binding.refreshLayout;
        mainRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList);
        mainRecycleview.setAdapter(adapter);
        //---
        headView = LayoutInflater.from(getContext()).inflate(R.layout.item_main_header, null);
        mainBanner = headView.findViewById(R.id.main_banner);
        viewpager = headView.findViewById(R.id.view_pager);
        //---
        imageAdapter = new ImageBannerAdapter(bannerBeanList, getContext());
        mainBanner.setCurrentItem(1);
        mainBanner.setIndicator(new RectangleIndicator(getContext()));
        mainBanner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
        mainBanner.setIndicatorMargins(new IndicatorConfig.Margins(0,0,100,50));
        //mainBanner.setBannerGalleryEffect(150, 20); //添加画廊效果
        mainBanner.setAdapter(imageAdapter).addBannerLifecycleObserver(this);
        //=====
        adapter.addHeaderView(headView);
    }

    @Override
    public void initListener(){
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
        //RecycleView点击事件
        adapter.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position){
                goWebActivity(mainArticleBeanList.get(position));
            }
        });
        //banner点击事件
        mainBanner.setOnBannerListener(new OnBannerListener(){
            @Override
            public void OnBannerClick(Object data, int position){
                goWebActivityForBanner(bannerBeanList.get(position));
            }
        });
    }

    private void goWebActivityForBanner(BannerBean bannerBean){
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITILE, bannerBean.getTitle());
        bundle.putString(Constants.AUTHOR, "");
        bundle.putInt(Constants.ID, bannerBean.getId());
        bundle.putString(Constants.URL, bannerBean.getUrl());
        activity(WebViewActivity.class, bundle);
    }

    private void goWeChatDetailActivity(WeChatArticle weChatArticle){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, weChatArticle.getId());
        bundle.putString(Constants.TITILE, weChatArticle.getName());
        activity(WeChatDetailActivity.class, bundle);
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
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public void getRemoteData(){
        //获取首页Banner
        mainViewModel.getBanner();
        //获取首页文章列表
        mainViewModel.getMainArticle(page);
        //获取微信公众号
        mainViewModel.getWeChatArticle();
    }

    @Event(key = {Constants.GET_MAIN_ARTICLE_REFRESH, Constants.GET_MAIN_ARTICLE_LOADMORE, Constants.REQUEST_ERROR, Constants.GET_MAIN_BANNER, Constants.GET_MAIN_WECHAT_ARTICLE})
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
        }else if(key.equals(Constants.GET_MAIN_BANNER)){
            bannerBeanList.addAll((List<BannerBean>) value);
            imageAdapter.notifyDataSetChanged();
        }else if(key.equals(Constants.GET_MAIN_WECHAT_ARTICLE)){
            weChatArticleList = (List<WeChatArticle>) value;
            loadGridViewPager();
        }else if(key.equals(Constants.REQUEST_ERROR)){
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            //显示网络错误视图
        }
    }

    private void loadGridViewPager(){
        totalPage = (int) Math.ceil(weChatArticleList.size() * 1.0 / mPageSize);
        viewPagerList = new ArrayList<View>();
        for(int i = 0; i < totalPage; i++){
            //每个页面都是inflate出一个新实例
            final MyGridView gridView = (MyGridView) View.inflate(getContext(), R.layout.gridview_main, null);
            gridView.setAdapter(new MyGridViewAdpter(getContext(), weChatArticleList, i, mPageSize));
            gridView.setNumColumns(5);
            //添加item点击监听
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
                    // TODO Auto-generated method stub
                    Object obj = gridView.getItemAtPosition(position);
                    if(obj != null && obj instanceof WeChatArticle){
                        goWeChatDetailActivity(weChatArticleList.get(position));
                    }
                }
            });
            //每一个GridView作为一个View对象添加到ViewPager集合中
            viewPagerList.add(gridView);
        }
        //设置ViewPager适配器
        viewpager.setAdapter(new MyViewPagerAdapter(viewPagerList));
    }
}