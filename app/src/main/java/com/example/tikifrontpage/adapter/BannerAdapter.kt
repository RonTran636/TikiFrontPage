package com.example.tikifrontpage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tikifrontpage.R
import com.example.tikifrontpage.data.Banner
import com.example.tikifrontpage.data.BannerItem
import com.example.tikifrontpage.util.getProgressDrawable
import com.example.tikifrontpage.util.loadImage
import kotlinx.android.synthetic.main.item_banner.view.*


private const val TAG = "BannerAdapter"

class BannerAdapter(private var listBanner: Banner) :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    fun updateBanner(newBanner: Banner) {
        listBanner = newBanner
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        )
    }

    override fun getItemCount(): Int = listBanner.data.size

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.setData(listBanner.data[position])
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.bannerImage
        private val progressDrawable = getProgressDrawable((itemView.context))
        fun setData(banner: BannerItem) {
            imageView.loadImage(banner.mobile_url, progressDrawable)
        }
    }
}
