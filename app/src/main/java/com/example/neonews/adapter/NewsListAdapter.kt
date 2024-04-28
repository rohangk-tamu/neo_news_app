package com.example.neonews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.neonews.data.NewsArticle
import com.example.neonews.databinding.ItemNewsBinding

class NewsListAdapter : ListAdapter<NewsArticle, NewsViewHolder>(NewsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item)

            holder.itemView.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    private var onItemClickListener: ((NewsArticle) -> Unit)? = null
    fun setOnClickListener(listener: (NewsArticle) -> Unit) {
        onItemClickListener = listener
    }

}