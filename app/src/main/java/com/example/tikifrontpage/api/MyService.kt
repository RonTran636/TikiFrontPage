package com.example.tikifrontpage.api


import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.QuickLink
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MyService {
    private val BASE_URL = "https://api.tiki.vn/"
    private val api: BannerAPI

    init {
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BannerAPI::class.java)
    }

    fun getBanner(): Single<Banner> {
        return api.getBanner()
    }

    fun getQuickLink(): Single<QuickLink> {
        return api.getQuickLink()
    }

    fun getFlashDeal(): Single<FlashDeal>{
        return api.getFlashDeal()
    }
}