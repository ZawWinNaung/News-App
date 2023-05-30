package com.example.newsapp.data

class ApiClient {
    private val retrofitService: RetrofitService by lazy {
        NetworkLayer.retrofit.create(RetrofitService::class.java)
    }
}