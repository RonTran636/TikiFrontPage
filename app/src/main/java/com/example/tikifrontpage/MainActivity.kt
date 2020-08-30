package com.example.tikifrontpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.tikifrontpage.adapter.BannerAdapter
import com.example.tikifrontpage.adapter.FlashDealAdapter
import com.example.tikifrontpage.adapter.QuickLinkAdapter
import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.QuickLink
import com.example.tikifrontpage.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bannerViewpager
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private var mainActivityViewModel = MainActivityViewModel()
    private val bannerAdapter :BannerAdapter = BannerAdapter(Banner(arrayListOf()))
    private var quickLinkAdapter: QuickLinkAdapter = QuickLinkAdapter(QuickLink(arrayListOf()))
    private var flashDealAdapter :FlashDealAdapter = FlashDealAdapter(FlashDeal(arrayListOf()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.refresh()

        bannerViewpager.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = bannerAdapter
            PagerSnapHelper().attachToRecyclerView(bannerViewpager)
            setHasFixedSize(true)
        }

        quickLinkView.apply {
            adapter=quickLinkAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        flashDealView.apply {
            adapter=flashDealAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            mainActivityViewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        mainActivityViewModel.bannerInfo.observe(this, { banner ->
            banner?.let {
                    bannerViewpager.visibility = View.VISIBLE
                    bannerAdapter.updateBanner(it)
            }
        })
        mainActivityViewModel.quickLinkInfo.observe(this, { quickLink ->
            quickLink.let {
                quickLinkView.visibility = View.VISIBLE
                quickLinkAdapter.updateQuickLinkData(it)
            }
        })

        mainActivityViewModel.flashDealInfo.observe(this, { flashDeal ->
            flashDeal?.let {
                bannerViewpager.visibility = View.VISIBLE
                flashDealAdapter.updateFlashDealData(it)
            }
        })

        mainActivityViewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                loadingProgressBar.visibility = if (it) View.INVISIBLE else View.GONE
                if (it) {
                    bannerViewpager.visibility = View.GONE
                }
            }
        })
    }
}