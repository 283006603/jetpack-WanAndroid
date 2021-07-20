package com.example.practice.bean

import android.os.Parcel
import android.os.Parcelable
import com.example.practice.config.NoArg
import kotlinx.android.parcel.Parcelize

/**
 * Created By 大苏打
 * on 2020/11/3
 */
@NoArg
@Parcelize
data class GankMeiziBean(
    var _id: String,
    var author: String,
    var category: String,
    var createdAt: String,
    var desc: String,
    var images: List<String>,
    var likeCounts: Int,
    var publishedAt: String,
    var stars: Int,
    var title: String,
    var type: String,
    var url: String,
    var views: Int
) : Parcelable