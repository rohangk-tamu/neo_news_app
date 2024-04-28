package com.example.neonews.api

data class NewsArticleDto(
    val title: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String
)