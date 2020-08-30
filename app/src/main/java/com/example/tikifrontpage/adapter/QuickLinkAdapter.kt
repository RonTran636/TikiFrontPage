package com.example.tikifrontpage.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tikifrontpage.R
import com.example.tikifrontpage.data.QuickLink
import com.example.tikifrontpage.util.getProgressDrawable
import com.example.tikifrontpage.util.loadImage
import com.example.tikifrontpage.data.QuickLinkItem
import kotlinx.android.synthetic.main.item_quicklink.view.*
import kotlinx.android.synthetic.main.row_quicklink.view.*

class QuickLinkAdapter(private var listQuickLink: QuickLink) :
    RecyclerView.Adapter<QuickLinkAdapter.QuickLinkViewHolder>(){

    fun updateQuickLinkData(newQuickLink: QuickLink){
        listQuickLink = newQuickLink
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickLinkViewHolder {
        return QuickLinkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_quicklink,parent,false))
    }

    override fun onBindViewHolder(holder: QuickLinkViewHolder, position: Int) {
        val childQuickLinkAdapter = ChildQuickLinkAdapter(listQuickLink.data[position])
        holder.rvQuickLink.apply {
            adapter = childQuickLinkAdapter
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        }
    }

    override fun getItemCount(): Int {
        return listQuickLink.data.size
    }

    class QuickLinkViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val rvQuickLink: RecyclerView = itemView.row_quickLink
    }

    class ChildQuickLinkAdapter(private var childQuickLink: ArrayList<QuickLinkItem>) :
        RecyclerView.Adapter<ChildQuickLinkAdapter.ChildQuickLinkViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildQuickLinkViewHolder {
           return ChildQuickLinkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_quicklink,parent,false))
        }

        override fun onBindViewHolder(holder: ChildQuickLinkViewHolder, position: Int) {
            holder.setData(childQuickLink[position])
        }

        override fun getItemCount(): Int {
           return childQuickLink.size
        }

        class ChildQuickLinkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            private val imageView=itemView.quickLinkIcon
            private val progressDrawable= getProgressDrawable((itemView.context))
            private val textView = itemView.quickLinkTitle
            fun setData(quickLink: QuickLinkItem){
                imageView.loadImage(quickLink.image_url, progressDrawable)
                textView.text = quickLink.title
            }
        }
    }



}