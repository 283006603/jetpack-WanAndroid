package com.example.practice.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.example.practice.R;
import com.example.practice.adapter.ImagePreviewAdapter;
import com.example.practice.base.BaseActivity;
import com.example.practice.bean.GankMeiziBean;
import com.example.practice.databinding.ActivityMeiZiDetailBinding;
import com.example.practice.utils.AppUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MeiZiDetailActivity extends BaseActivity<ActivityMeiZiDetailBinding>{

    RecyclerView recyclerView;
    TextView countTxtView;
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
         recyclerView=binding.rvImagePreview;
         countTxtView=binding.tvImagePreviewCount;
         saveTxtview=binding.tvImageSave;

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
    public void getRemoteData(){
    }

    @Override
    public void initViewModel(){
    }


    private boolean writeToSdcard(Context context, InputStream inputStream) {
        if (inputStream == null) {
            return false;
        }
        File dirFile = new File(Environment.getExternalStorageDirectory(), AppUtil.getAppName(this));
        String fileName = AppUtil.getAppName(this) + "_" + System.currentTimeMillis() + ".jpg";
        File file = new File(dirFile, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] buffer = new byte[1024 * 5];
        int len = 0;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        try {
            bis = new BufferedInputStream(inputStream);
            fos = new FileOutputStream(file);
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
        return true;
    }
}