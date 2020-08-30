package com.example.tikifrontpage.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.tikifrontpage.R
import com.example.tikifrontpage.data.FlashDeal
import com.example.tikifrontpage.data.FlashDealItem
import com.example.tikifrontpage.util.getProgressDrawable
import com.example.tikifrontpage.util.loadImage
import kotlinx.android.synthetic.main.item_flashdeal.view.*

private const val TAG= "FlashDealAdapter"
class FlashDealAdapter(private var listFlashDeal: FlashDeal) :
RecyclerView.Adapter<FlashDealAdapter.FlashDealViewHolder>(){

    fun updateFlashDealData(newFlashDeal: FlashDeal){
        listFlashDeal = newFlashDeal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashDealViewHolder {
       return FlashDealViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_flashdeal,parent,false))
    }

    override fun onBindViewHolder(holder: FlashDealViewHolder, position: Int) {
        holder.setData(listFlashDeal.data[position])
    }

    override fun getItemCount(): Int {
       return listFlashDeal.data.size
    }

    class FlashDealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imageView=itemView.flashDealImage
        private val progressDrawable= getProgressDrawable((itemView.context))
        private val price = itemView.flashDealPrice
        private val discount = itemView.flashDealDiscount
        private val progressBar = itemView.progressBar
        private var progressInfo = itemView.progressInfo
        fun setData(flashDeal: FlashDealItem){
            imageView.loadImage(flashDeal.product.thumbnail_url, progressDrawable)
            price.apply {
                setAmount(flashDeal.product.price.toFloat())
            }
            discount.text = "-${flashDeal.discount_percent}%"
            progressBar.setProgress(100-(flashDeal.progress.percent).toInt())
            if (flashDeal.progress.qty_ordered>0)
                progressInfo.text = "Đã bán ${flashDeal.progress.qty_ordered}"
            else progressInfo.text ="Vừa mở bán"

            imageView.setOnClickListener{
                Log.d(TAG, "setData: You have clicked on ${flashDeal.product.name}")
            }
        }
    }
}