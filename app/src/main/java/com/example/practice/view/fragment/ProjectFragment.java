package com.example.practice.view.fragment;

import android.os.Bundle;

import com.example.practice.R;
import com.example.practice.adapter.ProjectPageAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.ProjectListBean;
import com.example.practice.bean.ProjectPageBean;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment{

    @BindView(R.id.table_layout)
    TabLayout tableLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainViewModel mainViewModel;
    private List<ProjectListBean> projectListBeans;
    private List<ProjectPageBean> projectPageBeans;

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_project;
    }

    @Override
    public void initView(Bundle state){
        super.initView(state);
        initToolBar();
    }

    private void initToolBar(){
        
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getProject();
    }

    @Event(key = {Constants.GET_PROJECT_LIST, Constants.REQUEST_ERROR})
    public void event(String key, Object value){
        if(key.equals(Constants.GET_PROJECT_LIST)){
            projectListBeans = (List<ProjectListBean>) value;
            changePageFragment();
            relateVpAndTab();
        }else if(key.equals(Constants.REQUEST_ERROR)){
        }
    }

    private void relateVpAndTab(){
        ProjectPageAdapter pageAdapter = new ProjectPageAdapter(getChildFragmentManager(), projectPageBeans);
        viewPager.setAdapter(pageAdapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    private List<ProjectPageBean> changePageFragment(){
        projectPageBeans = new ArrayList<>();
        for(ProjectListBean projectListBean : projectListBeans){
            ProjectPageBean projectPageBean = new ProjectPageBean();
            projectPageBean.setId(projectListBean.getId());
            projectPageBean.setTitle(projectListBean.getName());
            projectPageBean.setFragment(ProjectPageFragment.newInstance(projectListBean.getId()));
            projectPageBeans.add(projectPageBean);
        }
        return projectPageBeans;
    }
}