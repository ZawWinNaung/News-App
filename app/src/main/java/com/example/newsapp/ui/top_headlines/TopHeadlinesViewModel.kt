package com.example.newsapp.ui.top_headlines

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.SharedRepository
import com.example.newsapp.db.RoomRepository
import com.example.newsapp.model.Article
import com.example.newsapp.model.TopHeadlinesResponseModel
import com.example.newsapp.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val repository: SharedRepository,
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val _topHeadlinesResponse = MutableLiveData<TopHeadlinesResponseModel>()
    val topHeadlinesResponseModel: LiveData<TopHeadlinesResponseModel>
        get() = _topHeadlinesResponse

    fun getTopHeadlines(category: String) {
        viewModelScope.launch {
            val response = repository.getTopHeadlines(Constants.API_KEY, "us", category)
            if (response.isSuccessful) {
                response.body.let {
                    _topHeadlinesResponse.postValue(it)
                }
            }

        }
    }

    fun getSavedArticles() = roomRepository.getAllArticles()

    fun saveArticle(article: Article) = viewModelScope.launch {
        roomRepository.upsert(article)
    }

    fun unsaveArticle(article: Article) = viewModelScope.launch {
        roomRepository.deleteArticle(article)
    }
}