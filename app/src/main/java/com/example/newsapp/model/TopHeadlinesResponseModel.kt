package com.example.newsapp.model

data class TopHeadlinesResponseModel(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article?>?,
)