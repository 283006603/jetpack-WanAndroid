package com.example.practice.view.fragment;

import android.os.Bundle;

import com.example.practice.R;
import com.example.practice.base.BaseFragment;
import com.example.practice.config.Constants;
import com.example.practice.viewmodel.MainViewModel;
import com.wljy.mvvmlibrary.annotation.Event;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectPageFragment extends BaseFragment{

    public int id;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private MainViewModel mainViewModel;
    int page;

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
    public void initView(Bundle state){
        super.initView(state);
        if(getArguments() != null){
            id = getArguments().getInt(Constants.ID);
        }
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_project_page;
    }

    @Override
    public void getRemoteData(){
        mainViewModel.getProjectFragemtList(page,id);
    }

    @Event(key = {Constants.GET_PROJECT_LIST_FRAGMENT,Constants.REQUEST_ERROR})
    public void even(String key,Object value){
        if(key.equals(Constants.GET_PROJECT_LIST_FRAGMENT)){

        }else if(key.equals(Constants.REQUEST_ERROR)){

        }
    }
}