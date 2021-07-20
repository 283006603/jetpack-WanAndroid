package com.example.practice.view.activity

import android.content.Intent
import android.graphics.Picture
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.R
import com.example.practice.adapter.ImagePreviewAdapter
import com.example.practice.base.BaseActivity
import com.example.practice.bean.GankMeiziBean
import com.example.practice.config.Constants
import com.example.practice.databinding.ActivityMeiZiDetailBinding
import com.example.practice.utils.AppUtil
import com.example.practice.viewmodel.DownLoadViewMoudel
import com.wljy.mvvmlibrary.annotation.Event
import java.io.File


class MeiZiDetailActivity : BaseActivity<ActivityMeiZiDetailBinding?>() {

    var recyclerView: RecyclerView? = null
    var countTxtView: TextView? = null
    var saveTxtView: TextView? = null

    var dataList: MutableList<GankMeiziBean> = ArrayList()
    var curPosition = 0
    var layoutManager: LinearLayoutManager? = null

    var adapter: ImagePreviewAdapter? = null
    val TAG: String = "MeiZiDetailActivity"
    var downLoadViewMoudel: DownLoadViewMoudel? = null

    var ttsDirPath: String? = null
    private var fileName: String? = null

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        recyclerView = binding?.rvImagePreview
        countTxtView = binding?.tvImagePreviewCount
        saveTxtView = binding?.tvImageSave;
        getIntentData()
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.layoutManager = layoutManager
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(recyclerView)
        adapter = ImagePreviewAdapter(R.layout.item_image_preview, dataList)
        recyclerView?.adapter = adapter

        recyclerView?.scrollToPosition(curPosition)

        var s = String.format(
            resources.getString(R.string.position_and_count),
            curPosition + 1,
            dataList.size
        )
        countTxtView?.setText(s)

        //        ttsDirPath = getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/tts";  data/data/包名下   Eviroment私有目录用户看不到
        //        File file = new File(ttsDirPath + "/" + unitId + "_" + sex + ".mp3");
        //        if (file.exists()) {
        //            doMediaPlay(file.getAbsolutePath());
        //        } else {
        //            downloadViewModule.download(textAudioUrl, file.getAbsolutePath());
        //        }
    }

    private fun getIntentData() {
        val bundle = intent?.extras
        if (bundle?.isEmpty == false) {
            curPosition = bundle.getInt("position")
            dataList = bundle.getParcelableArrayList<GankMeiziBean>("meizis") ?: ArrayList()
        }
    }

    override fun initListener() {
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    curPosition == layoutManager?.findLastVisibleItemPosition()
                    countTxtView?.setText((curPosition + 1).toString() + "/" + dataList.size)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        saveTxtView?.setOnClickListener {
            val meiziBean = dataList.get(curPosition)
            var downUrl = meiziBean.url
            //--

            val dirFile =getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            fileName =AppUtil.getAppName(applicationContext) + "_" + System.currentTimeMillis() + ".jpg"
            //第一个参数根目录，第二个参数相当于/+目录
            val file = File(dirFile, fileName)
            downLoadViewMoudel?.savaImg(downUrl, file.absolutePath)

        }
    }

    @Event(key = [Constants.DOWN_IMG_SUCCESS, Constants.REQUEST_ERROR])
    fun event(key: String, value: Any) {
        if (key == Constants.DOWN_IMG_SUCCESS) {
            //插入相册库
            MediaStore.Images.Media.insertImage(contentResolver, value as String, fileName, null)
            // 最后通知图库更新
            sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(value as String)))
            Toast.makeText(this,"保存图片成功",Toast.LENGTH_LONG).show()
        } else if (key == Constants.REQUEST_ERROR) {
            Log.d(TAG, "保存失败");
        }
    }

    override fun getRemoteData() {
    }

    override fun initViewModel() {
        downLoadViewMoudel = registerViewModel(DownLoadViewMoudel::class.java)
    }

    override fun useImmersionBar(): Boolean {
        return true
    }

}