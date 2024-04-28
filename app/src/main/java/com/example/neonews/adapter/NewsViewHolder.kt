package com.example.neonews.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neonews.R
import com.example.neonews.data.NewsArticle
import com.example.neonews.databinding.ItemNewsBinding

class NewsViewHolder(
    private val binding: ItemNewsBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(news: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(news.thumbnailUrl)
                .error(R.drawable.image_placeholder)
                .into(imageView)

            textViewTitle.text = news.title ?: ""
        }
    }
}