package com.example.neonews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.neonews.data.NewsArticle

@Dao
interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: NewsArticle): Long

    @Query("SELECT * FROM newsArticles")
    fun getAllArticles(): LiveData<List<NewsArticle>>

    @Delete
    suspend fun deleteArticle(article: NewsArticle)
}