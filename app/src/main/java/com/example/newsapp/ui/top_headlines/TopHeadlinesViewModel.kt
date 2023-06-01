package com.example.newsapp.ui.top_headlines

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.ApiResponse
import com.example.newsapp.data.SharedRepository
import com.example.newsapp.model.TopHeadlinesResponseModel
import com.example.newsapp.utilities.Constants.Companion.API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(private val repository: SharedRepository) :
    ViewModel() {
    private val _topHeadlinesResponse = MutableLiveData<TopHeadlinesResponseModel>()
    val topHeadlinesResponseModel: LiveData<TopHeadlinesResponseModel>
        get() = _topHeadlinesResponse

    fun getTopHeadlines(category: String) {
        viewModelScope.launch {
            val response = repository.getTopHeadlines(API_KEY, "us", category)
            if (response.isSuccessful) {
                response.body.let {

                    _topHeadlinesResponse.postValue(it)
                }
            }

        }
    }
}