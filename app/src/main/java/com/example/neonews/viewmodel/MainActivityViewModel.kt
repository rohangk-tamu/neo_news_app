package com.example.neonews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neonews.data.NewsArticle
import com.example.neonews.repository.NewsRepository
import com.example.neonews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _data: MutableLiveData<Resource<List<NewsArticle>>> = MutableLiveData()
    val data: LiveData<Resource<List<NewsArticle>>> get() = _data

    init {
          fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            try {
                _data.postValue(Resource.Loading())
                val news = repository.getNews()

                val newsArticlesList: List<NewsArticle> = news.articles.map { articleDto ->
                    NewsArticle(
                        title = articleDto.title ?: "",
                        url = articleDto.url,
                        thumbnailUrl = articleDto.urlToImage ?: "",
                        publishedAt = articleDto.publishedAt
                    )
                }
                _data.value = Resource.Success(newsArticlesList)
            } catch (e: Exception) {
                _data.value = Resource.Error(message = e.message ?: "An error occurred")
            }
        }
    }

    fun bookmarkArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.upsert(newsArticle)
    }

    fun getBookmarkedNews() = repository.getBookmarkedNews()

    fun deleteArticle(newsArticle: NewsArticle) = viewModelScope.launch {
        repository.deleteArticle(newsArticle)
    }
}