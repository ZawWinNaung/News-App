package com.example.newsapp.ui.saved_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.db.RoomRepository
import com.example.newsapp.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    ViewModel() {
    fun getSavedArticles() = roomRepository.getAllArticles()

    fun unsaveArticle(article: Article) =
        viewModelScope.launch { roomRepository.deleteArticle(article) }
}