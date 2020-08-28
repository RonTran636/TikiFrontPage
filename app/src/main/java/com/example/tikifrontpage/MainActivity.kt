package com.example.tikifrontpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.tikifrontpage.adapter.BannerAdapter
import com.example.tikifrontpage.adapter.QuickLinkAdapter
import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.viewmodel.MainActivityViewModel
import com.example.tikifrontpage.data.QuickLink
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bannerViewpager
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private var bannerViewModel = MainActivityViewModel()
    private val bannerAdapter = BannerAdapter(Banner(arrayListOf()))
    private val quickLinkAdapter = QuickLinkAdapter(QuickLink(arrayListOf()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bannerViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        bannerViewModel.refresh()

        bannerViewpager.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = bannerAdapter
            PagerSnapHelper().attachToRecyclerView(bannerViewpager)
            setHasFixedSize(true)
        }
        quickLinkView.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, 2, LinearLayoutManager.HORIZONTAL, false)
            adapter=quickLinkAdapter
            setHasFixedSize(true)
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        bannerViewModel.bannerInfo.observe(this, { banner ->
            banner?.let {
                bannerViewpager.visibility = View.VISIBLE
                bannerAdapter.updateBanner(it)
            }
        })
        bannerViewModel.quickLinkInfo.observe(this, { quickLink: QuickLink ->
            quickLinkView.visibility = View.VISIBLE
            quickLink.let { quickLinkAdapter.updateQuickLinkData(it)
            }
        })

        bannerViewModel.loading.observe(this, { isLoading ->
            isLoading?.let {
                loadingProgressBar.visibility = if (it) View.INVISIBLE else View.GONE
                if (it) {
                    bannerViewpager.visibility = View.GONE
                }
            }
        })
    }
}