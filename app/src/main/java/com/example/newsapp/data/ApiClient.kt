package com.example.newsapp.data

import com.example.newsapp.model.TopHeadlinesResponseModel
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ApiClient @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getTopHeadlines(
        apiKey: String,
        country: String,
        category: String,
        page: Int
    ): ApiResponse<TopHeadlinesResponseModel> {
        return safeApiCall { retrofitService.getTopHeadlines(apiKey, country, category, page) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): ApiResponse<T> {
        return try {
            ApiResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            ApiResponse.failure(e)
        }
    }
}