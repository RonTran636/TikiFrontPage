package com.example.tikifrontpage.data

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("data")
    val data: ArrayList<BannerItem>
)

data class BannerItem(
    @SerializedName("mobile_url")
    val mobile_url: String,
    @SerializedName("url")
    val url: String
)