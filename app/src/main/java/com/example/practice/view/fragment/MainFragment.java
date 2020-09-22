package com.example.practice.view.fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.practice.R;
import com.example.practice.adapter.ImageBannerAdapter;
import com.example.practice.adapter.MainArticleAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.BannerBean;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.bean.PageList;
import com.example.practice.bean.WeChatArticle;
import com.example.practice.config.Constants;
import com.example.practice.view.activity.WebViewActivity;
import com.example.practice.viewmodel.MainViewModel;
import com.example.practice.widge.gridviewpager.GridRecyclerAdapter;
import com.example.practice.widge.gridviewpager.GridViewPager;
import com.example.practice.widge.gridviewpager.GridViewPagerAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wljy.mvvmlibrary.annotation.Event;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

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
    private GridViewPager gridViewPager;
    private int[] colors = {0xffec407a, 0xffab47bc, 0xff29b6f6, 0xff7e57c2, 0xffe24073, 0xffee8360, 0xff26a69a, 0xffef5350, 0xff2baf2b, 0xffffa726};

    @Override
    public void initView(Bundle state){
        super.initView(state);
        mainRecycleview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter = new MainArticleAdapter(R.layout.item_main_article, mainArticleBeanList);
        headView = LayoutInflater.from(getContext()).inflate(R.layout.item_main_header, null);
        mainBanner = headView.findViewById(R.id.main_banner);
        mainRecycleview.setAdapter(adapter);
        imageAdapter = new ImageBannerAdapter(bannerBeanList, getContext());
        mainBanner.setIndicator(new CircleIndicator(getContext()));
        //        mainBanner.setBannerGalleryEffect(150, 20); //添加画廊效果
        mainBanner.setAdapter(imageAdapter);
        adapter.addHeaderView(headView);
        //=====添加公众号
        gridViewPager = headView.findViewById(R.id.viewpager);
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
                Toast.makeText(mActivity, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        //公众号点击事件
        gridViewPager.setOnGridItemClickListener(new GridViewPager.OnGridItemClickListener(){
            @Override
            public void onGridItemClick(int position, View view){
                goWeChatDetailActivity(weChatArticleList.get(position));
            }
        });
    }

    private void goWeChatDetailActivity(WeChatArticle weChatArticle){
        Bundle bundle=new Bundle();
        bundle.putInt(Constants.ID,weChatArticle.getId());
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
    public int getLayoutResId(){
        return R.layout.fragment_main;
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
                    mainArticleBeanList.addAll(pageList.getDatas());
                    adapter.notifyDataSetChanged();
                }
                //判断是否到了底页,得提示,告知用户
                if(page == pageList.getTotal() - 1){
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
            setGridViewAdapter();
        }else if(key.equals(Constants.REQUEST_ERROR)){
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            //显示网络错误视图
        }
    }

    private void setGridViewAdapter(){
        gridViewPager.setAdapter(new GridViewPagerAdapter<WeChatArticle>(weChatArticleList){
            @Override
            public void bindData(GridRecyclerAdapter.ViewHolder viewHolder, WeChatArticle weChatArticle, int position){
                ShapeDrawable shapeDrawable = new ShapeDrawable();
                shapeDrawable.setShape(new OvalShape());
                shapeDrawable.getPaint().setColor(colors[position % colors.length]);
                viewHolder.setText(R.id.tv_home_author_icon, String.valueOf(weChatArticle.getName().charAt(0))).
                        setText(R.id.tv_home_author_name, weChatArticle.getName()).setBackground(R.id.tv_home_author_icon, shapeDrawable);
            }
        });
    }
}