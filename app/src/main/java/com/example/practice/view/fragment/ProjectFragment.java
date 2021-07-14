package com.example.practice.view.fragment;

import android.os.Bundle;

import com.example.practice.adapter.ProjectPageAdapter;
import com.example.practice.base.BaseFragment;
import com.example.practice.bean.ProjectListBean;
import com.example.practice.bean.ProjectPageBean;
import com.example.practice.config.Constants;
import com.example.practice.databinding.FragmentProjectBinding;
import com.example.practice.viewmodel.MainViewModel;
import com.example.practice.widge.MySlidingTabLayout;
import com.wljy.mvvmlibrary.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment<FragmentProjectBinding>{

    MySlidingTabLayout tableLayout;
    ViewPager viewPager;

    private MainViewModel mainViewModel;
    private List<ProjectListBean> projectListBeans;
    private List<ProjectPageBean> projectPageBeans;

    @Override
    public void initViewModel(){
        mainViewModel = registerViewModel(MainViewModel.class);
    }

    @Override
    public void initView(Bundle bundle){
        super.initView(bundle);
        tableLayout = binding.tableLayout;
        viewPager = binding.viewPager;
    }

    @Override
    public void initListener(){
        super.initListener();

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
        //      https://www.jianshu.com/p/8fdd6f2719b0
        ProjectPageAdapter pageAdapter = new ProjectPageAdapter(getChildFragmentManager(), projectPageBeans);
        String titles[] = new String[projectPageBeans.size()];
        for(int i = 0; i < projectPageBeans.size(); i++){
            titles[i] = projectListBeans.get(i).getName();
        }
        viewPager.setAdapter(pageAdapter);
        tableLayout.setViewPager(viewPager, titles);
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