package com.example.newsapp.data

import com.example.newsapp.model.TopHeadlinesResponseModel
import retrofit2.Response
import java.lang.Exception

class ApiClient {
    private val retrofitService: RetrofitService by lazy {
        NetworkLayer.retrofit.create(RetrofitService::class.java)
    }

    suspend fun getTopHeadlines(
        apiKey: String,
        country: String
    ): ApiResponse<TopHeadlinesResponseModel> {
        return safeApiCall { retrofitService.getTopHeadlines(apiKey, country) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): ApiResponse<T> {
        return try {
            ApiResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            ApiResponse.failure(e)
        }
    }
}