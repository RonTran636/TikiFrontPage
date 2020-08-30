package com.example.tikifrontpage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
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

    private val _bannerInfo = MutableLiveData<Banner>()
    val bannerInfo: LiveData<Banner>
        get() = _bannerInfo

    private val _quickLinkInfo = MutableLiveData<QuickLink>()
    val quickLinkInfo: LiveData<QuickLink>
        get() = _quickLinkInfo

    private val _flashDealInfo = MutableLiveData<FlashDeal>()
    val flashDealInfo: LiveData<FlashDeal>
        get() = _flashDealInfo

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
                        _bannerInfo.value = value
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
                        _quickLinkInfo.value = t
                    }
                    override fun onError(e: Throwable?) {
                        loading.value = false
                    }
                })
        )

        disposable.add(
            myService.getFlashDeal()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FlashDeal>() {
                    override fun onSuccess(t: FlashDeal?) {
                        _flashDealInfo.value = t
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