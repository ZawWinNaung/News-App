package com.example.newsapp.data

import com.example.newsapp.model.TopHeadlinesResponseModel

class SharedRepository {
    suspend fun getTopHeadlines(apiKey: String, country: String): TopHeadlinesResponseModel? {
        val request = NetworkLayer.apiClient.getTopHeadlines(apiKey, country)
        if (request.failed || !request.isSuccessful) {
            return null
        }
        return request.body
    }
}