package com.example.practice.view.activity;

import android.os.Bundle;

import com.example.practice.R;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.MainArticleBean;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeChatDetailActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private MainViewModel mainViewModel;
    private int id;
    List<MainArticleBean>list=new ArrayList<>();

    @Override
    public boolean useImmersionBar(){
        return false;
    }

    @Override
    public void initStatusBar(){
        super.initStatusBar();
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        getInitData();
        recycleview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

    }

    private void getInitData(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getInt(Constants.ID, -1);
        }
    }

    @Override
    protected void initListener(){
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_we_chat_detail;
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}