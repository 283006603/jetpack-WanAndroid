package com.example.practice.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.adapter.ImagePreviewAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.GankMeiziBean;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class MeiZiDetailActivity extends BaseActivity{

    @BindView(R.id.rv_image_preview)
    RecyclerView recyclerView;
    @BindView(R.id.tv_image_preview_count)
    TextView countTxtView;
    @BindView(R.id.tv_image_save)
    TextView saveTxtview;

    private ArrayList<GankMeiziBean> dataList = new ArrayList<>();
    private int curPosition;
    private LinearLayoutManager layoutManager;

    private ImagePreviewAdapter adapter;

    @Override
    public boolean useImmersionBar(){
        return true;
    }

    @Override
    public void initViews(Bundle savedInstanceState){
        super.initViews(savedInstanceState);
        getIntentData();
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        PagerSnapHelper pagerSnapHelper=new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
        adapter=new ImagePreviewAdapter(R.layout.item_image_preview,dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(curPosition);
        String s=String.format(getResources().getString(R.string.position_and_count),curPosition+1,dataList.size());
        countTxtView.setText(s);
    }

    private void getIntentData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curPosition = bundle.getInt("position");
            dataList = bundle.<GankMeiziBean>getParcelableArrayList("meizis");
        }
    }

    @Override
    protected void initListener(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState){
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    curPosition = layoutManager.findLastVisibleItemPosition();
                    countTxtView.setText((curPosition + 1) + "/" + dataList.size());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        saveTxtview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                GankMeiziBean meiziBean=dataList.get(curPosition);
                if(meiziBean!=null){
                    String url=meiziBean.getUrl();
//                    savaImage(url);
                }
            }
        });
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_mei_zi_detail;
    }

    @Override
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
    }
}