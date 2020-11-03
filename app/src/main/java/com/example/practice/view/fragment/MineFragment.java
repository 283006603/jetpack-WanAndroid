package com.example.practice.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.example.practice.R;
import com.example.practice.base.BaseFragment;
import com.example.practice.utils.BlurUtil;
import com.example.practice.view.activity.MeiZiActivity;
import com.example.practice.widge.ItemView;
import com.example.practice.widge.ZoomScrollView;

import androidx.fragment.app.Fragment;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener{
    @BindView(R.id.iv_avatar_background)
    ImageView backImgView;
    @BindView(R.id.sv_scroll)
    ZoomScrollView scrollView;

    @BindView(R.id.iv_mine_favorite)
    ItemView favoriteItemView;
    @BindView(R.id.iv_mine_meizi)
    ItemView meiziItemView;
    @BindView(R.id.iv_mine_about)
    ItemView aboutItemView;



    @Override
    public void initViewModel(){
    }

    @Override
    public int getLayoutResId(){
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(View rootView){
        super.initView(rootView);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        backImgView.setImageBitmap(BlurUtil.blur(getContext(), bitmap, 18));
        scrollView.setZoomView(backImgView);

        initListener();
    }

    private void initListener(){
        favoriteItemView.setOnClickListener(this);
        meiziItemView.setOnClickListener(this);
        aboutItemView.setOnClickListener(this);
    }

    @Override
    public void getRemoteData(){


    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.iv_mine_meizi:
                activity(MeiZiActivity.class);
            break;
        }
    }
}