package com.example.newsapp.ui.top_headlines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.SharedRepository
import com.example.newsapp.utilities.Constants.Companion.API_KEY
import kotlinx.coroutines.launch

class TopHeadlinesViewModel : ViewModel() {
    private val repository = SharedRepository()

    fun getTopHeadlines() {
        viewModelScope.launch {
            val response = repository.getTopHeadlines(API_KEY, "us")
            Log.d("news response --->", response.toString())
        }
    }
}