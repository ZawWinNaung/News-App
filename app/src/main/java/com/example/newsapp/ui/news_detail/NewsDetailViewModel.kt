package com.example.newsapp.ui.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.db.RoomRepository
import com.example.newsapp.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    fun saveArticle(article: Article) =
        viewModelScope.launch { roomRepository.upsert(article) }
}