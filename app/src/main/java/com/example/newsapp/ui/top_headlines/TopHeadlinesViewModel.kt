package com.example.newsapp.ui.top_headlines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.SharedRepository
import com.example.newsapp.utilities.Constants.Companion.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val repository: SharedRepository) :
    ViewModel() {


    fun getTopHeadlines() {
        viewModelScope.launch {
            val response = repository.getTopHeadlines(API_KEY, "us")
            Log.d("news response --->", response.toString())
        }
    }
}