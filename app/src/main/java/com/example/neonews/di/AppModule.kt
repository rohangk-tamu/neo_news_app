package com.example.neonews.di

import android.content.Context
import androidx.room.Room
import com.example.neonews.api.NewsApi
import com.example.neonews.db.NewsArticleDao
import com.example.neonews.db.NewsArticleDatabase
import com.example.neonews.repository.NewsRepository
import com.example.neonews.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRepository(
        newsApi: NewsApi,
        newsArticleDao: NewsArticleDao
    ): NewsRepository = NewsRepositoryImpl(newsApi, newsArticleDao)

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    @Provides
    fun providesDao(newsArticleDatabase: NewsArticleDatabase): NewsArticleDao =
        newsArticleDatabase.getNewsArticleDao()

    @Provides
    fun providesDatabase(@ApplicationContext context: Context): NewsArticleDatabase =
         Room.databaseBuilder(context, NewsArticleDatabase::class.java, "news_articles.db")
             .build()
}