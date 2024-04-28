package com.example.neonews.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.neonews.data.NewsArticle

@Database(
    entities = [NewsArticle::class],
    version = 1,
    exportSchema = false
)
abstract class NewsArticleDatabase: RoomDatabase() {

    abstract fun getNewsArticleDao(): NewsArticleDao
}