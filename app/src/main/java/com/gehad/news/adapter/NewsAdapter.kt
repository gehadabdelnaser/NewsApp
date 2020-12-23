package com.gehad.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gehad.news.R
import com.gehad.news.data.ArticlesItem
import com.gehad.news.databinding.ItemNewsBinding

class NewsAdapter(var list:List<ArticlesItem?>?):RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val dataBinding=DataBindingUtil.inflate<ItemNewsBinding>(
            LayoutInflater.from(parent.context), R.layout.item_news ,parent,false)
        return NewsViewHolder(dataBinding)
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item= list?.get(position) ?: return
        holder.bind(item)

        // use binding adapter to change image
       // Glide.with(holder.itemView).load(item.urlToImage).into(holder.dataBinding.newsImage)

        if(onItemClickListener !=null){
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,item)
            }
        }
    }

    fun changeData(newsList :List<ArticlesItem?>?){
        this.list=newsList
        notifyDataSetChanged()
    }

    var onItemClickListener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(position :Int , news :ArticlesItem?)
    }

    class NewsViewHolder(val dataBinding:ItemNewsBinding):RecyclerView.ViewHolder(dataBinding.root){

        fun bind(item:ArticlesItem){
            dataBinding.item=item
            dataBinding.executePendingBindings()
        }
//        var newsImage:ImageView
//        var newsTitle:TextView
//        var dateOfPublished:TextView
//        var description:TextView

//        init {
//            newsImage=itemView.findViewById(R.id.news_image)
//            newsTitle=itemView.findViewById(R.id.news_title_tv)
//            dateOfPublished=itemView.findViewById(R.id.date_of_published_tv)
//            description=itemView.findViewById(R.id.description_tv)
//        }

    }
}