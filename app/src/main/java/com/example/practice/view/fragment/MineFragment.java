package com.example.practice.view.fragment;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.base.BaseFragment;
import com.example.practice.utils.BlurUtil;
import com.example.practice.utils.CacheToolsUtil;
import com.example.practice.utils.FullPopupwindow;
import com.example.practice.view.activity.MeiZiActivity;
import com.example.practice.widge.ItemView;
import com.example.practice.widge.ZoomScrollView;

import androidx.appcompat.app.AlertDialog;
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
    @BindView(R.id.iv_mine_cache)
    ItemView ivMineCache;
    @BindView(R.id.iv_mine_setting)
    ItemView ivMineSetting;

    FullPopupwindow popupWindow;

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
        ivMineCache.setOnClickListener(this);
        ivMineSetting.setOnClickListener(this);
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
            case R.id.iv_mine_cache:
                try{
                    showCachelDialog();
                }catch(Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.iv_mine_favorite:
            case R.id.iv_mine_setting:
                Toast.makeText(mActivity, R.string.str_function_not_open, Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_mine_about:
                showAboutPop();
                break;
        }
    }

    private void showAboutPop(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop_about_mine, null, false);
        if(popupWindow == null){
            popupWindow = new FullPopupwindow(getContext());
            popupWindow.setContentView(view);
        }
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.START, 0, 0);
    }

    private void showCachelDialog() throws Exception{
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
        normalDialog.setTitle("清除缓存");
        normalDialog.setMessage("清除全部缓存:" + CacheToolsUtil.getTotalCacheSize(getContext()));
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                CacheToolsUtil.clearAllCache(getContext());
            }
        });
        normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        });
        normalDialog.show();
    }
}