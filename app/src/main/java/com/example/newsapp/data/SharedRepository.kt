package com.example.newsapp.data

import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.model.TopHeadlinesResponseModel
import javax.inject.Inject

class SharedRepository @Inject constructor(
    private val apiClient: ApiClient,
) {

    suspend fun getTopHeadlines(
        apiKey: String,
        country: String,
        category: String,
    ): ApiResponse<TopHeadlinesResponseModel> {
        return apiClient.getTopHeadlines(apiKey, country, category)
    }

//    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)
//
//    fun getSavedNews() = db.getArticleDao().getAllArticles()
//
//    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}