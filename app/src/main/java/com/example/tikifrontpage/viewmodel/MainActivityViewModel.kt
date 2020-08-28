package com.example.tikifrontpage.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tikifrontpage.api.MyService
import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.QuickLink
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainActivityViewModel"

class MainActivityViewModel : ViewModel() {

    private val myService = MyService()
    private val disposable = CompositeDisposable()

    val bannerInfo = MutableLiveData<Banner>()
    val quickLinkInfo = MutableLiveData<QuickLink>()
    val flashDealInfo = MutableLiveData<FlashDeal>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchData()
    }

    private fun fetchData() {
        Log.d(TAG, "fetchBanner: Called")
        loading.value = true
        disposable.add(
            myService.getBanner()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Banner>() {
                    override fun onSuccess(value: Banner?) {
                        bannerInfo.value = value
                        loading.value = false
                    }
                    override fun onError(e: Throwable?) {
                        loading.value = false
                    }
                })
        )

        disposable.add(
            myService.getQuickLink()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<QuickLink>() {
                    override fun onSuccess(t: QuickLink?) {
                        quickLinkInfo.value = t
                        Log.d(TAG, "onSuccess: Value is $t")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "onError on Quick Link: error is $e")
                        loading.value = false
                    }
                })
        )
        Log.d(TAG, "fetchData: end of fetching quicklink data")

        disposable.add(
            myService.getFlashDeal()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FlashDeal>() {
                    override fun onSuccess(t: FlashDeal?) {
                        flashDealInfo.value = t
                        Log.d(TAG, "onSuccess: Value is $t")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e(TAG, "onError on Flash Deal: error is $e")
                        loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}