package com.example.neonews.repository

import androidx.lifecycle.LiveData
import com.example.neonews.api.NewsResponse
import com.example.neonews.data.NewsArticle
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call

interface NewsRepository {

    suspend fun getNews(): NewsResponse

    suspend fun upsert(newsArticle: NewsArticle): Long

    fun getBookmarkedNews(): LiveData<List<NewsArticle>>

    suspend fun deleteArticle(newsArticle: NewsArticle)
}