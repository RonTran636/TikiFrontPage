package com.example.tikifrontpage.api

import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.QuickLink
import io.reactivex.Single
import retrofit2.http.GET

interface MyAPI {

    @GET("v2/home/banners/v2")
    fun getBanner(): Single<Banner>

    @GET("shopping/v2/widgets/quick_link")
    fun getQuickLink(): Single<QuickLink>

    @GET("v2/widget/deals/hot")
    fun getFlashDeal(): Single<FlashDeal>

}