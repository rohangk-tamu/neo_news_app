package com.example.neonews.repository

import com.example.neonews.api.NewsApi
import com.example.neonews.api.NewsResponse
import com.example.neonews.data.NewsArticle
import com.example.neonews.db.NewsArticleDao
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    private val newsDao: NewsArticleDao
) : NewsRepository {

    override suspend fun getNews(): NewsResponse {
        return newsApi.getBreakingNews()
    }

    override suspend fun upsert(newsArticle: NewsArticle) = newsDao.upsert(newsArticle)

    override fun getBookmarkedNews() = newsDao.getAllArticles()

    override suspend fun deleteArticle(newsArticle: NewsArticle) = newsDao.deleteArticle(newsArticle)
}