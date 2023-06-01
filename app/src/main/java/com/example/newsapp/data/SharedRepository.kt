package com.example.newsapp.data

import com.example.newsapp.model.TopHeadlinesResponseModel
import javax.inject.Inject

class SharedRepository @Inject constructor(private val apiClient: ApiClient) {
    suspend fun getTopHeadlines(
        apiKey: String,
        country: String,
        category: String,
        page: Int
    ): ApiResponse<TopHeadlinesResponseModel> {
        return apiClient.getTopHeadlines(apiKey, country, category, page)
    }
}