package com.example.tikifrontpage.data

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class QuickLink(
    @SerializedName("data")
    val data: ArrayList<ArrayList<QuickLinkItem>>
)

data class QuickLinkItem(
    val authentication: Boolean,
    val content: String,
    val image_url: String,
    val title: String,
    val url: String,
    val web_url: String
)