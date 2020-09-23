package com.example.practice.bean;

import androidx.fragment.app.Fragment;

/**
 * Created By 大苏打
 * on 2020/9/23
 */
public class ProjectPageBean{

    public Fragment fragment;
    public String title;
    public int id;

    public Fragment getFragment(){
        return fragment;
    }

    public void setFragment(Fragment fragment){
        this.fragment = fragment;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return "ProjectPageBean{" + "fragment=" + fragment + ", title='" + title + '\'' + ", id=" + id + '}';
    }
}
