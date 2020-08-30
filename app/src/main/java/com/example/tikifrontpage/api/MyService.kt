package com.example.tikifrontpage.api

import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.QuickLink
import com.example.tikifrontpage.di.DaggerAPIComponent
import io.reactivex.Single
import javax.inject.Inject

class MyService {

    @Inject
    lateinit var  api: MyAPI
    init {
        DaggerAPIComponent.create().inject(this)
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