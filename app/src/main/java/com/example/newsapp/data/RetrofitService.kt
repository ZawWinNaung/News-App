package com.example.newsapp.data

import com.example.newsapp.model.TopHeadlinesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): Response<TopHeadlinesResponseModel>
}