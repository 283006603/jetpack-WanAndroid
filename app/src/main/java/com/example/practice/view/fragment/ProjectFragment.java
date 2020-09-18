package com.example.practice.view.fragment;

import com.example.practice.R;
import com.example.practice.base.BaseFragment;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends BaseFragment{

    @Override
    public void initViewModel(){
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_project;
    }

    @Override
    public void getRemoteData(){
    }
}